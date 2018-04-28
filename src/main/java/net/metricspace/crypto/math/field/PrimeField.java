/* Copyright (c) 2017, Eric McCorkle.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.metricspace.crypto.math.field;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Number;
import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * Common superclass for elements of a finite integer field, modulo a
 * prime number.
 *
 * @param <Val> The type of arguments to arithmetic functions,
 *              typically the leaf subclass.
 */
public abstract class PrimeField<Val extends PrimeField<Val>>
    implements Cloneable {

    /**
     * Mapping used in {@link #toString}.
     */
    private static final char[] CHARTAB =
        new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
                     '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * The digits array.
     * <p>
     * This is an array of "digits", each consisting of a value
     * portion and a carry portion.  The carry portion must have
     * enough bits to allow all of the additions in a full multiply to
     * take place without overflowing.  Therefore, the width of the
     * carry portion must equal {@code 2 * (log2(n) + 1)}.
     * <p>
     * This representation has a number of advantages:
     * <ul>
     * <li>It does not rely on architecture-dependent carry semantics.
     * <li>It avoids potential side-channel vulnerabilities (the carry
     * state as well as possible branch variance).
     * </ul>
     * <p>
     * In normalized representations, all carry portions are
     * sign-extensions of the value portion (all zeros or all ones),
     * and the total value lies between 0 and the modulus of the prime
     * field.
     * <p>
     * However, the laws of modular arithmetic on allow for modular
     * reduction to be avoided except when required for serialization
     * or equality checks.  Additionally, the highest-order digit's
     * carry portion can be retained and used as a carry-in value to
     * the next operation.  Thus, it is possible for an internal
     * representation to exist in a non-normalized state, where the
     * highest digit's carry portion may contain a value other than a
     * sign extension of the value portion, and the overall value may
     * not be modularly reduced.
     */
    protected final long[] digits;

    /**
     * Initialize with a digits array.
     *
     * This constructor initializes the {@code digits} field.  The
     * created object takes possession of the array.  To initialize
     * from an array without taking possession, use {@link #set}.
     *
     * @param digits The {@code digits} field.
     * @see #set
     */
    protected PrimeField(final long[] digits) {
        this.digits = digits;
    }

    /**
     * Get the {@code n}th bit of the number.
     *
     * @param n Index of the bit to get.
     * @return The {@code n}th bit.
     */
    public long bit(final int n) {
        normalize();

        return bitNormalized(n);
    }

    /**
     * Get the sign of the number.  A number {@code n mod p} is
     * considered "positive" if it lies in {@code [0, (n - 1) / 2]},
     * negative otherwise.
     *
     * @return The sign of the number.
     */
    public long sign() {
        normalize();

        return signNormalized();
    }

    /**
     * Mask this number by a given bit, which is expanded into a mask
     * of either all {@code 0}s or {@code 1}s.  This bit is taken as a
     * {@code long}, so as to avoid branches.
     *
     * @param bit Either {@code 0} or {@code 1}.
     */
    public void mask(final long bit) {
        long mask = bit;

        mask |= mask << 1;
        mask |= mask << 2;
        mask |= mask << 4;
        mask |= mask << 8;
        mask |= mask << 16;
        mask |= mask << 32;

        for(int i = 0; i < digits.length; i++) {
            digits[i] &= mask;
        }
    }

    /**
     * Bitwise-or this number by another.  This is primarily used in
     * conjunction with {@link #mask(long)} to select one of two
     * numbers based on an input without the need for a branch.
     *
     * @param other The number to bitwise-or against this one.
     */
    public void or(final Val other) {
        for(int i = 0; i < digits.length; i++) {
            digits[i] |= other.digits[i];
        }
    }

    /**
     * Take the abstract value of the number.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @see #abs
     * @see #sign
     */
    public void abs() {
        final int signbit = (int)sign();
        final short mulval = (short)((-signbit * 2) + 1);

        mul(mulval);
    }

    /**
     * Take the abstract value of the number.
     *
     * @see #sign
     */
    public void absNormalized() {
        final int signbit = (int)signNormalized();
        final short mulval = (short)((-signbit * 2) + 1);

        mul(mulval);
    }

    /**
     * Add a {@code Val} to this number.
     *
     * @param b The {@code Val} to add.
     */
    public void add(final Val b) {
        add(b.digits);
    }

    /**
     * Subtract a {@code Val} from this number.
     *
     * @param b The {@code Val} to subtract.
     */
    public void sub(final Val b) {
        sub(b.digits);
    }

    /**
     * Multiply this number by a {@code Val}.
     *
     * @param b The {@code Val} by which to multiply.
     */
    public void mul(final Val b) {
        mul(b.digits);
    }

    /**
     * Multiply this number by a {@code Val}.
     *
     * @param b The {@code Val} by which to multiply.
     */
    public void div(final Val b) {
        div(b.digits);
    }

    /**
     * Overwrite the digits in this object from an array, starting at a
     * given index.
     *
     * @param digits The digits to copy.
     * @param idx The index at which to start.
     */
    protected void set(final long[] digits,
                       final int idx) {
        for(int i = 0; i < this.digits.length; i++) {
            this.digits[i] = digits[i + idx];
        }
    }

    /**
     * Overwrite the digits in this object from an array.
     *
     * @param digits The digits to copy.
     */
    protected void set(final long[] digits) {
        set(digits, 0);
    }

    /**
     * Overwrite the value of this number from a {@code Val}.
     *
     * @param b The {@code Val} to copy.
     */
    public void set(final Val b) {
        set(b.digits);
    }

    /**
     * Overwrite the value of this number with a {@code long}.
     *
     * @param b The {@code long} to copy.
     */
    public void set(final int b) {
        Arrays.fill(digits, 0);
        init(b);
    }

    /**
     * Write a little-endian representation into an array.
     * <p>
     * This method normalizes the internal representation.
     *
     * @param arr Array into which to write.
     * @see #normalize
     */
    public void pack(final byte[] arr) {
        normalize();
        normalizedPack(arr, 0);
    }

    /**
     * Write a little-endian representation into an array at a specific index.
     * <p>
     * This method normalizes the internal representation.
     *
     * @param arr Array into which to write.
     * @param idx Index at which to start.
     * @see #normalize
     */
    public void pack(final byte[] arr,
                     final int idx) {
        normalize();
        normalizedPack(arr, idx);
    }

    /**
     * Write a little-endian representation as a byte array to an
     * {@link java.io.OutputStream}.
     *
     * @param stream The {@link java.io.OutputStream} to which to write.
     * @throws java.io.IOException If an error occurred while writing data.
     */
    public void pack(final OutputStream stream)
        throws IOException {
        normalize();
        normalizedPack(stream);
    }

    /**
     * Write a little-endian representation into an array.
     * <p>
     * This method assumes the internal representation has already
     * been normalized.  If this is not known to be the case, use
     * {@link #pack} instead.
     *
     * @param arr Array into which to write.
     * @see #normalize
     */
    public void normalizedPack(final byte[] arr) {
        normalizedPack(arr, 0);
    }

    /**
     * Generate a string representation of this number.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @return A string representation of this number.
     * @see #normalize
     * @see #digits
     */
    public String normalizedToString() {
        return packedToString(normalizedPacked());
    }

    /**
     * Generate a string representation of a packed representation.
     *
     * @param arr The packed representation to stringify.
     * @return A string representation of {@code arr} the packed
     *         representation.
     * @see #normalizedToString
     */
    public static String packedToString(final byte[] arr) {
        final StringBuilder builder = new StringBuilder();

        for(int i = arr.length - 1; i >= 0; i--) {
            final byte b = arr[i];

            builder.append(CHARTAB[(b >> 4) & 0x0f]);
            builder.append(CHARTAB[b & 0x0f]);
        }

        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        normalize();

        return normalizedToString();
    }

    /**
     * Compare two numbers for equality.
     * <p>
     * This method normalizes the internal representation.
     *
     * @param b The number against which to compare.
     * @return Whether or not this number equals {@code b}
     * @see #normalize
     * @see #digits
     */
    public boolean equals(final Val b) {
        normalize();
        b.normalize();

        return normalizedEquals(b);
    }

    /**
     * Compare two numbers for equality.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @param b The number against which to compare.
     * @return Whether or not this number equals {@code b}
     * @see #normalize
     * @see #digits
     */
    public boolean normalizedEquals(final Val b) {
        long out = 0;

        for(int i = 0; i < digits.length; i++) {
            out |= digits[i] ^ b.digits[i];
        }

        return (out == 0);
    }

    /**
     * Write a little-endian representation as a byte array to an
     * {@link java.io.OutputStream}.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @param stream The {@link java.io.OutputStream} to which to write.
     * @throws java.io.IOException If an error occurred while writing data.
     * @see #normalize
     * @see #digits
     */
    public void normalizedPack(final OutputStream stream)
        throws IOException {
        stream.write(packed());
    }

    /**
     * Set the value of this number from a packed representation.
     *
     * @param packed The packed representation.
     */
    public void unpack(final byte[] packed) {
        unpack(packed, 0);
    }

    /**
     * Generate a little-endian representation as a byte array.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @return The little-endian byte array.
     */
    public byte[] packed() {
        normalize();

        return normalizedPacked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Val clone();

    /**
     * Get the number of bits in a value.
     *
     * @return The number of bits in a value.
     */
    public abstract int numBits();

    /**
     * Overwrite the value of this number with a {@code long},
     * assuming {@code digits} is already zeroed-out.
     *
     * @param val The {@code long} to copy.
     */
    protected abstract void init(final int val);

    /**
     * Normalize the internal representation.
     * <p>
     * This normalizes the internal digits representation, ensuring
     * that no residual operations need to be performed, and that the
     * internal representation stores a fully reduced value.
     * <p>

     * It is not necessary to perform normalization prior to
     * arithmetic operations, and the {@link #equals}, {@link
     * #toString}, and {@link #pack} perform it automatically.  The
     * {@link #normalizedEquals}, {@link #normalizedToString}, and
     * {@link #normalizedPack} methods assume normalization has
     * already been done.
     *
     * @see #normalizedPack
     * @see #normalizedEquals
     * @see #normalizedToString
     */
    public abstract void normalize();

    /**
     * Get the {@code n}th bit of the number.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @param n Index of the bit to get.
     * @return The {@code n}th bit.
     * @see #bit
     */
    public abstract long bitNormalized(final int n);

    /**
     * Get the sign of the number.  A number {@code n mod p} is
     * considered "positive" if it lies in {@code [0, (n - 1) / 2]},
     * negative otherwise.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @return The sign of the number.
     * @see #sign
     */
    public abstract long signNormalized();

    /**
     * Get the lower bound on values that can be used in {@link
     * #add(long)}.  This is determined by details of the internal
     * representation, namely how many bits in each digit are reserved
     * for carry.
     *
     * @return The lower bound on values that can be used in {@link
     *         #add(long)}.
     */
    public abstract long addMin();

    /**
     * Get the upper bound on values that can be used in {@link
     * #add(long)}.  This is determined by details of the internal
     * representation, namely how many bits in each digit are reserved
     * for carry.
     *
     * @return The upper bound on values that can be used in {@link
     *         #add(long)}.
     */
    public abstract long addMax();

    /**
     * Add a {@code long} to this number.  The argument must be
     * between {@link #addMin()} and {@link #addMax()}.
     * <p>
     * This operation is more efficient than {@link #add(PrimeField)}.
     *
     * @param b The {@code long} to add.
     * @see #addMin()
     * @see #addMax()
     */
    public abstract void add(final long b);

    /**
     * Add a raw internal representation to this number.
     *
     * @param b The internal representation to add.
     */
    protected abstract void add(final long[] b);

    /**
     * Subtract a {@code long} from this number.  The argument must be
     * between {@link #addMin()} and {@link #addMax()}.
     * <p>
     * This operation is more efficient than {@link #sub(PrimeField)}.
     *
     * @param b The {@code long} to subtract.
     * @see #addMin()
     * @see #addMax()
     */
    public abstract void sub(final long b);

    /**
     * Subtract a raw internal representation from this number.
     *
     * @param b The internal representation to subtract.
     */
    protected abstract void sub(final long[] b);

    /**
     * Get the lower bound on values that can be used in {@link
     * #mul(int)}.  This is determined by details of the internal
     * representation, namely how many bits in each digit are reserved
     * for carry.
     *
     * @return The lower bound on values that can be used in {@link
     *         #mul(int)}.
     */
    public abstract int mulMin();

    /**
     * Get the upper bound on values that can be used in {@link
     * #mul(int)}.  This is determined by details of the internal
     * representation, namely how many bits in each digit are reserved
     * for carry.
     *
     * @return The upper bound on values that can be used in {@link
     *         #mul(int)}.
     */
    public abstract int mulMax();

    /**
     * Multiply this number by a {@code int}.  The argument must be
     * between {@link #mulMin()} and {@link #mulMax()}.
     * <p>
     * This operation is considerably more efficient than {@link
     * #mul(PrimeField)}.
     *
     * @param b The {@code int} by which to multiply.
     * @see #mulMin()
     * @see #mulMax()
     */
    public abstract void mul(final int b);

    /**
     * Multiply this number by a raw internal representation.
     *
     * @param b The internal representation by which to multiply.
     */
    protected abstract void mul(final long[] b);

    /**
     * Divide this number by a {@code int}.  This version is
     * <i>not</i> generally more efficient than {@link
     * #div(PrimeField)}.
     *
     * @param b The {@code int} by which to divide.
     */
    public abstract void div(final int b);

    /**
     * Divide this number by a raw internal representation.
     *
     * @param b The internal representation by which to divide.
     */
    protected abstract void div(final long[] b);

    /**
     * Negate the number.
     */
    public abstract void neg();

    /**
     * Take the reciprocal of the number.
     */
    public abstract void inv();

    /**
     * Square this number.
     * <p>
     * This is more efficient than multiplying the number by itself.
     */
    public abstract void square();

    /**
     * Compute the (quadratic) Legendre symbol on this number.
     * <p>
     * A number {@code n} is a <i>quadratic residue</i> {@code mod p}
     * if there exists some {@code m} such that {@code m * m = n mod
     * p} (that is, {@code n} has a square root {@code mod p}).
     * <p>
     * The (quadratic) Legendre symbol on {@code n mod p} evaluates to
     * {@code 1} if the value is a quadratic residue {@code mod p},
     * and {@code -1} if not.
     *
     * @return {@code 1} if the value is a quadratic residue, {@code -1} if not.
     */
    public abstract byte legendre();

    /**
     * Square root the number.
     * <p>
     * As per the laws of modular arithmetic, this only has meaning if
     * the value is a quadratic residue; otherwise, the result is
     * invalid.
     *
     * @see #legendre
     */
    public abstract void sqrt();

    /**
     * Square root the number and then invert it.  This computes
     * {@code 1/sqrt(n)}.
     * <p>
     * As per the laws of modular arithmetic, this only has meaning if
     * the value is a quadratic residue; otherwise, the result is
     * invalid.
     *
     * @see #legendre
     */
    public abstract void invSqrt();

    /**
     * Generate a little-endian representation as a byte array.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @return The little-endian byte array.
     */
    public abstract byte[] normalizedPacked();

    /**
     * Set the value of this number from a packed representation at a
     * specific index.
     *
     * @param packed The packed representation.
     * @param idx The index at which to start.
     */
    public abstract void unpack(final byte[] packed,
                                final int idx);

    /**
     * Set the value of this number from a packed representation read
     * from an {@link java.io.InputStream}.
     *
     * @param stream The {@link java.io.InputStream} from which to
     *               read the packed representation.
     * @throws java.io.IOException If an error occurred while reading
     *                             data.
     */
    public abstract void unpack(final InputStream stream) throws IOException;

    /**
     * Write a little-endian representation into an array at a specific index.
     * <p>
     * This method assumes the internal representation is normalized.
     *
     * @param arr Array into which to write.
     * @param idx Index at which to start.
     * @see #normalize
     * @see #digits
     */
    public abstract void normalizedPack(final byte[] arr,
                                        final int idx);

    /**
     * Check if this number is equal to zero.
     *
     * @return Whether this number is equal to zero.
     */
    public abstract boolean isZero();
}
