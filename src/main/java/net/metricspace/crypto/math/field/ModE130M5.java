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
import java.util.Arrays;

/**
 * Elements of the finite field modulo the pseudo-Mersenne prime
 * {@code 2^130 - 5}.
 * <p>
 * This field is the foundation of the Poly1305 MAC.
 */
public final class ModE130M5 extends PrimeField<ModE130M5> {
    private static final ThreadLocal<Scratchpad> scratchpads =
        new ThreadLocal<Scratchpad>() {
            @Override
            public Scratchpad initialValue() {
                return new Scratchpad(new long[NUM_DIGITS],
                                      new long[NUM_DIGITS],
                                      new long[NUM_DIGITS]);
            }
        };

    /**
     * Number of bits in a value.
     */
    public static final int NUM_BITS = 130;

    /**
     * Number of bytes in a packed representation.
     *
     * @see #pack
     * @see #packed
     * @see #unpack
     */
    public static final int PACKED_BYTES = 17;

    /**
     * Number of digits in an internal representation.
     *
     * @see #digits
     */
    static final int NUM_DIGITS = 3;

    /**
     * Number of bits in a regular digit.
     *
     * @see #digits
     */
    static final int DIGIT_BITS = 58;

    /**
     * Number of carry bits in a regular digit.
     *
     * @see #digits
     */
    static final int CARRY_BITS = 64 - DIGIT_BITS;

    /**
     * Mask for a regular digit.
     *
     * @see #digits
     */
    static final long DIGIT_MASK = 0x03ffffffffffffffL;

    /**
     * Number of bits in the highest digit.
     *
     * @see #digits
     */
    static final int HIGH_DIGIT_BITS = 14;

    /**
     * Number of bits in the highest digit.
     *
     * @see #digits
     */
    static final int HIGH_CARRY_BITS = 64 - HIGH_DIGIT_BITS;

    /**
     * Mask for the highest digit.
     *
     * @see #digits
     */
    static final long HIGH_DIGIT_MASK = 0x0000000000003fffL;

    /**
     * Number of bits in a multiplication digit.
     *
     * @see #digits
     */
    static final int MUL_DIGIT_BITS = 29;

    /**
     * Mask for a multiplication digit.
     *
     * @see #digits
     */
    static final int MUL_DIGIT_MASK = 0x1fffffff;

    static final int MUL_OVERLAP_BITS = DIGIT_BITS - HIGH_DIGIT_BITS;

    /**
     * The value {@code c}, in the pseudo-Mersenne prime form {@code 2^n - c}.
     */
    static final byte C_VAL = 5;

    /**
     * Data for the value {@code 0}.
     */
    private static final long[] ZERO_DATA = new long[] { 0, 0, 0 };

    /**
     * Data for the value {@code 1}.
     */
    private static final long[] ONE_DATA = new long[] { 1, 0, 0 };

    /**
     * Data for the value {@code -1}.
     */
    private static final long[] M_ONE_DATA =
        new long[] { 0x03fffffffffffffaL, 0x03ffffffffffffffL,
                     0x0000000000003fffL };

    /**
     * Data for the modulus value {@code 2^130 - 5}.
     */
    private static final long[] MODULUS_DATA =
        new long[] { 0x03fffffffffffffbL, 0x03ffffffffffffffL,
                     0x0000000000003fffL };

    /**
     * Data for the value {@code (MODULUS - 1) / 2 + C}.
     */
    private static final long[] ABS_DATA =
        new long[] { 0x0000000000000002L, 0x0000000000000000L,
                     0x0000000000002000L };

    /**
     * Create a {@code ModE130M5} initialized to {@code 0}.
     *
     * @return A {@code ModE130M5} initialized to {@code 0}.
     */
    public static ModE130M5 zero() {
        return create(ZERO_DATA);
    }

    /**
     * Create a {@code ModE130M5} initialized to {@code 1}.
     *
     * @return A {@code ModE130M5} initialized to {@code 1}.
     */
    public static ModE130M5 one() {
        return create(ONE_DATA);
    }

    /**
     * Create a {@code ModE130M5} initialized to {@code -1}.
     *
     * @return A {@code ModE130M5} initialized to {@code -1}.
     */
    public static ModE130M5 mone() {
        return create(M_ONE_DATA);
    }

    /**
     * Create a {@code ModE130M5} initialized to a copy of a given
     * digits array.
     *
     * @param data The data to initialize the {@code ModE130M5}.
     * @return data A {@code ModE130M5} initialized from a copy of
     *              {@code data}.
     * @see #digits
     */
    static ModE130M5 create(final long[] data) {
        return new ModE130M5(Arrays.copyOf(data, NUM_DIGITS));
    }

    /**
     * Initialize a {@code ModE130M5} with the given digits array.
     * The array is <i>not</i> copied.
     *
     * @param data The data to initialize the {@code ModE130M5}.
     * @see #digits
     */
    ModE130M5(final long[] data) {
        super(data);
    }

    /**
     * Initialize a {@code ModE130M5} with a fresh digits array.
     */
    private ModE130M5() {
        super(new long[NUM_DIGITS]);
    }

    /**
     * Initialize a {@code ModE130M5} from an {@code int}.
     *
     * @param n The {@code int} to initialize the {@code ModE130M5}.
     */
    public ModE130M5(final int n) {
        this();
        set(n);
    }

    /**
     * Initialize a {@code ModE130M5} from an packed represenation.
     *
     * @param packed The packed representation with which to
     *               initialize the {@code ModE130M5}.
     * @see #pack
     * @see #packed
     * @see #unpack
     */
    public ModE130M5(final byte[] packed) {
        this();
        unpack(packed);
    }

    /**
     * Initialize a {@code ModE130M5} by reading a packed
     * represenation from a {@link java.io.InputStream}.
     *
     * @param stream The {@link java.io.InputStream} from which to
     *               read the packed representation with which to
     *               initialize the {@code ModE130M5}.
     * @throws java.io.IOException If an error occurs reading input.
     * @see #pack
     * @see #packed
     * @see #unpack
    */
    public ModE130M5(final InputStream stream) throws IOException {
        this();
        unpack(stream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModE130M5 clone() {
        return create(digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Scratchpad scratchpad() {
        return scratchpads.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numBits() {
        return NUM_BITS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long addMin() {
        return 0xffffffffffffffffL & ~DIGIT_MASK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long addMax() {
        return DIGIT_MASK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int mulMin() {
        return 0xffffffff & ~MUL_DIGIT_MASK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int mulMax() {
        return MUL_DIGIT_MASK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long bitNormalized(final int n) {
        final int digit_idx = n / DIGIT_BITS;
        final int offset = n % DIGIT_BITS;

        return (digits[digit_idx] >> offset) & 0x1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte sign(final Scratchpad scratch) {
        addDigits(digits, ABS_DATA, scratch.d0);

        return (byte)carryOut(scratch.d0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void add(final long[] b) {
        addDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final long b) {
        addDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void neg() {
        subDigits(ZERO_DATA, digits, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void sub(final long[] b) {
        subDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sub(final long b) {
        subDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mul(final long[] b) {
        mulDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mul(final int b) {
        mulDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void square() {
        squareDigits(digits);
    }

    /**
     * Take the reciprocal of the number.  This is computed by raising
     * the number to the power {@code MODULUS - 2}.  In this field,
     * the value of {@code MODULUS - 2} is {@code
     * 0x3fffffffffffffffffffffffffffffff9}.
     *
     * @param scratch The scratchpad to use.
     */
    @Override
    public void inv(final Scratchpad scratch) {
        invDigits(digits, scratch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init(final int val) {
        initDigits(digits, val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void div(final long[] b,
                       final Scratchpad scratch) {
        System.arraycopy(b, 0, scratch.d2, 0, NUM_DIGITS);
        invDigits(scratch.d2, scratch);
        mul(scratch.d2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void div(final int b,
                    final Scratchpad scratch) {
        initDigits(scratch.d2, b);
        invDigits(scratch.d2, scratch);
        mul(scratch.d2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void normalize(final Scratchpad scratch) {
        normalizeDigits(digits, scratch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object b) {
        if (b instanceof ModE130M5) {
            return equals((ModE130M5)b);
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void normalizedPack(final byte[] bytes,
                               final int idx) {
        packDigits(digits, bytes, idx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unpack(final byte[] bytes,
                       final int idx) {
        digits[0] = ((long)bytes[0 + idx] & 0x00000000000000ffL) |
                    (((long)bytes[1 + idx] << 8) & 0x000000000000ff00L) |
                    (((long)bytes[2 + idx] << 16) & 0x0000000000ff0000L) |
                    (((long)bytes[3 + idx] << 24) & 0x00000000ff000000L) |
                    (((long)bytes[4 + idx] << 32) & 0x000000ff00000000L) |
                    (((long)bytes[5 + idx] << 40) & 0x0000ff0000000000L) |
                    (((long)bytes[6 + idx] << 48) & 0x00ff000000000000L) |
                    (((long)bytes[7 + idx] << 56) & 0x0300000000000000L);
        digits[1] = (((long)bytes[7 + idx] >> 2) & 0x000000000000003fL) |
                    (((long)bytes[8 + idx] << 6) & 0x0000000000003fc0L) |
                    (((long)bytes[9 + idx] << 14) & 0x00000000003fc000L) |
                    (((long)bytes[10 + idx] << 22) & 0x000000003fc00000L) |
                    (((long)bytes[11 + idx] << 30) & 0x0000003fc0000000L) |
                    (((long)bytes[12 + idx] << 38) & 0x00003fc000000000L) |
                    (((long)bytes[13 + idx] << 46) & 0x003fc00000000000L) |
                    (((long)bytes[14 + idx] << 54) & 0x03c0000000000000L);
        digits[2] = (((long)bytes[14 + idx] >> 4) & 0x000000000000000fL) |
                    (((long)bytes[15 + idx] << 4) & 0x0000000000000ff0L) |
                    (((long)bytes[16 + idx] << 12) & 0x0000000000003000L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unpack(final InputStream stream) throws IOException {
        final byte[] bytes = new byte[PACKED_BYTES];

        stream.read(bytes);

        unpack(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] normalizedPacked() {
        final byte[] out = new byte[PACKED_BYTES];

        normalizedPack(out);

        return out;
    }

    /**
     * Square root the number.
     * <p>
     * As per the laws of modular arithmetic, this only has meaning if
     * the value is a quadratic residue; otherwise, the result is
     * invalid.
     * <p>
     * As {@code MODULUS mod 4 = 3}, this is computed by raising the
     * number to the power {@code (MODULUS + 1) / 4} (Lagrange's
     * formula).  On this field, this value is {@code
     * 0x0ffffffffffffffffffffffffffffffff}.
     *
     * @param scratch The scratchpad to use.
     * @see #legendre
     */
    @Override
    public void sqrt(final Scratchpad scratch) {
        sqrtPowerDigits(digits, scratch);
    }

    /**
     * Square root the number then take the multiplicative inverse.
     * <p>
     * As per the laws of modular arithmetic, this only has meaning if
     * the value is a quadratic residue; otherwise, the result is
     * invalid.
     * <p>
     * As {@code MODULUS mod 4 = 3}, this is computed using Lagrange's
     * formula, which raises the number to the power {@code (3 *
     * MODULUS - 5) / 4}.
     * <p>
     * On this field, the exponent value is {@code
     * 0x2fffffffffffffffffffffffffffffffb}.
     *
     * @param scratch The scratchpad to use.
     * @see #legendre
     */
    @Override
    public void invSqrt(final Scratchpad scratch) {
        invSqrtPowerDigits(digits, scratch);
    }

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
     * <p>
     * This is computed by raising the number to the power {@code
     * (MODULUS - 1) / 2}.  On this field, this value is {@code
     * 0x1fffffffffffffffffffffffffffffffd}.
     *
     * @param scratch The scratchpad to use.
     * @return {@code 1} if the value is a quadratic residue, {@code -1} if not.
     */
    @Override
    public byte legendre(final Scratchpad scratch) {
        System.arraycopy(digits, 0, scratch.d2, 0, NUM_DIGITS);

        legendrePowerDigits(scratch.d2, scratch);
        normalizeDigits(scratch.d2, scratch);

        final long low = (scratch.d2[0] << CARRY_BITS) >>> CARRY_BITS;
        final byte sign = (byte)(low >>> (DIGIT_BITS - 1));
        final byte offset = (byte)(C_VAL * sign);
        final byte result = (byte)(low + offset);

        return result;
    }

    /**
     * Get the residual carry-out value from the highest digit.
     *
     * @param digits The digits array.
     * @return The residual carry-out value.
     * @see #digits
     */
    private static short carryOut(final long[] digits) {
        return (short)(digits[NUM_DIGITS - 1] >> HIGH_DIGIT_BITS);
    }

    /**
     * Perform normalization on low-level representations.
     *
     * @param digits The low-level representation.
     * @param scratch The scratchpad to use.
     * @see #normalize
     */
    private static void normalizeDigits(final long[] digits,
                                        final Scratchpad scratch) {
        final long[] offset = scratch.d0;
        final long[] plusc = scratch.d1;

        System.arraycopy(MODULUS_DATA, 0, offset, 0, NUM_DIGITS);
        System.arraycopy(digits, 0, plusc, 0, NUM_DIGITS);
        addDigits(plusc, C_VAL, plusc);
        mulDigits(offset, carryOut(plusc), offset);
        subDigits(digits, offset, digits);
    }

    private static void packDigits(final long[] digits,
                                   final byte[] bytes,
                                   final int idx) {
        bytes[0 + idx] = (byte)(digits[0] & 0xff);
        bytes[1 + idx] = (byte)((digits[0] >> 8) & 0xff);
        bytes[2 + idx] = (byte)((digits[0] >> 16) & 0xff);
        bytes[3 + idx] = (byte)((digits[0] >> 24) & 0xff);
        bytes[4 + idx] = (byte)((digits[0] >> 32) & 0xff);
        bytes[5 + idx] = (byte)((digits[0] >> 40) & 0xff);
        bytes[6 + idx] = (byte)((digits[0] >> 48) & 0xff);
        bytes[7 + idx] = (byte)(((digits[0] >> 56) & 0x03) |
                                ((digits[1] << 2) & 0xfc));
        bytes[8 + idx] = (byte)((digits[1] >> 6) & 0xff);
        bytes[9 + idx] = (byte)((digits[1] >> 14) & 0xff);
        bytes[10 + idx] = (byte)((digits[1] >> 22) & 0xff);
        bytes[11 + idx] = (byte)((digits[1] >> 30) & 0xff);
        bytes[12 + idx] = (byte)((digits[1] >> 38) & 0xff);
        bytes[13 + idx] = (byte)((digits[1] >> 46) & 0xff);
        bytes[14 + idx] = (byte)(((digits[1] >> 54) & 0x0f) |
                                 ((digits[2] << 4) & 0xf0));
        bytes[15 + idx] = (byte)((digits[2] >> 4) & 0xff);
        bytes[16 + idx] = (byte)((digits[2] >> 12) & 0x03);
    }

    /**
     * Low-level digits addition.  It <i>is</i> safe to specify the same
     * array as both an input and an output.
     *
     * @param a The LHS digit array.
     * @param b The RHS digit array.
     * @param out The digit array into which to write the result.
     */
    private static void addDigits(final long[] a,
                                  final long[] b,
                                  final long[] out) {
        final long a0 = a[0];
        final long a1 = a[1];
        final long a2 = a[2] & HIGH_DIGIT_MASK;

        final long b0 = b[0];
        final long b1 = b[1];
        final long b2 = b[2] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a) + carryOut(b);
        final long s0 = a0 + b0 + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 + b1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 + b2 + c1;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2;
    }

    /**
     * Low-level digit-small value addition.  It <i>is</i> safe to
     * specify the same array as both an input and an output.
     *
     * @param a The LHS digit array.
     * @param b The RHS value.
     * @param out The digit array into which to write the result.
     */
    private static void addDigits(final long[] a,
                                  final long b,
                                  final long[] out) {
        final long a0 = a[0];
        final long a1 = a[1];
        final long a2 = a[2] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a);
        final long s0 = a0 + b + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 + c1;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2;
    }

    /**
     * Low-level digits subtraction.  It <i>is</i> safe to specify the same
     * array as both an input and an output.
     *
     * @param a The LHS digit array.
     * @param b The RHS digit array.
     * @param out The digit array into which to write the result.
     */
    private static void subDigits(final long[] a,
                                  final long[] b,
                                  final long[] out) {
        final long a0 = a[0];
        final long a1 = a[1];
        final long a2 = a[2] & HIGH_DIGIT_MASK;

        final long b0 = b[0];
        final long b1 = b[1];
        final long b2 = b[2] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a) - carryOut(b);
        final long s0 = a0 - b0 + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 - b1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 - b2 + c1;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2;
    }

    /**
     * Low-level digit-small value subtraction.  It <i>is</i> safe to
     * specify the same array as both an input and an output.
     *
     * @param a The LHS digit array.
     * @param b The RHS value.
     * @param out The digit array into which to write the result.
     */
    private static void subDigits(final long[] a,
                                  final long b,
                                  final long[] out) {
        final long a0 = a[0];
        final long a1 = a[1];
        final long a2 = a[2] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a);
        final long s0 = a0 - b + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 + c1;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2;
    }

    /**
     * Low-level digits multiplication.  It <i>is</i> safe to specify
     * the same array as both an input and an output.
     *
     * @param a The LHS digit array.
     * @param b The RHS digit array.
     * @param out The digit array into which to write the result.
     */
    private static void mulDigits(final long[] a,
                                  final long[] b,
                                  final long[] out) {
        final long a0 = a[0] & MUL_DIGIT_MASK;
        final long a1 = a[0] >> MUL_DIGIT_BITS;
        final long a2 = a[1] & MUL_DIGIT_MASK;
        final long a3 = a[1] >> MUL_DIGIT_BITS;
        final long a4 = a[2];

        final long b0 = b[0] & MUL_DIGIT_MASK;
        final long b1 = b[0] >> MUL_DIGIT_BITS;
        final long b2 = b[1] & MUL_DIGIT_MASK;
        final long b3 = b[1] >> MUL_DIGIT_BITS;
        final long b4 = b[2];

        // Combined multiples
        final long m_0_0 = a0 * b0;
        final long m_0_1 = a0 * b1;
        final long m_0_2 = a0 * b2;
        final long m_0_3 = a0 * b3;
        final long m_0_4 = a0 * b4;
        final long m_1_0 = a1 * b0;
        final long m_1_1 = a1 * b1;
        final long m_1_2 = a1 * b2;
        final long m_1_3 = a1 * b3;
        final long m_1_4 = a1 * b4;
        final long m_2_0 = a2 * b0;
        final long m_2_1 = a2 * b1;
        final long m_2_2 = a2 * b2;
        final long m_2_3 = a2 * b3;
        final long m_2_4 = a2 * b4;
        final long m_3_0 = a3 * b0;
        final long m_3_1 = a3 * b1;
        final long m_3_2 = a3 * b2;
        final long m_3_3 = a3 * b3;
        final long m_3_4 = a3 * b4;
        final long m_4_0 = a4 * b0;
        final long m_4_1 = a4 * b1;
        final long m_4_2 = a4 * b2;
        final long m_4_3 = a4 * b3;
        final long m_4_4 = a4 * b4;

        // Compute the combined product using 64-bit operations.
        final long d0 =
            m_0_0 + ((m_0_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_1_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS);
        final long c0 = d0 >> DIGIT_BITS;
        final long d1 =
            (m_0_1 >> MUL_DIGIT_BITS) + m_0_2 +
            ((m_0_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_0 >> MUL_DIGIT_BITS) + m_1_1 +
            ((m_1_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_2_0 + ((m_2_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_3_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c0;
        final long c1 = d1 >> DIGIT_BITS;
        final long d2 =
            (m_0_3 >> MUL_DIGIT_BITS) + m_0_4 +
            (m_1_2 >> MUL_DIGIT_BITS) + m_1_3 +
            ((m_1_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_1 >> MUL_DIGIT_BITS) + m_2_2 +
            ((m_2_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_0 >> MUL_DIGIT_BITS) + m_3_1 +
            ((m_3_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_4_0 + ((m_4_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c1;
        final long c2 = d2 >> DIGIT_BITS;
        final long d3 =
            (m_1_4 >> MUL_DIGIT_BITS) +
            (m_2_3 >> MUL_DIGIT_BITS) + m_2_4 +
            (m_3_2 >> MUL_DIGIT_BITS) + m_3_3 +
            ((m_3_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_1 >> MUL_DIGIT_BITS) + m_4_2 +
            ((m_4_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c2;
        final long c3 = d3 >> DIGIT_BITS;
        final long d4 =
            (m_3_4 >> MUL_DIGIT_BITS) +
            (m_4_3 >> MUL_DIGIT_BITS) + m_4_4 +
            c3;

        // Modular reduction by a pseudo-mersenne prime of the form 2^n - c.

        // These are the n low-order
        final long l0_0 = d0 & DIGIT_MASK;
        final long l1_0 = d1 & DIGIT_MASK;
        final long l2_0 = d2 & HIGH_DIGIT_MASK;

        // Shift the high bits down into another n-bit number.
        final long h0_0 = ((d2 & DIGIT_MASK) >> HIGH_DIGIT_BITS) |
                          ((d3 & HIGH_DIGIT_MASK) << 44);
        final long h1_0 = ((d3 & DIGIT_MASK) >> HIGH_DIGIT_BITS) |
                          ((d4 & HIGH_DIGIT_MASK) << 44);
        final long h2_0 = d4 >> HIGH_DIGIT_BITS;

        // Multiply by C
        final long hc0_0 = h0_0 * C_VAL;
        final long hc1_0 = h1_0 * C_VAL;
        final long hc2_0 = h2_0 * C_VAL;

        // Add h and l.
        final long kin_0 = hc2_0 >> HIGH_DIGIT_BITS;
        final long s0_0 = l0_0 + hc0_0 + (kin_0 * C_VAL);
        final long k0_0 = s0_0 >> DIGIT_BITS;
        final long s1_0 = l1_0 + hc1_0 + k0_0;
        final long k1_0 = s1_0 >> DIGIT_BITS;
        final long s2_0 = l2_0 + (hc2_0 & HIGH_DIGIT_MASK) + k1_0;

        out[0] = s0_0 & DIGIT_MASK;
        out[1] = s1_0 & DIGIT_MASK;
        out[2] = s2_0;
    }

    /**
     * Low-level digit-small value multiplication.  It <i>is</i> safe
     * to specify the same array as both an input and an output.
     *
     * @param a The LHS digit array.
     * @param b The RHS value.
     * @param out The digit array into which to write the result.
     */
    private static void mulDigits(final long[] a,
                                  final int b,
                                  final long[] out) {
        final long a0 = a[0] & MUL_DIGIT_MASK;
        final long a1 = a[0] >> MUL_DIGIT_BITS;
        final long a2 = a[1] & MUL_DIGIT_MASK;
        final long a3 = a[1] >> MUL_DIGIT_BITS;
        final long a4 = a[2] & HIGH_DIGIT_MASK;

        final long m0 = a0 * b;
        final long m1 = a1 * b;
        final long m2 = a2 * b;
        final long m3 = a3 * b;
        final long m4 = a4 * b;

        final long cin = carryOut(a);
        final long d0 =
            m0 + ((m1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (cin * C_VAL * b);
        final long c0 = d0 >> DIGIT_BITS;
        final long d1 =
            (m1 >> MUL_DIGIT_BITS) + m2 +
            ((m3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c0;
        final long c1 = d1 >> DIGIT_BITS;
        final long d2 =
            (m3 >> MUL_DIGIT_BITS) + m4 +
            c1;

        final long kin = d2 >> HIGH_DIGIT_BITS;
        final long s0 = (d0 & DIGIT_MASK) + (kin * C_VAL);
        final long k0 = s0 >> DIGIT_BITS;
        final long s1 = (d1 & DIGIT_MASK) + k0;
        final long k1 = s1 >> DIGIT_BITS;
        final long s2 = (d2 & HIGH_DIGIT_MASK) + k1;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2;
    }

    /**
     * Low-level digits squaring.
     *
     * @param digits The digits array to square.
     */
    private static void squareDigits(final long[] digits) {
        final long a0 = digits[0] & MUL_DIGIT_MASK;
        final long a1 = digits[0] >> MUL_DIGIT_BITS;
        final long a2 = digits[1] & MUL_DIGIT_MASK;
        final long a3 = digits[1] >> MUL_DIGIT_BITS;
        final long a4 = digits[2];

        // Combined multiples
        final long m_0_0 = a0 * a0;
        final long m_0_1 = a0 * a1;
        final long m_0_2 = a0 * a2;
        final long m_0_3 = a0 * a3;
        final long m_0_4 = a0 * a4;
        final long m_1_0 = m_0_1;
        final long m_1_1 = a1 * a1;
        final long m_1_2 = a1 * a2;
        final long m_1_3 = a1 * a3;
        final long m_1_4 = a1 * a4;
        final long m_2_0 = m_0_2;
        final long m_2_1 = m_1_2;
        final long m_2_2 = a2 * a2;
        final long m_2_3 = a2 * a3;
        final long m_2_4 = a2 * a4;
        final long m_3_0 = m_0_3;
        final long m_3_1 = m_1_3;
        final long m_3_2 = m_2_3;
        final long m_3_3 = a3 * a3;
        final long m_3_4 = a3 * a4;
        final long m_4_0 = m_0_4;
        final long m_4_1 = m_1_4;
        final long m_4_2 = m_2_4;
        final long m_4_3 = m_3_4;
        final long m_4_4 = a4 * a4;

        // Compute the combined product using 64-bit operations.
        final long d0 =
            m_0_0 + ((m_0_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_1_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS);
        final long c0 = d0 >> DIGIT_BITS;
        final long d1 =
            (m_0_1 >> MUL_DIGIT_BITS) + m_0_2 +
            ((m_0_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_0 >> MUL_DIGIT_BITS) + m_1_1 +
            ((m_1_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_2_0 + ((m_2_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_3_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c0;
        final long c1 = d1 >> DIGIT_BITS;
        final long d2 =
            (m_0_3 >> MUL_DIGIT_BITS) + m_0_4 +
            (m_1_2 >> MUL_DIGIT_BITS) + m_1_3 +
            ((m_1_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_1 >> MUL_DIGIT_BITS) + m_2_2 +
            ((m_2_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_0 >> MUL_DIGIT_BITS) + m_3_1 +
            ((m_3_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_4_0 + ((m_4_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c1;
        final long c2 = d2 >> DIGIT_BITS;
        final long d3 =
            (m_1_4 >> MUL_DIGIT_BITS) +
            (m_2_3 >> MUL_DIGIT_BITS) + m_2_4 +
            (m_3_2 >> MUL_DIGIT_BITS) + m_3_3 +
            ((m_3_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_1 >> MUL_DIGIT_BITS) + m_4_2 +
            ((m_4_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c2;
        final long c3 = d3 >> DIGIT_BITS;
        final long d4 =
            (m_3_4 >> MUL_DIGIT_BITS) +
            (m_4_3 >> MUL_DIGIT_BITS) + m_4_4 +
            c3;

        // Modular reduction by a pseudo-mersenne prime of the form 2^n - c.

        // These are the n low-order
        final long l0_0 = d0 & DIGIT_MASK;
        final long l1_0 = d1 & DIGIT_MASK;
        final long l2_0 = d2 & HIGH_DIGIT_MASK;

        // Shift the high bits down into another n-bit number.
        final long h0_0 = ((d2 & DIGIT_MASK) >> HIGH_DIGIT_BITS) |
                          ((d3 & HIGH_DIGIT_MASK) << 44);
        final long h1_0 = ((d3 & DIGIT_MASK) >> HIGH_DIGIT_BITS) |
                          ((d4 & HIGH_DIGIT_MASK) << 44);
        final long h2_0 = d4 >> HIGH_DIGIT_BITS;

        // Multiply by C
        final long hc0_0 = h0_0 * C_VAL;
        final long hc1_0 = h1_0 * C_VAL;
        final long hc2_0 = h2_0 * C_VAL;

        // Add h and l.
        final long kin_0 = hc2_0 >> HIGH_DIGIT_BITS;
        final long s0_0 = l0_0 + hc0_0 + (kin_0 * C_VAL);
        final long k0_0 = s0_0 >> DIGIT_BITS;
        final long s1_0 = l1_0 + hc1_0 + k0_0;
        final long k1_0 = s1_0 >> DIGIT_BITS;
        final long s2_0 = l2_0 + (hc2_0 & HIGH_DIGIT_MASK) + k1_0;

        digits[0] = s0_0 & DIGIT_MASK;
        digits[1] = s1_0 & DIGIT_MASK;
        digits[2] = s2_0;
    }

    /**
     * Low-level digits multiplicative inverse (reciprocal).
     *
     * @param digits The digits array to invert.
     * @param scratch The scratchpad to use.
     */
    private static void invDigits(final long[] digits,
                                  final Scratchpad scratch) {
        // First digit is 1.
        final long[] sqval = scratch.d0;

        System.arraycopy(digits, 0, sqval, 0, NUM_DIGITS);

        // Second and third digits are 0.
        squareDigits(sqval);
        squareDigits(sqval);

        // All the remaining digits are 1.
        for(int i = 3; i < 130; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }
    }

    /**
     * Low-level digits initialization from an {@code int}.
     *
     * @param digits The digits array to initalize.
     * @param val The {@code int} from which to initialize.
     */
    private static void initDigits(final long[] digits,
                                   final int val) {
        Arrays.fill(digits, 0);
        addDigits(digits, val, digits);
    }

    private static void sqrtPowerDigits(final long[] digits,
                                        final Scratchpad scratch) {
        // First digit is 1.
        final long[] sqval = scratch.d0;

        System.arraycopy(digits, 0, sqval, 0, NUM_DIGITS);

        // All remaining digits are 1.
        for (int i = 1; i < 128; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }
    }

    private static void invSqrtPowerDigits(final long[] digits,
                                           final Scratchpad scratch) {
        // First digit is 1.
        final long[] sqval = scratch.d0;

        System.arraycopy(digits, 0, sqval, 0, NUM_DIGITS);

        // Second digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // Third digit is 0.
        squareDigits(sqval);

        // Fourth digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // All digits up to 128 are 1.
        for(int i = 4; i < 128; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }

        // 128th digit is 0.
        squareDigits(sqval);

        // Last digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);
    }


    private static void legendrePowerDigits(final long[] digits,
                                            final Scratchpad scratch) {
        // First digit is 1.
        final long[] sqval = scratch.d0;

        System.arraycopy(digits, 0, sqval, 0, NUM_DIGITS);

        // Second digit is 0.
        squareDigits(sqval);

        // Third digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // All the remaining digits are 1.
        for(int i = 3; i < 129; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }
    }

    /*
    private static String digitsToString(final long[] digits) {
        final long[] digitscpy = Arrays.copyOf(digits, NUM_DIGITS);
        final byte[] bytes = new byte[PACKED_BYTES];

        normalizeDigits(digitscpy);
        packDigits(digitscpy, bytes, 0);

        return PrimeField.packedToString(bytes);
    }
    */
}
