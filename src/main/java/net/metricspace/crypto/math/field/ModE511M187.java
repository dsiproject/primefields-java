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
 * {@code 2^511 - 187}.
 * <p>
 * This field is the foundation of the M-511 curve.
 */
public final class ModE511M187 extends PrimeField1Mod4<ModE511M187> {
    /**
     * Number of bits in a value.
     */
    public static final int NUM_BITS = 511;

    /**
     * Number of bytes in a packed representation.
     *
     * @see #pack
     * @see #packed
     * @see #unpack
     */
    public static final int PACKED_BYTES = 64;

    /**
     * Number of digits in an internal representation.
     *
     * @see #digits
     */
    static final int NUM_DIGITS = 10;

    /**
     * Number of bits in a regular digit.
     *
     * @see #digits
     */
    static final int DIGIT_BITS = 54;

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
    static final long DIGIT_MASK = 0x003fffffffffffffL;

    /**
     * Number of bits in the highest digit.
     *
     * @see #digits
     */
    static final int HIGH_DIGIT_BITS = 25;

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
    static final long HIGH_DIGIT_MASK = 0x0000000001ffffffL;

    /**
     * Number of bits in a multiplication digit.
     *
     * @see #digits
     */
    static final int MUL_DIGIT_BITS = 27;

    /**
     * Mask for a multiplication digit.
     *
     * @see #digits
     */
    static final int MUL_DIGIT_MASK = 0x07ffffff;

    static final int MUL_OVERLAP_BITS = DIGIT_BITS - HIGH_DIGIT_BITS;

    /**
     * The value {@code c}, in the pseudo-Mersenne prime form {@code 2^n - c}.
     */
    static final short C_VAL = 187;

    /**
     * Data for the value {@code 0}.
     */
    private static final long[] ZERO_DATA =
        new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    /**
     * Data for the value {@code 1}.
     */
    private static final long[] ONE_DATA =
        new long[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    /**
     * Data for the value {@code -1}.
     */
    private static final long[] M_ONE_DATA =
        new long[] { 0x003fffffffffff44L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x0000000001ffffffL };

    /**
     * Data for the modulus value {@code 2^511 - 187}.
     */
    private static final long[] MODULUS_DATA =
        new long[] { 0x003fffffffffff45L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x0000000001ffffffL };

    /**
     * Data for the value {@code 1/2}.
     */
    private static final long[] HALF_DATA =
        new long[] { 0x003fffffffffffa2L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x0000000001ffffffL };

    /**
     * The value {@code 2 ^ ((MODULUS - 1) / 4) - 1}.  Used in the
     * computation of square roots.  The value of this is one less
     * than {@code
     * 0x0ecda5605ac73ca62d360dd35afc67fcb2d9d627c653c0cdcff56cdde9b115862a4216356433c83fdf4744e63847e9b2a6526b3ddabf1019abca2025f19a0806}.
     */
    private static final long[] SQRT_COEFF_M1;

    static {
        SQRT_COEFF_M1 = new long[] { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        legendreQuarticPowerDigits(SQRT_COEFF_M1);
        subDigits(SQRT_COEFF_M1, 1, SQRT_COEFF_M1);
        normalizeDigits(SQRT_COEFF_M1);
    }

    /**
     * The value {@code 2 ^ (3 * (MODULUS - 1) / 4) - 1}.  Used in the
     * computation of inverse square roots.  The value of this is one
     * less than {@code
     * 0x71325a9fa538c359d2c9f22ca50398034d2629d839ac3f32300a9322164eea79d5bde9ca9bcc37c020b8bb19c7b8164d59ad94c22540efe65435dfda0e65f73f}.
     */
    private static final long[] INV_SQRT_COEFF_M1;

    static {
        INV_SQRT_COEFF_M1 = new long[] { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        legendreQuarticPowerDigits(INV_SQRT_COEFF_M1);
        invDigits(INV_SQRT_COEFF_M1);
        subDigits(INV_SQRT_COEFF_M1, 1, INV_SQRT_COEFF_M1);
        normalizeDigits(INV_SQRT_COEFF_M1);
    }

    /**
     * Create a {@code ModE511M187} initialized to {@code 0}.
     *
     * @return A {@code ModE511M187} initialized to {@code 0}.
     */
    public static ModE511M187 zero() {
        return create(ZERO_DATA);
    }

    /**
     * Create a {@code ModE511M187} initialized to {@code 1}.
     *
     * @return A {@code ModE511M187} initialized to {@code 1}.
     */
    public static ModE511M187 one() {
        return create(ONE_DATA);
    }

    /**
     * Create a {@code ModE511M187} initialized to {@code 1/2}.
     *
     * @return A {@code ModE511M187} initialized to {@code 1/2}.
     */
    public static ModE511M187 half() {
        return create(HALF_DATA);
    }

    /**
     * Create a {@code ModE511M187} initialized to {@code -1}.
     *
     * @return A {@code ModE511M187} initialized to {@code -1}.
     */
    public static ModE511M187 mone() {
        return create(M_ONE_DATA);
    }

    /**
     * Create a {@code ModE511M187} initialized to a copy of a given
     * digits array.
     *
     * @param data The data to initialize the {@code ModE511M187}.
     * @return data A {@code ModE511M187} initialized from a copy of
     *              {@code data}.
     * @see #digits
     */
    static ModE511M187 create(final long[] data) {
        return new ModE511M187(Arrays.copyOf(data, NUM_DIGITS));
    }

    /**
     * Initialize a {@code ModE511M187} with the given digits array.
     * The array is <i>not</i> copied.
     *
     * @param data The data to initialize the {@code ModE511M187}.
     * @see #digits
     */
    ModE511M187(final long[] data) {
        super(data);
    }

    /**
     * Initialize a {@code ModE511M187} with a fresh digits array.
     */
    private ModE511M187() {
        super(new long[NUM_DIGITS]);
    }

    /**
     * Initialize a {@code ModE511M187} from an {@code int}.
     *
     * @param n The {@code int} to initialize the {@code ModE511M187}.
     */
    public ModE511M187(final int n) {
        this();
        set(n);
    }

    /**
     * Initialize a {@code ModE511M187} from an packed represenation.
     *
     * @param packed The packed representation with which to
     *               initialize the {@code ModE511M187}.
     * @see #pack
     * @see #packed
     * @see #unpack
     */
    public ModE511M187(final byte[] packed) {
        this();
        unpack(packed);
    }

    /**
     * Initialize a {@code ModE511M187} by reading a packed
     * represenation from a {@link java.io.InputStream}.
     *
     * @param stream The {@link java.io.InputStream} from which to
     *               read the packed representation with which to
     *               initialize the {@code ModE511M187}.
     * @throws java.io.IOException If an error occurs reading input.
     * @see #pack
     * @see #packed
     * @see #unpack
    */
    public ModE511M187(final InputStream stream) throws IOException {
        this();
        unpack(stream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModE511M187 clone() {
        return create(digits);
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
    public long signNormalized() {
        return bit(NUM_BITS - 1);
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
    public void add(final int b) {
        addDigits(digits, b, digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void neg() {
        add(MODULUS_DATA);
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
    public void sub(final int b) {
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
    public void mul(final short b) {
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
     * 0x7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff43}.
     */
    @Override
    public void inv() {
        invDigits(digits);
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
    protected void div(final long[] b) {
        final long[] copied = Arrays.copyOf(b, NUM_DIGITS);

        invDigits(copied);
        mul(copied);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void div(final int b) {
        final long[] divisor = new long[NUM_DIGITS];

        initDigits(divisor, b);
        div(divisor);
    }

    /**
     * {@inheritDoc}
     */
    public void normalize() {
        normalizeDigits(digits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object b) {
        if (b instanceof ModE511M187) {
            return equals((ModE511M187)b);
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
        assert(bytes.length - idx >= PACKED_BYTES);
        packDigits(digits, bytes, idx);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unpack(final byte[] bytes,
                       final int idx) {
        digits[0] = ((long)bytes[0] & 0x00000000000000ffL) |
                    (((long)bytes[1] << 8) & 0x000000000000ff00L) |
                    (((long)bytes[2] << 16) & 0x0000000000ff0000L) |
                    (((long)bytes[3] << 24) & 0x00000000ff000000L) |
                    (((long)bytes[4] << 32) & 0x000000ff00000000L) |
                    (((long)bytes[5] << 40) & 0x0000ff0000000000L) |
                    (((long)bytes[6] << 48) & 0x003f000000000000L);
        digits[1] = (((long)bytes[6] >> 6) & 0x0000000000000003L) |
                    (((long)bytes[7] << 2) & 0x00000000000003fcL) |
                    (((long)bytes[8] << 10) & 0x000000000003fc00L) |
                    (((long)bytes[9] << 18) & 0x0000000003fc0000L) |
                    (((long)bytes[10] << 26) & 0x00000003fc000000L) |
                    (((long)bytes[11] << 34) & 0x000003fc00000000L) |
                    (((long)bytes[12] << 42) & 0x0003fc0000000000L) |
                    (((long)bytes[13] << 50) & 0x003c000000000000L);
        digits[2] = (((long)bytes[13] >> 4) & 0x000000000000000fL) |
                    (((long)bytes[14] << 4) & 0x0000000000000ff0L) |
                    (((long)bytes[15] << 12) & 0x00000000000ff000L) |
                    (((long)bytes[16] << 20) & 0x000000000ff00000L) |
                    (((long)bytes[17] << 28) & 0x0000000ff0000000L) |
                    (((long)bytes[18] << 36) & 0x00000ff000000000L) |
                    (((long)bytes[19] << 44) & 0x000ff00000000000L) |
                    (((long)bytes[20] << 52) & 0x0030000000000000L);
        digits[3] = (((long)bytes[20] >> 2) & 0x000000000000003fL) |
                    (((long)bytes[21] << 6) & 0x0000000000003fc0L) |
                    (((long)bytes[22] << 14) & 0x00000000003fc000L) |
                    (((long)bytes[23] << 22) & 0x000000003fc00000L) |
                    (((long)bytes[24] << 30) & 0x0000003fc0000000L) |
                    (((long)bytes[25] << 38) & 0x00003fc000000000L) |
                    (((long)bytes[26] << 46) & 0x003fc00000000000L);
        digits[4] = ((long)bytes[27] & 0x00000000000000ffL) |
                    (((long)bytes[28] << 8) & 0x000000000000ff00L) |
                    (((long)bytes[29] << 16) & 0x0000000000ff0000L) |
                    (((long)bytes[30] << 24) & 0x00000000ff000000L) |
                    (((long)bytes[31] << 32) & 0x000000ff00000000L) |
                    (((long)bytes[32] << 40) & 0x0000ff0000000000L) |
                    (((long)bytes[33] << 48) & 0x003f000000000000L);
        digits[5] = (((long)bytes[33] >> 6) & 0x0000000000000003L) |
                    (((long)bytes[34] << 2) & 0x00000000000003fcL) |
                    (((long)bytes[35] << 10) & 0x000000000003fc00L) |
                    (((long)bytes[36] << 18) & 0x0000000003fc0000L) |
                    (((long)bytes[37] << 26) & 0x00000003fc000000L) |
                    (((long)bytes[38] << 34) & 0x000003fc00000000L) |
                    (((long)bytes[39] << 42) & 0x0003fc0000000000L) |
                    (((long)bytes[40] << 50) & 0x003c000000000000L);
        digits[6] = (((long)bytes[40] >> 4) & 0x000000000000000fL) |
                    (((long)bytes[41] << 4) & 0x0000000000000ff0L) |
                    (((long)bytes[42] << 12) & 0x00000000000ff000L) |
                    (((long)bytes[43] << 20) & 0x000000000ff00000L) |
                    (((long)bytes[44] << 28) & 0x0000000ff0000000L) |
                    (((long)bytes[45] << 36) & 0x00000ff000000000L) |
                    (((long)bytes[46] << 44) & 0x000ff00000000000L) |
                    (((long)bytes[47] << 52) & 0x0030000000000000L);
        digits[7] = (((long)bytes[47] >> 2) & 0x000000000000003fL) |
                    (((long)bytes[48] << 6) & 0x0000000000003fc0L) |
                    (((long)bytes[49] << 14) & 0x00000000003fc000L) |
                    (((long)bytes[50] << 22) & 0x000000003fc00000L) |
                    (((long)bytes[51] << 30) & 0x0000003fc0000000L) |
                    (((long)bytes[52] << 38) & 0x00003fc000000000L) |
                    (((long)bytes[53] << 46) & 0x003fc00000000000L);
        digits[8] = ((long)bytes[54] & 0x00000000000000ffL) |
                    (((long)bytes[55] << 8) & 0x000000000000ff00L) |
                    (((long)bytes[56] << 16) & 0x0000000000ff0000L) |
                    (((long)bytes[57] << 24) & 0x00000000ff000000L) |
                    (((long)bytes[58] << 32) & 0x000000ff00000000L) |
                    (((long)bytes[59] << 40) & 0x0000ff0000000000L) |
                    (((long)bytes[60] << 48) & 0x003f000000000000L);
        digits[9] = (((long)bytes[60] >> 6) & 0x0000000000000003L) |
                    (((long)bytes[61] << 2) & 0x00000000000003fcL) |
                    (((long)bytes[62] << 10) & 0x000000000003fc00L) |
                    (((long)bytes[63] << 18) & 0x0000000001fc0000L);

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
     * As {@code MODULUS mod 4 = 1} and {@code MODULUS mod 8 = 5},
     * this is computed using Legendre's formula, which raises the
     * number to the power {@code (MODULUS + 3) / 8} and multiplies by
     * {@code 2 ^ ((MODULUS - 1) / 4)} (the quartic legendre symbol
     * for {@code 2} if the original number is a quartic non-residue.
     * <p>
     * On this field, the exponent value is {@code
     * 0x0fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe9}.
     *
     * @see #legendre
     */
    @Override
    public void sqrt() {
        // Legendre's formula for 5 mod 8 primes.
        final byte leg = legendreQuartic();

        sqrtPowerDigits(digits);

        final byte onezero = (byte)((-leg + 1) / 2);
        final long[] coeff = SQRT_COEFF_M1.clone();

        // Multiply 2 ^ (3 * (P - 1) / 4) - 1 by 0 for quartic residue, 1
        // otherwise.
        mulDigits(coeff, onezero, coeff);
        // Add 1, now 1 for quartic residue, 2 ^ (3 * (P - 1) / 4) otherwise.
        addDigits(coeff, 1, coeff);
        mul(coeff);
    }

    /**
     * Square root the number then take the multiplicative inverse.
     * <p>
     * As per the laws of modular arithmetic, this only has meaning if
     * the value is a quadratic residue; otherwise, the result is
     * invalid.
     * <p>
     * As {@code MODULUS mod 4 = 1} and {@code MODULUS mod 8 = 5},
     * this is computed using Legendre's formula, which raises the
     * number to the power {@code (7 * MODULUS - 11) / 8} and multiplies by
     * {@code 2 ^ (3 * (MODULUS - 1) / 4)} (the quartic legendre symbol
     * for {@code 2} if the original number is a quartic non-residue.
     * <p>
     * On this field, the exponent value is {@code
     * 0x6fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff5b}.
     *
     * @see #legendre
     */
    @Override
    public void invSqrt() {
        // Legendre's formula for 5 mod 8 primes.
        final byte leg = legendreQuartic();

        invSqrtPowerDigits(digits);

        final byte onezero = (byte)((-leg + 1) / 2);
        final long[] coeff = INV_SQRT_COEFF_M1.clone();

        // Multiply 2 ^ ((3P - 3) / 4) - 1 by 0 for quartic residue, 1
        // otherwise.
        mulDigits(coeff, onezero, coeff);
        // Add 1, now 1 for quartic residue, 2 ^ ((P - 1) / 4) otherwise.
        addDigits(coeff, 1, coeff);
        mul(coeff);
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
     * 0x3fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffa2}.
     *
     * @return {@code 1} if the value is a quadratic residue, {@code -1} if not.
     */
    @Override
    public byte legendre() {
        final long[] out = Arrays.copyOf(digits, NUM_DIGITS);

        legendrePowerDigits(out);
        normalizeDigits(out);

        final long low = (out[0] << CARRY_BITS) >>> CARRY_BITS;
        final byte sign = (byte)(low >>> (DIGIT_BITS - 1));
        final byte offset = (byte)(C_VAL * sign);
        final byte result = (byte)(low + offset);

        return result;
    }

    /**
     * Compute the quartic Legendre symbol on this number.
     * <p>
     * A number {@code n} is a <i>quartic residue</i> {@code mod p}
     * if there exists some {@code m} such that {@code m * m * m * m = n mod
     * p} (that is, {@code n} has a quartic root {@code mod p}).
     * <p>
     * The quartic Legendre symbol on {@code n mod p} evaluates to
     * {@code 1} if the value is a quartic residue {@code mod p},
     * and {@code -1} if not.
     * <p>
     * This function is only guaranteed to produce a meaningful result
     * if the input is a quadratic residue (meaning {@link #legendre}
     * yields {@code 1}.
     * <p>
     * This is computed by raising the number to the power {@code
     * (MODULUS - 1) / 4}.  On this field, this value is {@code
     * 0x1fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd1}.
     *
     * @return {@code 1} if the value is a quartic residue, {@code -1}
     *         if not.
     * @see #legendre
     */
    @Override
    public byte legendreQuartic() {
        final long[] out = Arrays.copyOf(digits, NUM_DIGITS);

        legendreQuarticPowerDigits(out);
        normalizeDigits(out);

        final long low = (out[0] << CARRY_BITS) >>> CARRY_BITS;
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
     * @see #normalize
     */
    private static void normalizeDigits(final long[] digits) {
        final long[] offset = Arrays.copyOf(MODULUS_DATA, NUM_DIGITS);
        final long[] plusc = Arrays.copyOf(digits, NUM_DIGITS);

        addDigits(plusc, (int)C_VAL, plusc);
        mulDigits(offset, carryOut(plusc), offset);
        subDigits(digits, offset, digits);
    }

    private static void packDigits(final long[] digits,
                                   final byte[] bytes,
                                   final int idx) {
        bytes[idx + 0] = (byte)(digits[0] & 0xff);
        bytes[idx + 1] = (byte)((digits[0] >> 8) & 0xff);
        bytes[idx + 2] = (byte)((digits[0] >> 16) & 0xff);
        bytes[idx + 3] = (byte)((digits[0] >> 24) & 0xff);
        bytes[idx + 4] = (byte)((digits[0] >> 32) & 0xff);
        bytes[idx + 5] = (byte)((digits[0] >> 40) & 0xff);
        bytes[idx + 6] = (byte)(((digits[0] >> 48) & 0x3f) |
                                ((digits[1] << 6) & 0xc0));
        bytes[idx + 7] = (byte)((digits[1] >> 2) & 0xff);
        bytes[idx + 8] = (byte)((digits[1] >> 10) & 0xff);
        bytes[idx + 9] = (byte)((digits[1] >> 18) & 0xff);
        bytes[idx + 10] = (byte)((digits[1] >> 26) & 0xff);
        bytes[idx + 11] = (byte)((digits[1] >> 34) & 0xff);
        bytes[idx + 12] = (byte)((digits[1] >> 42) & 0xff);
        bytes[idx + 13] = (byte)(((digits[1] >> 50) & 0x0f) |
                                 ((digits[2] << 4) & 0xf0));
        bytes[idx + 14] = (byte)((digits[2] >> 4) & 0xff);
        bytes[idx + 15] = (byte)((digits[2] >> 12) & 0xff);
        bytes[idx + 16] = (byte)((digits[2] >> 20) & 0xff);
        bytes[idx + 17] = (byte)((digits[2] >> 28) & 0xff);
        bytes[idx + 18] = (byte)((digits[2] >> 36) & 0xff);
        bytes[idx + 19] = (byte)((digits[2] >> 44) & 0xff);
        bytes[idx + 20] = (byte)(((digits[2] >> 52) & 0x03) |
                                 ((digits[3] << 2) & 0xfc));
        bytes[idx + 21] = (byte)((digits[3] >> 6) & 0xff);
        bytes[idx + 22] = (byte)((digits[3] >> 14) & 0xff);
        bytes[idx + 23] = (byte)((digits[3] >> 22) & 0xff);
        bytes[idx + 24] = (byte)((digits[3] >> 30) & 0xff);
        bytes[idx + 25] = (byte)((digits[3] >> 38) & 0xff);
        bytes[idx + 26] = (byte)((digits[3] >> 46) & 0xff);
        bytes[idx + 27] = (byte)(digits[4] & 0xff);
        bytes[idx + 28] = (byte)((digits[4] >> 8) & 0xff);
        bytes[idx + 29] = (byte)((digits[4] >> 16) & 0xff);
        bytes[idx + 30] = (byte)((digits[4] >> 24) & 0xff);
        bytes[idx + 31] = (byte)((digits[4] >> 32) & 0xff);
        bytes[idx + 32] = (byte)((digits[4] >> 40) & 0xff);
        bytes[idx + 33] = (byte)(((digits[4] >> 48) & 0x3f) |
                                 ((digits[5] << 6) & 0xc0));
        bytes[idx + 34] = (byte)((digits[5] >> 2) & 0xff);
        bytes[idx + 35] = (byte)((digits[5] >> 10) & 0xff);
        bytes[idx + 36] = (byte)((digits[5] >> 18) & 0xff);
        bytes[idx + 37] = (byte)((digits[5] >> 26) & 0xff);
        bytes[idx + 38] = (byte)((digits[5] >> 34) & 0xff);
        bytes[idx + 39] = (byte)((digits[5] >> 42) & 0xff);
        bytes[idx + 40] = (byte)(((digits[5] >> 50) & 0x0f) |
                                 ((digits[6] << 4) & 0xf0));
        bytes[idx + 41] = (byte)((digits[6] >> 4) & 0xff);
        bytes[idx + 42] = (byte)((digits[6] >> 12) & 0xff);
        bytes[idx + 43] = (byte)((digits[6] >> 20) & 0xff);
        bytes[idx + 44] = (byte)((digits[6] >> 28) & 0xff);
        bytes[idx + 45] = (byte)((digits[6] >> 36) & 0xff);
        bytes[idx + 46] = (byte)((digits[6] >> 44) & 0xff);
        bytes[idx + 47] = (byte)(((digits[6] >> 52) & 0x03) |
                                 ((digits[7] << 2) & 0xfc));
        bytes[idx + 48] = (byte)((digits[7] >> 6) & 0xff);
        bytes[idx + 49] = (byte)((digits[7] >> 14) & 0xff);
        bytes[idx + 50] = (byte)((digits[7] >> 22) & 0xff);
        bytes[idx + 51] = (byte)((digits[7] >> 30) & 0xff);
        bytes[idx + 52] = (byte)((digits[7] >> 38) & 0xff);
        bytes[idx + 53] = (byte)((digits[7] >> 46) & 0xff);
        bytes[idx + 54] = (byte)(digits[8] & 0xff);
        bytes[idx + 55] = (byte)((digits[8] >> 8) & 0xff);
        bytes[idx + 56] = (byte)((digits[8] >> 16) & 0xff);
        bytes[idx + 57] = (byte)((digits[8] >> 24) & 0xff);
        bytes[idx + 58] = (byte)((digits[8] >> 32) & 0xff);
        bytes[idx + 59] = (byte)((digits[8] >> 40) & 0xff);
        bytes[idx + 60] = (byte)(((digits[8] >> 48) & 0x3f) |
                                 ((digits[9] << 6) & 0xc0));
        bytes[idx + 61] = (byte)((digits[9] >> 2) & 0xff);
        bytes[idx + 62] = (byte)((digits[9] >> 10) & 0xff);
        bytes[idx + 63] = (byte)((digits[9] >> 18) & 0x7f);
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
        final long a2 = a[2];
        final long a3 = a[3];
        final long a4 = a[4];
        final long a5 = a[5];
        final long a6 = a[6];
        final long a7 = a[7];
        final long a8 = a[8];
        final long a9 = a[9] & HIGH_DIGIT_MASK;

        final long b0 = b[0];
        final long b1 = b[1];
        final long b2 = b[2];
        final long b3 = b[3];
        final long b4 = b[4];
        final long b5 = b[5];
        final long b6 = b[6];
        final long b7 = b[7];
        final long b8 = b[8];
        final long b9 = b[9] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a) + carryOut(b);
        final long s0 = a0 + b0 + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 + b1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 + b2 + c1;
        final long c2 = s2 >> DIGIT_BITS;
        final long s3 = a3 + b3 + c2;
        final long c3 = s3 >> DIGIT_BITS;
        final long s4 = a4 + b4 + c3;
        final long c4 = s4 >> DIGIT_BITS;
        final long s5 = a5 + b5 + c4;
        final long c5 = s5 >> DIGIT_BITS;
        final long s6 = a6 + b6 + c5;
        final long c6 = s6 >> DIGIT_BITS;
        final long s7 = a7 + b7 + c6;
        final long c7 = s7 >> DIGIT_BITS;
        final long s8 = a8 + b8 + c7;
        final long c8 = s8 >> DIGIT_BITS;
        final long s9 = a9 + b9 + c8;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2 & DIGIT_MASK;
        out[3] = s3 & DIGIT_MASK;
        out[4] = s4 & DIGIT_MASK;
        out[5] = s5 & DIGIT_MASK;
        out[6] = s6 & DIGIT_MASK;
        out[7] = s7 & DIGIT_MASK;
        out[8] = s8 & DIGIT_MASK;
        out[9] = s9;
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
                                  final int b,
                                  final long[] out) {
        final long a0 = a[0];
        final long a1 = a[1];
        final long a2 = a[2];
        final long a3 = a[3];
        final long a4 = a[4];
        final long a5 = a[5];
        final long a6 = a[6];
        final long a7 = a[7];
        final long a8 = a[8];
        final long a9 = a[9] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a);
        final long s0 = a0 + b + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 + c1;
        final long c2 = s2 >> DIGIT_BITS;
        final long s3 = a3 + c2;
        final long c3 = s3 >> DIGIT_BITS;
        final long s4 = a4 + c3;
        final long c4 = s4 >> DIGIT_BITS;
        final long s5 = a5 + c4;
        final long c5 = s5 >> DIGIT_BITS;
        final long s6 = a6 + c5;
        final long c6 = s6 >> DIGIT_BITS;
        final long s7 = a7 + c6;
        final long c7 = s7 >> DIGIT_BITS;
        final long s8 = a8 + c7;
        final long c8 = s8 >> DIGIT_BITS;
        final long s9 = a9 + c8;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2 & DIGIT_MASK;
        out[3] = s3 & DIGIT_MASK;
        out[4] = s4 & DIGIT_MASK;
        out[5] = s5 & DIGIT_MASK;
        out[6] = s6 & DIGIT_MASK;
        out[7] = s7 & DIGIT_MASK;
        out[8] = s8 & DIGIT_MASK;
        out[9] = s9;
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
        final long a2 = a[2];
        final long a3 = a[3];
        final long a4 = a[4];
        final long a5 = a[5];
        final long a6 = a[6];
        final long a7 = a[7];
        final long a8 = a[8];
        final long a9 = a[9] & HIGH_DIGIT_MASK;

        final long b0 = b[0];
        final long b1 = b[1];
        final long b2 = b[2];
        final long b3 = b[3];
        final long b4 = b[4];
        final long b5 = b[5];
        final long b6 = b[6];
        final long b7 = b[7];
        final long b8 = b[8];
        final long b9 = b[9] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a) + carryOut(b);
        final long s0 = a0 - b0 + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 - b1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 - b2 + c1;
        final long c2 = s2 >> DIGIT_BITS;
        final long s3 = a3 - b3 + c2;
        final long c3 = s3 >> DIGIT_BITS;
        final long s4 = a4 - b4 + c3;
        final long c4 = s4 >> DIGIT_BITS;
        final long s5 = a5 - b5 + c4;
        final long c5 = s5 >> DIGIT_BITS;
        final long s6 = a6 - b6 + c5;
        final long c6 = s6 >> DIGIT_BITS;
        final long s7 = a7 - b7 + c6;
        final long c7 = s7 >> DIGIT_BITS;
        final long s8 = a8 - b8 + c7;
        final long c8 = s8 >> DIGIT_BITS;
        final long s9 = a9 - b9 + c8;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2 & DIGIT_MASK;
        out[3] = s3 & DIGIT_MASK;
        out[4] = s4 & DIGIT_MASK;
        out[5] = s5 & DIGIT_MASK;
        out[6] = s6 & DIGIT_MASK;
        out[7] = s7 & DIGIT_MASK;
        out[8] = s8 & DIGIT_MASK;
        out[9] = s9;
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
                                  final int b,
                                  final long[] out) {
        final long a0 = a[0];
        final long a1 = a[1];
        final long a2 = a[2];
        final long a3 = a[3];
        final long a4 = a[4];
        final long a5 = a[5];
        final long a6 = a[6];
        final long a7 = a[7];
        final long a8 = a[8];
        final long a9 = a[9] & HIGH_DIGIT_MASK;

        final long cin = carryOut(a);
        final long s0 = a0 - b + (cin * C_VAL);
        final long c0 = s0 >> DIGIT_BITS;
        final long s1 = a1 + c0;
        final long c1 = s1 >> DIGIT_BITS;
        final long s2 = a2 + c1;
        final long c2 = s2 >> DIGIT_BITS;
        final long s3 = a3 + c2;
        final long c3 = s3 >> DIGIT_BITS;
        final long s4 = a4 + c3;
        final long c4 = s4 >> DIGIT_BITS;
        final long s5 = a5 + c4;
        final long c5 = s5 >> DIGIT_BITS;
        final long s6 = a6 + c5;
        final long c6 = s6 >> DIGIT_BITS;
        final long s7 = a7 + c6;
        final long c7 = s7 >> DIGIT_BITS;
        final long s8 = a8 + c7;
        final long c8 = s8 >> DIGIT_BITS;
        final long s9 = a9 + c8;

        out[0] = s0 & DIGIT_MASK;
        out[1] = s1 & DIGIT_MASK;
        out[2] = s2 & DIGIT_MASK;
        out[3] = s3 & DIGIT_MASK;
        out[4] = s4 & DIGIT_MASK;
        out[5] = s5 & DIGIT_MASK;
        out[6] = s6 & DIGIT_MASK;
        out[7] = s7 & DIGIT_MASK;
        out[8] = s8 & DIGIT_MASK;
        out[9] = s9;
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
        final long a4 = a[2] & MUL_DIGIT_MASK;
        final long a5 = a[2] >> MUL_DIGIT_BITS;
        final long a6 = a[3] & MUL_DIGIT_MASK;
        final long a7 = a[3] >> MUL_DIGIT_BITS;
        final long a8 = a[4] & MUL_DIGIT_MASK;
        final long a9 = a[4] >> MUL_DIGIT_BITS;
        final long a10 = a[5] & MUL_DIGIT_MASK;
        final long a11 = a[5] >> MUL_DIGIT_BITS;
        final long a12 = a[6] & MUL_DIGIT_MASK;
        final long a13 = a[6] >> MUL_DIGIT_BITS;
        final long a14 = a[7] & MUL_DIGIT_MASK;
        final long a15 = a[7] >> MUL_DIGIT_BITS;
        final long a16 = a[8] & MUL_DIGIT_MASK;
        final long a17 = a[8] >> MUL_DIGIT_BITS;
        final long a18 = a[9];

        final long b0 = b[0] & MUL_DIGIT_MASK;
        final long b1 = b[0] >> MUL_DIGIT_BITS;
        final long b2 = b[1] & MUL_DIGIT_MASK;
        final long b3 = b[1] >> MUL_DIGIT_BITS;
        final long b4 = b[2] & MUL_DIGIT_MASK;
        final long b5 = b[2] >> MUL_DIGIT_BITS;
        final long b6 = b[3] & MUL_DIGIT_MASK;
        final long b7 = b[3] >> MUL_DIGIT_BITS;
        final long b8 = b[4] & MUL_DIGIT_MASK;
        final long b9 = b[4] >> MUL_DIGIT_BITS;
        final long b10 = b[5] & MUL_DIGIT_MASK;
        final long b11 = b[5] >> MUL_DIGIT_BITS;
        final long b12 = b[6] & MUL_DIGIT_MASK;
        final long b13 = b[6] >> MUL_DIGIT_BITS;
        final long b14 = b[7] & MUL_DIGIT_MASK;
        final long b15 = b[7] >> MUL_DIGIT_BITS;
        final long b16 = b[8] & MUL_DIGIT_MASK;
        final long b17 = b[8] >> MUL_DIGIT_BITS;
        final long b18 = b[9];

        // Combined multiples
        final long m_0_0 = a0 * b0;
        final long m_0_1 = a0 * b1;
        final long m_0_2 = a0 * b2;
        final long m_0_3 = a0 * b3;
        final long m_0_4 = a0 * b4;
        final long m_0_5 = a0 * b5;
        final long m_0_6 = a0 * b6;
        final long m_0_7 = a0 * b7;
        final long m_0_8 = a0 * b8;
        final long m_0_9 = a0 * b9;
        final long m_0_10 = a0 * b10;
        final long m_0_11 = a0 * b11;
        final long m_0_12 = a0 * b12;
        final long m_0_13 = a0 * b13;
        final long m_0_14 = a0 * b14;
        final long m_0_15 = a0 * b15;
        final long m_0_16 = a0 * b16;
        final long m_0_17 = a0 * b17;
        final long m_0_18 = a0 * b18;
        final long m_1_0 = a1 * b0;
        final long m_1_1 = a1 * b1;
        final long m_1_2 = a1 * b2;
        final long m_1_3 = a1 * b3;
        final long m_1_4 = a1 * b4;
        final long m_1_5 = a1 * b5;
        final long m_1_6 = a1 * b6;
        final long m_1_7 = a1 * b7;
        final long m_1_8 = a1 * b8;
        final long m_1_9 = a1 * b9;
        final long m_1_10 = a1 * b10;
        final long m_1_11 = a1 * b11;
        final long m_1_12 = a1 * b12;
        final long m_1_13 = a1 * b13;
        final long m_1_14 = a1 * b14;
        final long m_1_15 = a1 * b15;
        final long m_1_16 = a1 * b16;
        final long m_1_17 = a1 * b17;
        final long m_1_18 = a1 * b18;
        final long m_2_0 = a2 * b0;
        final long m_2_1 = a2 * b1;
        final long m_2_2 = a2 * b2;
        final long m_2_3 = a2 * b3;
        final long m_2_4 = a2 * b4;
        final long m_2_5 = a2 * b5;
        final long m_2_6 = a2 * b6;
        final long m_2_7 = a2 * b7;
        final long m_2_8 = a2 * b8;
        final long m_2_9 = a2 * b9;
        final long m_2_10 = a2 * b10;
        final long m_2_11 = a2 * b11;
        final long m_2_12 = a2 * b12;
        final long m_2_13 = a2 * b13;
        final long m_2_14 = a2 * b14;
        final long m_2_15 = a2 * b15;
        final long m_2_16 = a2 * b16;
        final long m_2_17 = a2 * b17;
        final long m_2_18 = a2 * b18;
        final long m_3_0 = a3 * b0;
        final long m_3_1 = a3 * b1;
        final long m_3_2 = a3 * b2;
        final long m_3_3 = a3 * b3;
        final long m_3_4 = a3 * b4;
        final long m_3_5 = a3 * b5;
        final long m_3_6 = a3 * b6;
        final long m_3_7 = a3 * b7;
        final long m_3_8 = a3 * b8;
        final long m_3_9 = a3 * b9;
        final long m_3_10 = a3 * b10;
        final long m_3_11 = a3 * b11;
        final long m_3_12 = a3 * b12;
        final long m_3_13 = a3 * b13;
        final long m_3_14 = a3 * b14;
        final long m_3_15 = a3 * b15;
        final long m_3_16 = a3 * b16;
        final long m_3_17 = a3 * b17;
        final long m_3_18 = a3 * b18;
        final long m_4_0 = a4 * b0;
        final long m_4_1 = a4 * b1;
        final long m_4_2 = a4 * b2;
        final long m_4_3 = a4 * b3;
        final long m_4_4 = a4 * b4;
        final long m_4_5 = a4 * b5;
        final long m_4_6 = a4 * b6;
        final long m_4_7 = a4 * b7;
        final long m_4_8 = a4 * b8;
        final long m_4_9 = a4 * b9;
        final long m_4_10 = a4 * b10;
        final long m_4_11 = a4 * b11;
        final long m_4_12 = a4 * b12;
        final long m_4_13 = a4 * b13;
        final long m_4_14 = a4 * b14;
        final long m_4_15 = a4 * b15;
        final long m_4_16 = a4 * b16;
        final long m_4_17 = a4 * b17;
        final long m_4_18 = a4 * b18;
        final long m_5_0 = a5 * b0;
        final long m_5_1 = a5 * b1;
        final long m_5_2 = a5 * b2;
        final long m_5_3 = a5 * b3;
        final long m_5_4 = a5 * b4;
        final long m_5_5 = a5 * b5;
        final long m_5_6 = a5 * b6;
        final long m_5_7 = a5 * b7;
        final long m_5_8 = a5 * b8;
        final long m_5_9 = a5 * b9;
        final long m_5_10 = a5 * b10;
        final long m_5_11 = a5 * b11;
        final long m_5_12 = a5 * b12;
        final long m_5_13 = a5 * b13;
        final long m_5_14 = a5 * b14;
        final long m_5_15 = a5 * b15;
        final long m_5_16 = a5 * b16;
        final long m_5_17 = a5 * b17;
        final long m_5_18 = a5 * b18;
        final long m_6_0 = a6 * b0;
        final long m_6_1 = a6 * b1;
        final long m_6_2 = a6 * b2;
        final long m_6_3 = a6 * b3;
        final long m_6_4 = a6 * b4;
        final long m_6_5 = a6 * b5;
        final long m_6_6 = a6 * b6;
        final long m_6_7 = a6 * b7;
        final long m_6_8 = a6 * b8;
        final long m_6_9 = a6 * b9;
        final long m_6_10 = a6 * b10;
        final long m_6_11 = a6 * b11;
        final long m_6_12 = a6 * b12;
        final long m_6_13 = a6 * b13;
        final long m_6_14 = a6 * b14;
        final long m_6_15 = a6 * b15;
        final long m_6_16 = a6 * b16;
        final long m_6_17 = a6 * b17;
        final long m_6_18 = a6 * b18;
        final long m_7_0 = a7 * b0;
        final long m_7_1 = a7 * b1;
        final long m_7_2 = a7 * b2;
        final long m_7_3 = a7 * b3;
        final long m_7_4 = a7 * b4;
        final long m_7_5 = a7 * b5;
        final long m_7_6 = a7 * b6;
        final long m_7_7 = a7 * b7;
        final long m_7_8 = a7 * b8;
        final long m_7_9 = a7 * b9;
        final long m_7_10 = a7 * b10;
        final long m_7_11 = a7 * b11;
        final long m_7_12 = a7 * b12;
        final long m_7_13 = a7 * b13;
        final long m_7_14 = a7 * b14;
        final long m_7_15 = a7 * b15;
        final long m_7_16 = a7 * b16;
        final long m_7_17 = a7 * b17;
        final long m_7_18 = a7 * b18;
        final long m_8_0 = a8 * b0;
        final long m_8_1 = a8 * b1;
        final long m_8_2 = a8 * b2;
        final long m_8_3 = a8 * b3;
        final long m_8_4 = a8 * b4;
        final long m_8_5 = a8 * b5;
        final long m_8_6 = a8 * b6;
        final long m_8_7 = a8 * b7;
        final long m_8_8 = a8 * b8;
        final long m_8_9 = a8 * b9;
        final long m_8_10 = a8 * b10;
        final long m_8_11 = a8 * b11;
        final long m_8_12 = a8 * b12;
        final long m_8_13 = a8 * b13;
        final long m_8_14 = a8 * b14;
        final long m_8_15 = a8 * b15;
        final long m_8_16 = a8 * b16;
        final long m_8_17 = a8 * b17;
        final long m_8_18 = a8 * b18;
        final long m_9_0 = a9 * b0;
        final long m_9_1 = a9 * b1;
        final long m_9_2 = a9 * b2;
        final long m_9_3 = a9 * b3;
        final long m_9_4 = a9 * b4;
        final long m_9_5 = a9 * b5;
        final long m_9_6 = a9 * b6;
        final long m_9_7 = a9 * b7;
        final long m_9_8 = a9 * b8;
        final long m_9_9 = a9 * b9;
        final long m_9_10 = a9 * b10;
        final long m_9_11 = a9 * b11;
        final long m_9_12 = a9 * b12;
        final long m_9_13 = a9 * b13;
        final long m_9_14 = a9 * b14;
        final long m_9_15 = a9 * b15;
        final long m_9_16 = a9 * b16;
        final long m_9_17 = a9 * b17;
        final long m_9_18 = a9 * b18;
        final long m_10_0 = a10 * b0;
        final long m_10_1 = a10 * b1;
        final long m_10_2 = a10 * b2;
        final long m_10_3 = a10 * b3;
        final long m_10_4 = a10 * b4;
        final long m_10_5 = a10 * b5;
        final long m_10_6 = a10 * b6;
        final long m_10_7 = a10 * b7;
        final long m_10_8 = a10 * b8;
        final long m_10_9 = a10 * b9;
        final long m_10_10 = a10 * b10;
        final long m_10_11 = a10 * b11;
        final long m_10_12 = a10 * b12;
        final long m_10_13 = a10 * b13;
        final long m_10_14 = a10 * b14;
        final long m_10_15 = a10 * b15;
        final long m_10_16 = a10 * b16;
        final long m_10_17 = a10 * b17;
        final long m_10_18 = a10 * b18;
        final long m_11_0 = a11 * b0;
        final long m_11_1 = a11 * b1;
        final long m_11_2 = a11 * b2;
        final long m_11_3 = a11 * b3;
        final long m_11_4 = a11 * b4;
        final long m_11_5 = a11 * b5;
        final long m_11_6 = a11 * b6;
        final long m_11_7 = a11 * b7;
        final long m_11_8 = a11 * b8;
        final long m_11_9 = a11 * b9;
        final long m_11_10 = a11 * b10;
        final long m_11_11 = a11 * b11;
        final long m_11_12 = a11 * b12;
        final long m_11_13 = a11 * b13;
        final long m_11_14 = a11 * b14;
        final long m_11_15 = a11 * b15;
        final long m_11_16 = a11 * b16;
        final long m_11_17 = a11 * b17;
        final long m_11_18 = a11 * b18;
        final long m_12_0 = a12 * b0;
        final long m_12_1 = a12 * b1;
        final long m_12_2 = a12 * b2;
        final long m_12_3 = a12 * b3;
        final long m_12_4 = a12 * b4;
        final long m_12_5 = a12 * b5;
        final long m_12_6 = a12 * b6;
        final long m_12_7 = a12 * b7;
        final long m_12_8 = a12 * b8;
        final long m_12_9 = a12 * b9;
        final long m_12_10 = a12 * b10;
        final long m_12_11 = a12 * b11;
        final long m_12_12 = a12 * b12;
        final long m_12_13 = a12 * b13;
        final long m_12_14 = a12 * b14;
        final long m_12_15 = a12 * b15;
        final long m_12_16 = a12 * b16;
        final long m_12_17 = a12 * b17;
        final long m_12_18 = a12 * b18;
        final long m_13_0 = a13 * b0;
        final long m_13_1 = a13 * b1;
        final long m_13_2 = a13 * b2;
        final long m_13_3 = a13 * b3;
        final long m_13_4 = a13 * b4;
        final long m_13_5 = a13 * b5;
        final long m_13_6 = a13 * b6;
        final long m_13_7 = a13 * b7;
        final long m_13_8 = a13 * b8;
        final long m_13_9 = a13 * b9;
        final long m_13_10 = a13 * b10;
        final long m_13_11 = a13 * b11;
        final long m_13_12 = a13 * b12;
        final long m_13_13 = a13 * b13;
        final long m_13_14 = a13 * b14;
        final long m_13_15 = a13 * b15;
        final long m_13_16 = a13 * b16;
        final long m_13_17 = a13 * b17;
        final long m_13_18 = a13 * b18;
        final long m_14_0 = a14 * b0;
        final long m_14_1 = a14 * b1;
        final long m_14_2 = a14 * b2;
        final long m_14_3 = a14 * b3;
        final long m_14_4 = a14 * b4;
        final long m_14_5 = a14 * b5;
        final long m_14_6 = a14 * b6;
        final long m_14_7 = a14 * b7;
        final long m_14_8 = a14 * b8;
        final long m_14_9 = a14 * b9;
        final long m_14_10 = a14 * b10;
        final long m_14_11 = a14 * b11;
        final long m_14_12 = a14 * b12;
        final long m_14_13 = a14 * b13;
        final long m_14_14 = a14 * b14;
        final long m_14_15 = a14 * b15;
        final long m_14_16 = a14 * b16;
        final long m_14_17 = a14 * b17;
        final long m_14_18 = a14 * b18;
        final long m_15_0 = a15 * b0;
        final long m_15_1 = a15 * b1;
        final long m_15_2 = a15 * b2;
        final long m_15_3 = a15 * b3;
        final long m_15_4 = a15 * b4;
        final long m_15_5 = a15 * b5;
        final long m_15_6 = a15 * b6;
        final long m_15_7 = a15 * b7;
        final long m_15_8 = a15 * b8;
        final long m_15_9 = a15 * b9;
        final long m_15_10 = a15 * b10;
        final long m_15_11 = a15 * b11;
        final long m_15_12 = a15 * b12;
        final long m_15_13 = a15 * b13;
        final long m_15_14 = a15 * b14;
        final long m_15_15 = a15 * b15;
        final long m_15_16 = a15 * b16;
        final long m_15_17 = a15 * b17;
        final long m_15_18 = a15 * b18;
        final long m_16_0 = a16 * b0;
        final long m_16_1 = a16 * b1;
        final long m_16_2 = a16 * b2;
        final long m_16_3 = a16 * b3;
        final long m_16_4 = a16 * b4;
        final long m_16_5 = a16 * b5;
        final long m_16_6 = a16 * b6;
        final long m_16_7 = a16 * b7;
        final long m_16_8 = a16 * b8;
        final long m_16_9 = a16 * b9;
        final long m_16_10 = a16 * b10;
        final long m_16_11 = a16 * b11;
        final long m_16_12 = a16 * b12;
        final long m_16_13 = a16 * b13;
        final long m_16_14 = a16 * b14;
        final long m_16_15 = a16 * b15;
        final long m_16_16 = a16 * b16;
        final long m_16_17 = a16 * b17;
        final long m_16_18 = a16 * b18;
        final long m_17_0 = a17 * b0;
        final long m_17_1 = a17 * b1;
        final long m_17_2 = a17 * b2;
        final long m_17_3 = a17 * b3;
        final long m_17_4 = a17 * b4;
        final long m_17_5 = a17 * b5;
        final long m_17_6 = a17 * b6;
        final long m_17_7 = a17 * b7;
        final long m_17_8 = a17 * b8;
        final long m_17_9 = a17 * b9;
        final long m_17_10 = a17 * b10;
        final long m_17_11 = a17 * b11;
        final long m_17_12 = a17 * b12;
        final long m_17_13 = a17 * b13;
        final long m_17_14 = a17 * b14;
        final long m_17_15 = a17 * b15;
        final long m_17_16 = a17 * b16;
        final long m_17_17 = a17 * b17;
        final long m_17_18 = a17 * b18;
        final long m_18_0 = a18 * b0;
        final long m_18_1 = a18 * b1;
        final long m_18_2 = a18 * b2;
        final long m_18_3 = a18 * b3;
        final long m_18_4 = a18 * b4;
        final long m_18_5 = a18 * b5;
        final long m_18_6 = a18 * b6;
        final long m_18_7 = a18 * b7;
        final long m_18_8 = a18 * b8;
        final long m_18_9 = a18 * b9;
        final long m_18_10 = a18 * b10;
        final long m_18_11 = a18 * b11;
        final long m_18_12 = a18 * b12;
        final long m_18_13 = a18 * b13;
        final long m_18_14 = a18 * b14;
        final long m_18_15 = a18 * b15;
        final long m_18_16 = a18 * b16;
        final long m_18_17 = a18 * b17;
        final long m_18_18 = a18 * b18;

        // Compute the 40-digit combined product using 64-bit operations.
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
            ((m_3_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c0;
        final long c1 = d1 >> DIGIT_BITS;
        final long d2 =
            (m_0_3 >> MUL_DIGIT_BITS) + m_0_4 +
            ((m_0_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_2 >> MUL_DIGIT_BITS) + m_1_3 +
            ((m_1_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_1 >> MUL_DIGIT_BITS) + m_2_2 +
            ((m_2_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_0 >> MUL_DIGIT_BITS) + m_3_1 +
            ((m_3_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_4_0 + ((m_4_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_5_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c1;
        final long c2 = d2 >> DIGIT_BITS;
        final long d3 =
            (m_0_5 >> MUL_DIGIT_BITS) + m_0_6 +
            ((m_0_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_4 >> MUL_DIGIT_BITS) + m_1_5 +
            ((m_1_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_3 >> MUL_DIGIT_BITS) + m_2_4 +
            ((m_2_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_2 >> MUL_DIGIT_BITS) + m_3_3 +
            ((m_3_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_1 >> MUL_DIGIT_BITS) + m_4_2 +
            ((m_4_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_0 >> MUL_DIGIT_BITS) + m_5_1 +
            ((m_5_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_6_0 + ((m_6_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_7_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c2;
        final long c3 = d3 >> DIGIT_BITS;
        final long d4 =
            (m_0_7 >> MUL_DIGIT_BITS) + m_0_8 +
            ((m_0_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_6 >> MUL_DIGIT_BITS) + m_1_7 +
            ((m_1_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_5 >> MUL_DIGIT_BITS) + m_2_6 +
            ((m_2_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_4 >> MUL_DIGIT_BITS) + m_3_5 +
            ((m_3_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_3 >> MUL_DIGIT_BITS) + m_4_4 +
            ((m_4_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_2 >> MUL_DIGIT_BITS) + m_5_3 +
            ((m_5_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_1 >> MUL_DIGIT_BITS) + m_6_2 +
            ((m_6_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_0 >> MUL_DIGIT_BITS) + m_7_1 +
            ((m_7_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_8_0 + ((m_8_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_9_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c3;
        final long c4 = d4 >> DIGIT_BITS;
        final long d5 =
            (m_0_9 >> MUL_DIGIT_BITS) + m_0_10 +
            ((m_0_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_8 >> MUL_DIGIT_BITS) + m_1_9 +
            ((m_1_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_7 >> MUL_DIGIT_BITS) + m_2_8 +
            ((m_2_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_6 >> MUL_DIGIT_BITS) + m_3_7 +
            ((m_3_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_5 >> MUL_DIGIT_BITS) + m_4_6 +
            ((m_4_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_4 >> MUL_DIGIT_BITS) + m_5_5 +
            ((m_5_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_3 >> MUL_DIGIT_BITS) + m_6_4 +
            ((m_6_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_2 >> MUL_DIGIT_BITS) + m_7_3 +
            ((m_7_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_1 >> MUL_DIGIT_BITS) + m_8_2 +
            ((m_8_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_0 >> MUL_DIGIT_BITS) + m_9_1 +
            ((m_9_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_10_0 + ((m_10_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_11_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c4;
        final long c5 = d5 >> DIGIT_BITS;
        final long d6 =
            (m_0_11 >> MUL_DIGIT_BITS) + m_0_12 +
            ((m_0_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_10 >> MUL_DIGIT_BITS) + m_1_11 +
            ((m_1_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_9 >> MUL_DIGIT_BITS) + m_2_10 +
            ((m_2_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_8 >> MUL_DIGIT_BITS) + m_3_9 +
            ((m_3_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_7 >> MUL_DIGIT_BITS) + m_4_8 +
            ((m_4_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_6 >> MUL_DIGIT_BITS) + m_5_7 +
            ((m_5_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_5 >> MUL_DIGIT_BITS) + m_6_6 +
            ((m_6_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_4 >> MUL_DIGIT_BITS) + m_7_5 +
            ((m_7_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_3 >> MUL_DIGIT_BITS) + m_8_4 +
            ((m_8_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_2 >> MUL_DIGIT_BITS) + m_9_3 +
            ((m_9_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_1 >> MUL_DIGIT_BITS) + m_10_2 +
            ((m_10_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_0 >> MUL_DIGIT_BITS) + m_11_1 +
            ((m_11_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_12_0 + ((m_12_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_13_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c5;
        final long c6 = d6 >> DIGIT_BITS;
        final long d7 =
            (m_0_13 >> MUL_DIGIT_BITS) + m_0_14 +
            ((m_0_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_12 >> MUL_DIGIT_BITS) + m_1_13 +
            ((m_1_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_11 >> MUL_DIGIT_BITS) + m_2_12 +
            ((m_2_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_10 >> MUL_DIGIT_BITS) + m_3_11 +
            ((m_3_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_9 >> MUL_DIGIT_BITS) + m_4_10 +
            ((m_4_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_8 >> MUL_DIGIT_BITS) + m_5_9 +
            ((m_5_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_7 >> MUL_DIGIT_BITS) + m_6_8 +
            ((m_6_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_6 >> MUL_DIGIT_BITS) + m_7_7 +
            ((m_7_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_5 >> MUL_DIGIT_BITS) + m_8_6 +
            ((m_8_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_4 >> MUL_DIGIT_BITS) + m_9_5 +
            ((m_9_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_3 >> MUL_DIGIT_BITS) + m_10_4 +
            ((m_10_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_2 >> MUL_DIGIT_BITS) + m_11_3 +
            ((m_11_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_1 >> MUL_DIGIT_BITS) + m_12_2 +
            ((m_12_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_0 >> MUL_DIGIT_BITS) + m_13_1 +
            ((m_13_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_14_0 + ((m_14_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_15_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c6;
        final long c7 = d7 >> DIGIT_BITS;
        final long d8 =
            (m_0_15 >> MUL_DIGIT_BITS) + m_0_16 +
            ((m_0_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_14 >> MUL_DIGIT_BITS) + m_1_15 +
            ((m_1_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_13 >> MUL_DIGIT_BITS) + m_2_14 +
            ((m_2_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_12 >> MUL_DIGIT_BITS) + m_3_13 +
            ((m_3_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_11 >> MUL_DIGIT_BITS) + m_4_12 +
            ((m_4_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_10 >> MUL_DIGIT_BITS) + m_5_11 +
            ((m_5_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_9 >> MUL_DIGIT_BITS) + m_6_10 +
            ((m_6_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_8 >> MUL_DIGIT_BITS) + m_7_9 +
            ((m_7_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_7 >> MUL_DIGIT_BITS) + m_8_8 +
            ((m_8_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_6 >> MUL_DIGIT_BITS) + m_9_7 +
            ((m_9_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_5 >> MUL_DIGIT_BITS) + m_10_6 +
            ((m_10_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_4 >> MUL_DIGIT_BITS) + m_11_5 +
            ((m_11_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_3 >> MUL_DIGIT_BITS) + m_12_4 +
            ((m_12_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_2 >> MUL_DIGIT_BITS) + m_13_3 +
            ((m_13_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_1 >> MUL_DIGIT_BITS) + m_14_2 +
            ((m_14_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_0 >> MUL_DIGIT_BITS) + m_15_1 +
            ((m_15_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_16_0 + ((m_16_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_17_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c7;
        final long c8 = d8 >> DIGIT_BITS;
        final long d9 =
            (m_0_17 >> MUL_DIGIT_BITS) + m_0_18 +
            (m_1_16 >> MUL_DIGIT_BITS) + m_1_17 +
            ((m_1_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_15 >> MUL_DIGIT_BITS) + m_2_16 +
            ((m_2_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_14 >> MUL_DIGIT_BITS) + m_3_15 +
            ((m_3_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_13 >> MUL_DIGIT_BITS) + m_4_14 +
            ((m_4_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_12 >> MUL_DIGIT_BITS) + m_5_13 +
            ((m_5_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_11 >> MUL_DIGIT_BITS) + m_6_12 +
            ((m_6_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_10 >> MUL_DIGIT_BITS) + m_7_11 +
            ((m_7_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_9 >> MUL_DIGIT_BITS) + m_8_10 +
            ((m_8_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_8 >> MUL_DIGIT_BITS) + m_9_9 +
            ((m_9_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_7 >> MUL_DIGIT_BITS) + m_10_8 +
            ((m_10_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_6 >> MUL_DIGIT_BITS) + m_11_7 +
            ((m_11_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_5 >> MUL_DIGIT_BITS) + m_12_6 +
            ((m_12_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_4 >> MUL_DIGIT_BITS) + m_13_5 +
            ((m_13_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_3 >> MUL_DIGIT_BITS) + m_14_4 +
            ((m_14_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_2 >> MUL_DIGIT_BITS) + m_15_3 +
            ((m_15_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_1 >> MUL_DIGIT_BITS) + m_16_2 +
            ((m_16_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_0 >> MUL_DIGIT_BITS) + m_17_1 +
            ((m_17_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_18_0 + ((m_18_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c8;
        final long c9 = d9 >> DIGIT_BITS;
        final long d10 =
            (m_1_18 >> MUL_DIGIT_BITS) +
            (m_2_17 >> MUL_DIGIT_BITS) + m_2_18 +
            (m_3_16 >> MUL_DIGIT_BITS) + m_3_17 +
            ((m_3_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_15 >> MUL_DIGIT_BITS) + m_4_16 +
            ((m_4_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_14 >> MUL_DIGIT_BITS) + m_5_15 +
            ((m_5_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_13 >> MUL_DIGIT_BITS) + m_6_14 +
            ((m_6_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_12 >> MUL_DIGIT_BITS) + m_7_13 +
            ((m_7_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_11 >> MUL_DIGIT_BITS) + m_8_12 +
            ((m_8_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_10 >> MUL_DIGIT_BITS) + m_9_11 +
            ((m_9_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_9 >> MUL_DIGIT_BITS) + m_10_10 +
            ((m_10_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_8 >> MUL_DIGIT_BITS) + m_11_9 +
            ((m_11_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_7 >> MUL_DIGIT_BITS) + m_12_8 +
            ((m_12_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_6 >> MUL_DIGIT_BITS) + m_13_7 +
            ((m_13_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_5 >> MUL_DIGIT_BITS) + m_14_6 +
            ((m_14_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_4 >> MUL_DIGIT_BITS) + m_15_5 +
            ((m_15_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_3 >> MUL_DIGIT_BITS) + m_16_4 +
            ((m_16_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_2 >> MUL_DIGIT_BITS) + m_17_3 +
            ((m_17_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_1 >> MUL_DIGIT_BITS) + m_18_2 +
            ((m_18_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c9;
        final long c10 = d10 >> DIGIT_BITS;
        final long d11 =
            (m_3_18 >> MUL_DIGIT_BITS) +
            (m_4_17 >> MUL_DIGIT_BITS) + m_4_18 +
            (m_5_16 >> MUL_DIGIT_BITS) + m_5_17 +
            ((m_5_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_15 >> MUL_DIGIT_BITS) + m_6_16 +
            ((m_6_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_14 >> MUL_DIGIT_BITS) + m_7_15 +
            ((m_7_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_13 >> MUL_DIGIT_BITS) + m_8_14 +
            ((m_8_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_12 >> MUL_DIGIT_BITS) + m_9_13 +
            ((m_9_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_11 >> MUL_DIGIT_BITS) + m_10_12 +
            ((m_10_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_10 >> MUL_DIGIT_BITS) + m_11_11 +
            ((m_11_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_9 >> MUL_DIGIT_BITS) + m_12_10 +
            ((m_12_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_8 >> MUL_DIGIT_BITS) + m_13_9 +
            ((m_13_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_7 >> MUL_DIGIT_BITS) + m_14_8 +
            ((m_14_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_6 >> MUL_DIGIT_BITS) + m_15_7 +
            ((m_15_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_5 >> MUL_DIGIT_BITS) + m_16_6 +
            ((m_16_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_4 >> MUL_DIGIT_BITS) + m_17_5 +
            ((m_17_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_3 >> MUL_DIGIT_BITS) + m_18_4 +
            ((m_18_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c10;
        final long c11 = d11 >> DIGIT_BITS;
        final long d12 =
            (m_5_18 >> MUL_DIGIT_BITS) +
            (m_6_17 >> MUL_DIGIT_BITS) + m_6_18 +
            (m_7_16 >> MUL_DIGIT_BITS) + m_7_17 +
            ((m_7_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_15 >> MUL_DIGIT_BITS) + m_8_16 +
            ((m_8_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_14 >> MUL_DIGIT_BITS) + m_9_15 +
            ((m_9_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_13 >> MUL_DIGIT_BITS) + m_10_14 +
            ((m_10_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_12 >> MUL_DIGIT_BITS) + m_11_13 +
            ((m_11_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_11 >> MUL_DIGIT_BITS) + m_12_12 +
            ((m_12_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_10 >> MUL_DIGIT_BITS) + m_13_11 +
            ((m_13_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_9 >> MUL_DIGIT_BITS) + m_14_10 +
            ((m_14_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_8 >> MUL_DIGIT_BITS) + m_15_9 +
            ((m_15_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_7 >> MUL_DIGIT_BITS) + m_16_8 +
            ((m_16_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_6 >> MUL_DIGIT_BITS) + m_17_7 +
            ((m_17_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_5 >> MUL_DIGIT_BITS) + m_18_6 +
            ((m_18_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c11;
        final long c12 = d12 >> DIGIT_BITS;
        final long d13 =
            (m_7_18 >> MUL_DIGIT_BITS) +
            (m_8_17 >> MUL_DIGIT_BITS) + m_8_18 +
            (m_9_16 >> MUL_DIGIT_BITS) + m_9_17 +
            ((m_9_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_15 >> MUL_DIGIT_BITS) + m_10_16 +
            ((m_10_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_14 >> MUL_DIGIT_BITS) + m_11_15 +
            ((m_11_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_13 >> MUL_DIGIT_BITS) + m_12_14 +
            ((m_12_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_12 >> MUL_DIGIT_BITS) + m_13_13 +
            ((m_13_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_11 >> MUL_DIGIT_BITS) + m_14_12 +
            ((m_14_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_10 >> MUL_DIGIT_BITS) + m_15_11 +
            ((m_15_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_9 >> MUL_DIGIT_BITS) + m_16_10 +
            ((m_16_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_8 >> MUL_DIGIT_BITS) + m_17_9 +
            ((m_17_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_7 >> MUL_DIGIT_BITS) + m_18_8 +
            ((m_18_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c12;
        final long c13 = d13 >> DIGIT_BITS;
        final long d14 =
            (m_9_18 >> MUL_DIGIT_BITS) +
            (m_10_17 >> MUL_DIGIT_BITS) + m_10_18 +
            (m_11_16 >> MUL_DIGIT_BITS) + m_11_17 +
            ((m_11_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_15 >> MUL_DIGIT_BITS) + m_12_16 +
            ((m_12_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_14 >> MUL_DIGIT_BITS) + m_13_15 +
            ((m_13_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_13 >> MUL_DIGIT_BITS) + m_14_14 +
            ((m_14_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_12 >> MUL_DIGIT_BITS) + m_15_13 +
            ((m_15_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_11 >> MUL_DIGIT_BITS) + m_16_12 +
            ((m_16_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_10 >> MUL_DIGIT_BITS) + m_17_11 +
            ((m_17_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_9 >> MUL_DIGIT_BITS) + m_18_10 +
            ((m_18_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c13;
        final long c14 = d14 >> DIGIT_BITS;
        final long d15 =
            (m_11_18 >> MUL_DIGIT_BITS) +
            (m_12_17 >> MUL_DIGIT_BITS) + m_12_18 +
            (m_13_16 >> MUL_DIGIT_BITS) + m_13_17 +
            ((m_13_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_15 >> MUL_DIGIT_BITS) + m_14_16 +
            ((m_14_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_14 >> MUL_DIGIT_BITS) + m_15_15 +
            ((m_15_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_13 >> MUL_DIGIT_BITS) + m_16_14 +
            ((m_16_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_12 >> MUL_DIGIT_BITS) + m_17_13 +
            ((m_17_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_11 >> MUL_DIGIT_BITS) + m_18_12 +
            ((m_18_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c14;
        final long c15 = d15 >> DIGIT_BITS;
        final long d16 =
            (m_13_18 >> MUL_DIGIT_BITS) +
            (m_14_17 >> MUL_DIGIT_BITS) + m_14_18 +
            (m_15_16 >> MUL_DIGIT_BITS) + m_15_17 +
            ((m_15_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_15 >> MUL_DIGIT_BITS) + m_16_16 +
            ((m_16_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_14 >> MUL_DIGIT_BITS) + m_17_15 +
            ((m_17_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_13 >> MUL_DIGIT_BITS) + m_18_14 +
            ((m_18_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c15;
        final long c16 = d16 >> DIGIT_BITS;
        final long d17 =
            (m_15_18 >> MUL_DIGIT_BITS) +
            (m_16_17 >> MUL_DIGIT_BITS) + m_16_18 +
            (m_17_16 >> MUL_DIGIT_BITS) + m_17_17 +
            ((m_17_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_15 >> MUL_DIGIT_BITS) + m_18_16 +
            ((m_18_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c16;
        final long c17 = d17 >> DIGIT_BITS;
        final long d18 =
            (m_17_18 >> MUL_DIGIT_BITS) +
            (m_18_17 >> MUL_DIGIT_BITS) + m_18_18 + c17;

        // Modular reduction by a pseudo-mersenne prime of the form 2^n - c.

        // These are the n low-order bits.
        final long l0_0 = d0 & DIGIT_MASK;
        final long l1_0 = d1 & DIGIT_MASK;
        final long l2_0 = d2 & DIGIT_MASK;
        final long l3_0 = d3 & DIGIT_MASK;
        final long l4_0 = d4 & DIGIT_MASK;
        final long l5_0 = d5 & DIGIT_MASK;
        final long l6_0 = d6 & DIGIT_MASK;
        final long l7_0 = d7 & DIGIT_MASK;
        final long l8_0 = d8 & DIGIT_MASK;
        final long l9_0 = d9 & HIGH_DIGIT_MASK;

        // Shift the high bits down into another n-bit number.
        final long h0_0 = ((d9 & DIGIT_MASK) >> 25) |
                   ((d10 & HIGH_DIGIT_MASK) << 29);
        final long h1_0 = ((d10 & DIGIT_MASK) >> 25) |
                   ((d11 & HIGH_DIGIT_MASK) << 29);
        final long h2_0 = ((d11 & DIGIT_MASK) >> 25) |
                   ((d12 & HIGH_DIGIT_MASK) << 29);
        final long h3_0 = ((d12 & DIGIT_MASK) >> 25) |
                   ((d13 & HIGH_DIGIT_MASK) << 29);
        final long h4_0 = ((d13 & DIGIT_MASK) >> 25) |
                   ((d14 & HIGH_DIGIT_MASK) << 29);
        final long h5_0 = ((d14 & DIGIT_MASK) >> 25) |
                   ((d15 & HIGH_DIGIT_MASK) << 29);
        final long h6_0 = ((d15 & DIGIT_MASK) >> 25) |
                   ((d16 & HIGH_DIGIT_MASK) << 29);
        final long h7_0 = ((d16 & DIGIT_MASK) >> 25) |
                   ((d17 & HIGH_DIGIT_MASK) << 29);
        final long h8_0 = ((d17 & DIGIT_MASK) >> 25) |
                   ((d18 & HIGH_DIGIT_MASK) << 29);
        final long h9_0 = d18 >> 25;

        // Multiply by C
        final long hc0_0 = h0_0 * C_VAL;
        final long hc1_0 = h1_0 * C_VAL;
        final long hc2_0 = h2_0 * C_VAL;
        final long hc3_0 = h3_0 * C_VAL;
        final long hc4_0 = h4_0 * C_VAL;
        final long hc5_0 = h5_0 * C_VAL;
        final long hc6_0 = h6_0 * C_VAL;
        final long hc7_0 = h7_0 * C_VAL;
        final long hc8_0 = h8_0 * C_VAL;
        final long hc9_0 = h9_0 * C_VAL;

        // Add h and l.
        final long kin_0 = hc9_0 >> 25;
        final long s0_0 = l0_0 + hc0_0 + (kin_0 * C_VAL);
        final long k0_0 = s0_0 >> DIGIT_BITS;
        final long s1_0 = l1_0 + hc1_0 + k0_0;
        final long k1_0 = s1_0 >> DIGIT_BITS;
        final long s2_0 = l2_0 + hc2_0 + k1_0;
        final long k2_0 = s2_0 >> DIGIT_BITS;
        final long s3_0 = l3_0 + hc3_0 + k2_0;
        final long k3_0 = s3_0 >> DIGIT_BITS;
        final long s4_0 = l4_0 + hc4_0 + k3_0;
        final long k4_0 = s4_0 >> DIGIT_BITS;
        final long s5_0 = l5_0 + hc5_0 + k4_0;
        final long k5_0 = s5_0 >> DIGIT_BITS;
        final long s6_0 = l6_0 + hc6_0 + k5_0;
        final long k6_0 = s6_0 >> DIGIT_BITS;
        final long s7_0 = l7_0 + hc7_0 + k6_0;
        final long k7_0 = s7_0 >> DIGIT_BITS;
        final long s8_0 = l8_0 + hc8_0 + k7_0;
        final long k8_0 = s8_0 >> DIGIT_BITS;
        final long s9_0 = l9_0 + (hc9_0 & HIGH_DIGIT_MASK) + k8_0;

        out[0] = s0_0 & DIGIT_MASK;
        out[1] = s1_0 & DIGIT_MASK;
        out[2] = s2_0 & DIGIT_MASK;
        out[3] = s3_0 & DIGIT_MASK;
        out[4] = s4_0 & DIGIT_MASK;
        out[5] = s5_0 & DIGIT_MASK;
        out[6] = s6_0 & DIGIT_MASK;
        out[7] = s7_0 & DIGIT_MASK;
        out[8] = s8_0 & DIGIT_MASK;
        out[9] = s9_0;
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
                                  final short b,
                                  final long[] out) {
        final long a0 = a[0] & MUL_DIGIT_MASK;
        final long a1 = a[0] >> MUL_DIGIT_BITS;
        final long a2 = a[1] & MUL_DIGIT_MASK;
        final long a3 = a[1] >> MUL_DIGIT_BITS;
        final long a4 = a[2] & MUL_DIGIT_MASK;
        final long a5 = a[2] >> MUL_DIGIT_BITS;
        final long a6 = a[3] & MUL_DIGIT_MASK;
        final long a7 = a[3] >> MUL_DIGIT_BITS;
        final long a8 = a[4] & MUL_DIGIT_MASK;
        final long a9 = a[4] >> MUL_DIGIT_BITS;
        final long a10 = a[5] & MUL_DIGIT_MASK;
        final long a11 = a[5] >> MUL_DIGIT_BITS;
        final long a12 = a[6] & MUL_DIGIT_MASK;
        final long a13 = a[6] >> MUL_DIGIT_BITS;
        final long a14 = a[7] & MUL_DIGIT_MASK;
        final long a15 = a[7] >> MUL_DIGIT_BITS;
        final long a16 = a[8] & MUL_DIGIT_MASK;
        final long a17 = a[8] >> MUL_DIGIT_BITS;
        final long a18 = a[9];

        final long m0 = a0 * b;
        final long m1 = a1 * b;
        final long m2 = a2 * b;
        final long m3 = a3 * b;
        final long m4 = a4 * b;
        final long m5 = a5 * b;
        final long m6 = a6 * b;
        final long m7 = a7 * b;
        final long m8 = a8 * b;
        final long m9 = a9 * b;
        final long m10 = a10 * b;
        final long m11 = a11 * b;
        final long m12 = a12 * b;
        final long m13 = a13 * b;
        final long m14 = a14 * b;
        final long m15 = a15 * b;
        final long m16 = a16 * b;
        final long m17 = a17 * b;
        final long m18 = a18 * b;

        final long cin = carryOut(a);
        final long d0 =
            m0 + ((m1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + (cin * C_VAL);
        final long c0 = d0 >> DIGIT_BITS;
        final long d1 =
            (m1 >> MUL_DIGIT_BITS) + m2 +
            ((m3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c0;
        final long c1 = d1 >> DIGIT_BITS;
        final long d2 =
            (m3 >> MUL_DIGIT_BITS) + m4 +
            ((m5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c1;
        final long c2 = d2 >> DIGIT_BITS;
        final long d3 =
            (m5 >> MUL_DIGIT_BITS) + m6 +
            ((m7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c2;
        final long c3 = d3 >> DIGIT_BITS;
        final long d4 =
            (m7 >> MUL_DIGIT_BITS) + m8 +
            ((m9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c3;
        final long c4 = d4 >> DIGIT_BITS;
        final long d5 =
            (m9 >> MUL_DIGIT_BITS) + m10 +
            ((m11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c4;
        final long c5 = d5 >> DIGIT_BITS;
        final long d6 =
            (m11 >> MUL_DIGIT_BITS) + m12 +
            ((m13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c5;
        final long c6 = d6 >> DIGIT_BITS;
        final long d7 =
            (m13 >> MUL_DIGIT_BITS) + m14 +
            ((m15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c6;
        final long c7 = d7 >> DIGIT_BITS;
        final long d8 =
            (m15 >> MUL_DIGIT_BITS) + m16 +
            ((m17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c7;
        final long c8 = d8 >> DIGIT_BITS;
        final long d9 = (m17 >> MUL_DIGIT_BITS) + m18 + c8;

        out[0] = d0 & DIGIT_MASK;
        out[1] = d1 & DIGIT_MASK;
        out[2] = d2 & DIGIT_MASK;
        out[3] = d3 & DIGIT_MASK;
        out[4] = d4 & DIGIT_MASK;
        out[5] = d5 & DIGIT_MASK;
        out[6] = d6 & DIGIT_MASK;
        out[7] = d7 & DIGIT_MASK;
        out[8] = d8 & DIGIT_MASK;
        out[9] = d9;
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
        final long a4 = digits[2] & MUL_DIGIT_MASK;
        final long a5 = digits[2] >> MUL_DIGIT_BITS;
        final long a6 = digits[3] & MUL_DIGIT_MASK;
        final long a7 = digits[3] >> MUL_DIGIT_BITS;
        final long a8 = digits[4] & MUL_DIGIT_MASK;
        final long a9 = digits[4] >> MUL_DIGIT_BITS;
        final long a10 = digits[5] & MUL_DIGIT_MASK;
        final long a11 = digits[5] >> MUL_DIGIT_BITS;
        final long a12 = digits[6] & MUL_DIGIT_MASK;
        final long a13 = digits[6] >> MUL_DIGIT_BITS;
        final long a14 = digits[7] & MUL_DIGIT_MASK;
        final long a15 = digits[7] >> MUL_DIGIT_BITS;
        final long a16 = digits[8] & MUL_DIGIT_MASK;
        final long a17 = digits[8] >> MUL_DIGIT_BITS;
        final long a18 = digits[9];

        // Combined multiples
        final long m_0_0 = a0 * a0;
        final long m_0_1 = a0 * a1;
        final long m_0_2 = a0 * a2;
        final long m_0_3 = a0 * a3;
        final long m_0_4 = a0 * a4;
        final long m_0_5 = a0 * a5;
        final long m_0_6 = a0 * a6;
        final long m_0_7 = a0 * a7;
        final long m_0_8 = a0 * a8;
        final long m_0_9 = a0 * a9;
        final long m_0_10 = a0 * a10;
        final long m_0_11 = a0 * a11;
        final long m_0_12 = a0 * a12;
        final long m_0_13 = a0 * a13;
        final long m_0_14 = a0 * a14;
        final long m_0_15 = a0 * a15;
        final long m_0_16 = a0 * a16;
        final long m_0_17 = a0 * a17;
        final long m_0_18 = a0 * a18;
        final long m_1_0 = m_0_1;
        final long m_1_1 = a1 * a1;
        final long m_1_2 = a1 * a2;
        final long m_1_3 = a1 * a3;
        final long m_1_4 = a1 * a4;
        final long m_1_5 = a1 * a5;
        final long m_1_6 = a1 * a6;
        final long m_1_7 = a1 * a7;
        final long m_1_8 = a1 * a8;
        final long m_1_9 = a1 * a9;
        final long m_1_10 = a1 * a10;
        final long m_1_11 = a1 * a11;
        final long m_1_12 = a1 * a12;
        final long m_1_13 = a1 * a13;
        final long m_1_14 = a1 * a14;
        final long m_1_15 = a1 * a15;
        final long m_1_16 = a1 * a16;
        final long m_1_17 = a1 * a17;
        final long m_1_18 = a1 * a18;
        final long m_2_0 = m_0_2;
        final long m_2_1 = m_1_2;
        final long m_2_2 = a2 * a2;
        final long m_2_3 = a2 * a3;
        final long m_2_4 = a2 * a4;
        final long m_2_5 = a2 * a5;
        final long m_2_6 = a2 * a6;
        final long m_2_7 = a2 * a7;
        final long m_2_8 = a2 * a8;
        final long m_2_9 = a2 * a9;
        final long m_2_10 = a2 * a10;
        final long m_2_11 = a2 * a11;
        final long m_2_12 = a2 * a12;
        final long m_2_13 = a2 * a13;
        final long m_2_14 = a2 * a14;
        final long m_2_15 = a2 * a15;
        final long m_2_16 = a2 * a16;
        final long m_2_17 = a2 * a17;
        final long m_2_18 = a2 * a18;
        final long m_3_0 = m_0_3;
        final long m_3_1 = m_1_3;
        final long m_3_2 = m_2_3;
        final long m_3_3 = a3 * a3;
        final long m_3_4 = a3 * a4;
        final long m_3_5 = a3 * a5;
        final long m_3_6 = a3 * a6;
        final long m_3_7 = a3 * a7;
        final long m_3_8 = a3 * a8;
        final long m_3_9 = a3 * a9;
        final long m_3_10 = a3 * a10;
        final long m_3_11 = a3 * a11;
        final long m_3_12 = a3 * a12;
        final long m_3_13 = a3 * a13;
        final long m_3_14 = a3 * a14;
        final long m_3_15 = a3 * a15;
        final long m_3_16 = a3 * a16;
        final long m_3_17 = a3 * a17;
        final long m_3_18 = a3 * a18;
        final long m_4_0 = m_0_4;
        final long m_4_1 = m_1_4;
        final long m_4_2 = m_2_4;
        final long m_4_3 = m_3_4;
        final long m_4_4 = a4 * a4;
        final long m_4_5 = a4 * a5;
        final long m_4_6 = a4 * a6;
        final long m_4_7 = a4 * a7;
        final long m_4_8 = a4 * a8;
        final long m_4_9 = a4 * a9;
        final long m_4_10 = a4 * a10;
        final long m_4_11 = a4 * a11;
        final long m_4_12 = a4 * a12;
        final long m_4_13 = a4 * a13;
        final long m_4_14 = a4 * a14;
        final long m_4_15 = a4 * a15;
        final long m_4_16 = a4 * a16;
        final long m_4_17 = a4 * a17;
        final long m_4_18 = a4 * a18;
        final long m_5_0 = m_0_5;
        final long m_5_1 = m_1_5;
        final long m_5_2 = m_2_5;
        final long m_5_3 = m_3_5;
        final long m_5_4 = m_4_5;
        final long m_5_5 = a5 * a5;
        final long m_5_6 = a5 * a6;
        final long m_5_7 = a5 * a7;
        final long m_5_8 = a5 * a8;
        final long m_5_9 = a5 * a9;
        final long m_5_10 = a5 * a10;
        final long m_5_11 = a5 * a11;
        final long m_5_12 = a5 * a12;
        final long m_5_13 = a5 * a13;
        final long m_5_14 = a5 * a14;
        final long m_5_15 = a5 * a15;
        final long m_5_16 = a5 * a16;
        final long m_5_17 = a5 * a17;
        final long m_5_18 = a5 * a18;
        final long m_6_0 = m_0_6;
        final long m_6_1 = m_1_6;
        final long m_6_2 = m_2_6;
        final long m_6_3 = m_3_6;
        final long m_6_4 = m_4_6;
        final long m_6_5 = m_5_6;
        final long m_6_6 = a6 * a6;
        final long m_6_7 = a6 * a7;
        final long m_6_8 = a6 * a8;
        final long m_6_9 = a6 * a9;
        final long m_6_10 = a6 * a10;
        final long m_6_11 = a6 * a11;
        final long m_6_12 = a6 * a12;
        final long m_6_13 = a6 * a13;
        final long m_6_14 = a6 * a14;
        final long m_6_15 = a6 * a15;
        final long m_6_16 = a6 * a16;
        final long m_6_17 = a6 * a17;
        final long m_6_18 = a6 * a18;
        final long m_7_0 = m_0_7;
        final long m_7_1 = m_1_7;
        final long m_7_2 = m_2_7;
        final long m_7_3 = m_3_7;
        final long m_7_4 = m_4_7;
        final long m_7_5 = m_5_7;
        final long m_7_6 = m_6_7;
        final long m_7_7 = a7 * a7;
        final long m_7_8 = a7 * a8;
        final long m_7_9 = a7 * a9;
        final long m_7_10 = a7 * a10;
        final long m_7_11 = a7 * a11;
        final long m_7_12 = a7 * a12;
        final long m_7_13 = a7 * a13;
        final long m_7_14 = a7 * a14;
        final long m_7_15 = a7 * a15;
        final long m_7_16 = a7 * a16;
        final long m_7_17 = a7 * a17;
        final long m_7_18 = a7 * a18;
        final long m_8_0 = m_0_8;
        final long m_8_1 = m_1_8;
        final long m_8_2 = m_2_8;
        final long m_8_3 = m_3_8;
        final long m_8_4 = m_4_8;
        final long m_8_5 = m_5_8;
        final long m_8_6 = m_6_8;
        final long m_8_7 = m_7_8;
        final long m_8_8 = a8 * a8;
        final long m_8_9 = a8 * a9;
        final long m_8_10 = a8 * a10;
        final long m_8_11 = a8 * a11;
        final long m_8_12 = a8 * a12;
        final long m_8_13 = a8 * a13;
        final long m_8_14 = a8 * a14;
        final long m_8_15 = a8 * a15;
        final long m_8_16 = a8 * a16;
        final long m_8_17 = a8 * a17;
        final long m_8_18 = a8 * a18;
        final long m_9_0 = m_0_9;
        final long m_9_1 = m_1_9;
        final long m_9_2 = m_2_9;
        final long m_9_3 = m_3_9;
        final long m_9_4 = m_4_9;
        final long m_9_5 = m_5_9;
        final long m_9_6 = m_6_9;
        final long m_9_7 = m_7_9;
        final long m_9_8 = m_8_9;
        final long m_9_9 = a9 * a9;
        final long m_9_10 = a9 * a10;
        final long m_9_11 = a9 * a11;
        final long m_9_12 = a9 * a12;
        final long m_9_13 = a9 * a13;
        final long m_9_14 = a9 * a14;
        final long m_9_15 = a9 * a15;
        final long m_9_16 = a9 * a16;
        final long m_9_17 = a9 * a17;
        final long m_9_18 = a9 * a18;
        final long m_10_0 = m_0_10;
        final long m_10_1 = m_1_10;
        final long m_10_2 = m_2_10;
        final long m_10_3 = m_3_10;
        final long m_10_4 = m_4_10;
        final long m_10_5 = m_5_10;
        final long m_10_6 = m_6_10;
        final long m_10_7 = m_7_10;
        final long m_10_8 = m_8_10;
        final long m_10_9 = m_9_10;
        final long m_10_10 = a10 * a10;
        final long m_10_11 = a10 * a11;
        final long m_10_12 = a10 * a12;
        final long m_10_13 = a10 * a13;
        final long m_10_14 = a10 * a14;
        final long m_10_15 = a10 * a15;
        final long m_10_16 = a10 * a16;
        final long m_10_17 = a10 * a17;
        final long m_10_18 = a10 * a18;
        final long m_11_0 = m_0_11;
        final long m_11_1 = m_1_11;
        final long m_11_2 = m_2_11;
        final long m_11_3 = m_3_11;
        final long m_11_4 = m_4_11;
        final long m_11_5 = m_5_11;
        final long m_11_6 = m_6_11;
        final long m_11_7 = m_7_11;
        final long m_11_8 = m_8_11;
        final long m_11_9 = m_9_11;
        final long m_11_10 = m_10_11;
        final long m_11_11 = a11 * a11;
        final long m_11_12 = a11 * a12;
        final long m_11_13 = a11 * a13;
        final long m_11_14 = a11 * a14;
        final long m_11_15 = a11 * a15;
        final long m_11_16 = a11 * a16;
        final long m_11_17 = a11 * a17;
        final long m_11_18 = a11 * a18;
        final long m_12_0 = m_0_12;
        final long m_12_1 = m_1_12;
        final long m_12_2 = m_2_12;
        final long m_12_3 = m_3_12;
        final long m_12_4 = m_4_12;
        final long m_12_5 = m_5_12;
        final long m_12_6 = m_6_12;
        final long m_12_7 = m_7_12;
        final long m_12_8 = m_8_12;
        final long m_12_9 = m_9_12;
        final long m_12_10 = m_10_12;
        final long m_12_11 = m_11_12;
        final long m_12_12 = a12 * a12;
        final long m_12_13 = a12 * a13;
        final long m_12_14 = a12 * a14;
        final long m_12_15 = a12 * a15;
        final long m_12_16 = a12 * a16;
        final long m_12_17 = a12 * a17;
        final long m_12_18 = a12 * a18;
        final long m_13_0 = m_0_13;
        final long m_13_1 = m_1_13;
        final long m_13_2 = m_2_13;
        final long m_13_3 = m_3_13;
        final long m_13_4 = m_4_13;
        final long m_13_5 = m_5_13;
        final long m_13_6 = m_6_13;
        final long m_13_7 = m_7_13;
        final long m_13_8 = m_8_13;
        final long m_13_9 = m_9_13;
        final long m_13_10 = m_10_13;
        final long m_13_11 = m_11_13;
        final long m_13_12 = m_12_13;
        final long m_13_13 = a13 * a13;
        final long m_13_14 = a13 * a14;
        final long m_13_15 = a13 * a15;
        final long m_13_16 = a13 * a16;
        final long m_13_17 = a13 * a17;
        final long m_13_18 = a13 * a18;
        final long m_14_0 = m_0_14;
        final long m_14_1 = m_1_14;
        final long m_14_2 = m_2_14;
        final long m_14_3 = m_3_14;
        final long m_14_4 = m_4_14;
        final long m_14_5 = m_5_14;
        final long m_14_6 = m_6_14;
        final long m_14_7 = m_7_14;
        final long m_14_8 = m_8_14;
        final long m_14_9 = m_9_14;
        final long m_14_10 = m_10_14;
        final long m_14_11 = m_11_14;
        final long m_14_12 = m_12_14;
        final long m_14_13 = m_13_14;
        final long m_14_14 = a14 * a14;
        final long m_14_15 = a14 * a15;
        final long m_14_16 = a14 * a16;
        final long m_14_17 = a14 * a17;
        final long m_14_18 = a14 * a18;
        final long m_15_0 = m_0_15;
        final long m_15_1 = m_1_15;
        final long m_15_2 = m_2_15;
        final long m_15_3 = m_3_15;
        final long m_15_4 = m_4_15;
        final long m_15_5 = m_5_15;
        final long m_15_6 = m_6_15;
        final long m_15_7 = m_7_15;
        final long m_15_8 = m_8_15;
        final long m_15_9 = m_9_15;
        final long m_15_10 = m_10_15;
        final long m_15_11 = m_11_15;
        final long m_15_12 = m_12_15;
        final long m_15_13 = m_13_15;
        final long m_15_14 = m_14_15;
        final long m_15_15 = a15 * a15;
        final long m_15_16 = a15 * a16;
        final long m_15_17 = a15 * a17;
        final long m_15_18 = a15 * a18;
        final long m_16_0 = m_0_16;
        final long m_16_1 = m_1_16;
        final long m_16_2 = m_2_16;
        final long m_16_3 = m_3_16;
        final long m_16_4 = m_4_16;
        final long m_16_5 = m_5_16;
        final long m_16_6 = m_6_16;
        final long m_16_7 = m_7_16;
        final long m_16_8 = m_8_16;
        final long m_16_9 = m_9_16;
        final long m_16_10 = m_10_16;
        final long m_16_11 = m_11_16;
        final long m_16_12 = m_12_16;
        final long m_16_13 = m_13_16;
        final long m_16_14 = m_14_16;
        final long m_16_15 = m_15_16;
        final long m_16_16 = a16 * a16;
        final long m_16_17 = a16 * a17;
        final long m_16_18 = a16 * a18;
        final long m_17_0 = m_0_17;
        final long m_17_1 = m_1_17;
        final long m_17_2 = m_2_17;
        final long m_17_3 = m_3_17;
        final long m_17_4 = m_4_17;
        final long m_17_5 = m_5_17;
        final long m_17_6 = m_6_17;
        final long m_17_7 = m_7_17;
        final long m_17_8 = m_8_17;
        final long m_17_9 = m_9_17;
        final long m_17_10 = m_10_17;
        final long m_17_11 = m_11_17;
        final long m_17_12 = m_12_17;
        final long m_17_13 = m_13_17;
        final long m_17_14 = m_14_17;
        final long m_17_15 = m_15_17;
        final long m_17_16 = m_16_17;
        final long m_17_17 = a17 * a17;
        final long m_17_18 = a17 * a18;
        final long m_18_0 = m_0_18;
        final long m_18_1 = m_1_18;
        final long m_18_2 = m_2_18;
        final long m_18_3 = m_3_18;
        final long m_18_4 = m_4_18;
        final long m_18_5 = m_5_18;
        final long m_18_6 = m_6_18;
        final long m_18_7 = m_7_18;
        final long m_18_8 = m_8_18;
        final long m_18_9 = m_9_18;
        final long m_18_10 = m_10_18;
        final long m_18_11 = m_11_18;
        final long m_18_12 = m_12_18;
        final long m_18_13 = m_13_18;
        final long m_18_14 = m_14_18;
        final long m_18_15 = m_15_18;
        final long m_18_16 = m_16_18;
        final long m_18_17 = m_17_18;
        final long m_18_18 = a18 * a18;

        // Compute the 40-digit combined product using 64-bit operations.
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
            ((m_3_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c0;
        final long c1 = d1 >> DIGIT_BITS;
        final long d2 =
            (m_0_3 >> MUL_DIGIT_BITS) + m_0_4 +
            ((m_0_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_2 >> MUL_DIGIT_BITS) + m_1_3 +
            ((m_1_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_1 >> MUL_DIGIT_BITS) + m_2_2 +
            ((m_2_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_0 >> MUL_DIGIT_BITS) + m_3_1 +
            ((m_3_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_4_0 + ((m_4_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_5_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c1;
        final long c2 = d2 >> DIGIT_BITS;
        final long d3 =
            (m_0_5 >> MUL_DIGIT_BITS) + m_0_6 +
            ((m_0_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_4 >> MUL_DIGIT_BITS) + m_1_5 +
            ((m_1_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_3 >> MUL_DIGIT_BITS) + m_2_4 +
            ((m_2_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_2 >> MUL_DIGIT_BITS) + m_3_3 +
            ((m_3_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_1 >> MUL_DIGIT_BITS) + m_4_2 +
            ((m_4_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_0 >> MUL_DIGIT_BITS) + m_5_1 +
            ((m_5_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_6_0 + ((m_6_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_7_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c2;
        final long c3 = d3 >> DIGIT_BITS;
        final long d4 =
            (m_0_7 >> MUL_DIGIT_BITS) + m_0_8 +
            ((m_0_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_6 >> MUL_DIGIT_BITS) + m_1_7 +
            ((m_1_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_5 >> MUL_DIGIT_BITS) + m_2_6 +
            ((m_2_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_4 >> MUL_DIGIT_BITS) + m_3_5 +
            ((m_3_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_3 >> MUL_DIGIT_BITS) + m_4_4 +
            ((m_4_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_2 >> MUL_DIGIT_BITS) + m_5_3 +
            ((m_5_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_1 >> MUL_DIGIT_BITS) + m_6_2 +
            ((m_6_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_0 >> MUL_DIGIT_BITS) + m_7_1 +
            ((m_7_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_8_0 + ((m_8_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_9_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c3;
        final long c4 = d4 >> DIGIT_BITS;
        final long d5 =
            (m_0_9 >> MUL_DIGIT_BITS) + m_0_10 +
            ((m_0_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_8 >> MUL_DIGIT_BITS) + m_1_9 +
            ((m_1_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_7 >> MUL_DIGIT_BITS) + m_2_8 +
            ((m_2_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_6 >> MUL_DIGIT_BITS) + m_3_7 +
            ((m_3_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_5 >> MUL_DIGIT_BITS) + m_4_6 +
            ((m_4_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_4 >> MUL_DIGIT_BITS) + m_5_5 +
            ((m_5_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_3 >> MUL_DIGIT_BITS) + m_6_4 +
            ((m_6_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_2 >> MUL_DIGIT_BITS) + m_7_3 +
            ((m_7_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_1 >> MUL_DIGIT_BITS) + m_8_2 +
            ((m_8_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_0 >> MUL_DIGIT_BITS) + m_9_1 +
            ((m_9_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_10_0 + ((m_10_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_11_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c4;
        final long c5 = d5 >> DIGIT_BITS;
        final long d6 =
            (m_0_11 >> MUL_DIGIT_BITS) + m_0_12 +
            ((m_0_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_10 >> MUL_DIGIT_BITS) + m_1_11 +
            ((m_1_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_9 >> MUL_DIGIT_BITS) + m_2_10 +
            ((m_2_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_8 >> MUL_DIGIT_BITS) + m_3_9 +
            ((m_3_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_7 >> MUL_DIGIT_BITS) + m_4_8 +
            ((m_4_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_6 >> MUL_DIGIT_BITS) + m_5_7 +
            ((m_5_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_5 >> MUL_DIGIT_BITS) + m_6_6 +
            ((m_6_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_4 >> MUL_DIGIT_BITS) + m_7_5 +
            ((m_7_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_3 >> MUL_DIGIT_BITS) + m_8_4 +
            ((m_8_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_2 >> MUL_DIGIT_BITS) + m_9_3 +
            ((m_9_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_1 >> MUL_DIGIT_BITS) + m_10_2 +
            ((m_10_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_0 >> MUL_DIGIT_BITS) + m_11_1 +
            ((m_11_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_12_0 + ((m_12_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_13_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c5;
        final long c6 = d6 >> DIGIT_BITS;
        final long d7 =
            (m_0_13 >> MUL_DIGIT_BITS) + m_0_14 +
            ((m_0_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_12 >> MUL_DIGIT_BITS) + m_1_13 +
            ((m_1_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_11 >> MUL_DIGIT_BITS) + m_2_12 +
            ((m_2_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_10 >> MUL_DIGIT_BITS) + m_3_11 +
            ((m_3_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_9 >> MUL_DIGIT_BITS) + m_4_10 +
            ((m_4_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_8 >> MUL_DIGIT_BITS) + m_5_9 +
            ((m_5_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_7 >> MUL_DIGIT_BITS) + m_6_8 +
            ((m_6_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_6 >> MUL_DIGIT_BITS) + m_7_7 +
            ((m_7_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_5 >> MUL_DIGIT_BITS) + m_8_6 +
            ((m_8_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_4 >> MUL_DIGIT_BITS) + m_9_5 +
            ((m_9_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_3 >> MUL_DIGIT_BITS) + m_10_4 +
            ((m_10_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_2 >> MUL_DIGIT_BITS) + m_11_3 +
            ((m_11_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_1 >> MUL_DIGIT_BITS) + m_12_2 +
            ((m_12_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_0 >> MUL_DIGIT_BITS) + m_13_1 +
            ((m_13_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_14_0 + ((m_14_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_15_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c6;
        final long c7 = d7 >> DIGIT_BITS;
        final long d8 =
            (m_0_15 >> MUL_DIGIT_BITS) + m_0_16 +
            ((m_0_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_1_14 >> MUL_DIGIT_BITS) + m_1_15 +
            ((m_1_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_13 >> MUL_DIGIT_BITS) + m_2_14 +
            ((m_2_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_12 >> MUL_DIGIT_BITS) + m_3_13 +
            ((m_3_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_11 >> MUL_DIGIT_BITS) + m_4_12 +
            ((m_4_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_10 >> MUL_DIGIT_BITS) + m_5_11 +
            ((m_5_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_9 >> MUL_DIGIT_BITS) + m_6_10 +
            ((m_6_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_8 >> MUL_DIGIT_BITS) + m_7_9 +
            ((m_7_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_7 >> MUL_DIGIT_BITS) + m_8_8 +
            ((m_8_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_6 >> MUL_DIGIT_BITS) + m_9_7 +
            ((m_9_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_5 >> MUL_DIGIT_BITS) + m_10_6 +
            ((m_10_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_4 >> MUL_DIGIT_BITS) + m_11_5 +
            ((m_11_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_3 >> MUL_DIGIT_BITS) + m_12_4 +
            ((m_12_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_2 >> MUL_DIGIT_BITS) + m_13_3 +
            ((m_13_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_1 >> MUL_DIGIT_BITS) + m_14_2 +
            ((m_14_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_0 >> MUL_DIGIT_BITS) + m_15_1 +
            ((m_15_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_16_0 + ((m_16_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            ((m_17_0 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c7;
        final long c8 = d8 >> DIGIT_BITS;
        final long d9 =
            (m_0_17 >> MUL_DIGIT_BITS) + m_0_18 +
            (m_1_16 >> MUL_DIGIT_BITS) + m_1_17 +
            ((m_1_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_2_15 >> MUL_DIGIT_BITS) + m_2_16 +
            ((m_2_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_3_14 >> MUL_DIGIT_BITS) + m_3_15 +
            ((m_3_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_13 >> MUL_DIGIT_BITS) + m_4_14 +
            ((m_4_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_12 >> MUL_DIGIT_BITS) + m_5_13 +
            ((m_5_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_11 >> MUL_DIGIT_BITS) + m_6_12 +
            ((m_6_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_10 >> MUL_DIGIT_BITS) + m_7_11 +
            ((m_7_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_9 >> MUL_DIGIT_BITS) + m_8_10 +
            ((m_8_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_8 >> MUL_DIGIT_BITS) + m_9_9 +
            ((m_9_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_7 >> MUL_DIGIT_BITS) + m_10_8 +
            ((m_10_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_6 >> MUL_DIGIT_BITS) + m_11_7 +
            ((m_11_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_5 >> MUL_DIGIT_BITS) + m_12_6 +
            ((m_12_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_4 >> MUL_DIGIT_BITS) + m_13_5 +
            ((m_13_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_3 >> MUL_DIGIT_BITS) + m_14_4 +
            ((m_14_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_2 >> MUL_DIGIT_BITS) + m_15_3 +
            ((m_15_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_1 >> MUL_DIGIT_BITS) + m_16_2 +
            ((m_16_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_0 >> MUL_DIGIT_BITS) + m_17_1 +
            ((m_17_2 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            m_18_0 + ((m_18_1 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c8;
        final long c9 = d9 >> DIGIT_BITS;
        final long d10 =
            (m_1_18 >> MUL_DIGIT_BITS) +
            (m_2_17 >> MUL_DIGIT_BITS) + m_2_18 +
            (m_3_16 >> MUL_DIGIT_BITS) + m_3_17 +
            ((m_3_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_4_15 >> MUL_DIGIT_BITS) + m_4_16 +
            ((m_4_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_5_14 >> MUL_DIGIT_BITS) + m_5_15 +
            ((m_5_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_13 >> MUL_DIGIT_BITS) + m_6_14 +
            ((m_6_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_12 >> MUL_DIGIT_BITS) + m_7_13 +
            ((m_7_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_11 >> MUL_DIGIT_BITS) + m_8_12 +
            ((m_8_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_10 >> MUL_DIGIT_BITS) + m_9_11 +
            ((m_9_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_9 >> MUL_DIGIT_BITS) + m_10_10 +
            ((m_10_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_8 >> MUL_DIGIT_BITS) + m_11_9 +
            ((m_11_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_7 >> MUL_DIGIT_BITS) + m_12_8 +
            ((m_12_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_6 >> MUL_DIGIT_BITS) + m_13_7 +
            ((m_13_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_5 >> MUL_DIGIT_BITS) + m_14_6 +
            ((m_14_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_4 >> MUL_DIGIT_BITS) + m_15_5 +
            ((m_15_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_3 >> MUL_DIGIT_BITS) + m_16_4 +
            ((m_16_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_2 >> MUL_DIGIT_BITS) + m_17_3 +
            ((m_17_4 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_1 >> MUL_DIGIT_BITS) + m_18_2 +
            ((m_18_3 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c9;
        final long c10 = d10 >> DIGIT_BITS;
        final long d11 =
            (m_3_18 >> MUL_DIGIT_BITS) +
            (m_4_17 >> MUL_DIGIT_BITS) + m_4_18 +
            (m_5_16 >> MUL_DIGIT_BITS) + m_5_17 +
            ((m_5_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_6_15 >> MUL_DIGIT_BITS) + m_6_16 +
            ((m_6_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_7_14 >> MUL_DIGIT_BITS) + m_7_15 +
            ((m_7_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_13 >> MUL_DIGIT_BITS) + m_8_14 +
            ((m_8_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_12 >> MUL_DIGIT_BITS) + m_9_13 +
            ((m_9_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_11 >> MUL_DIGIT_BITS) + m_10_12 +
            ((m_10_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_10 >> MUL_DIGIT_BITS) + m_11_11 +
            ((m_11_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_9 >> MUL_DIGIT_BITS) + m_12_10 +
            ((m_12_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_8 >> MUL_DIGIT_BITS) + m_13_9 +
            ((m_13_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_7 >> MUL_DIGIT_BITS) + m_14_8 +
            ((m_14_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_6 >> MUL_DIGIT_BITS) + m_15_7 +
            ((m_15_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_5 >> MUL_DIGIT_BITS) + m_16_6 +
            ((m_16_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_4 >> MUL_DIGIT_BITS) + m_17_5 +
            ((m_17_6 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_3 >> MUL_DIGIT_BITS) + m_18_4 +
            ((m_18_5 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c10;
        final long c11 = d11 >> DIGIT_BITS;
        final long d12 =
            (m_5_18 >> MUL_DIGIT_BITS) +
            (m_6_17 >> MUL_DIGIT_BITS) + m_6_18 +
            (m_7_16 >> MUL_DIGIT_BITS) + m_7_17 +
            ((m_7_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_8_15 >> MUL_DIGIT_BITS) + m_8_16 +
            ((m_8_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_9_14 >> MUL_DIGIT_BITS) + m_9_15 +
            ((m_9_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_13 >> MUL_DIGIT_BITS) + m_10_14 +
            ((m_10_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_12 >> MUL_DIGIT_BITS) + m_11_13 +
            ((m_11_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_11 >> MUL_DIGIT_BITS) + m_12_12 +
            ((m_12_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_10 >> MUL_DIGIT_BITS) + m_13_11 +
            ((m_13_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_9 >> MUL_DIGIT_BITS) + m_14_10 +
            ((m_14_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_8 >> MUL_DIGIT_BITS) + m_15_9 +
            ((m_15_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_7 >> MUL_DIGIT_BITS) + m_16_8 +
            ((m_16_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_6 >> MUL_DIGIT_BITS) + m_17_7 +
            ((m_17_8 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_5 >> MUL_DIGIT_BITS) + m_18_6 +
            ((m_18_7 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c11;
        final long c12 = d12 >> DIGIT_BITS;
        final long d13 =
            (m_7_18 >> MUL_DIGIT_BITS) +
            (m_8_17 >> MUL_DIGIT_BITS) + m_8_18 +
            (m_9_16 >> MUL_DIGIT_BITS) + m_9_17 +
            ((m_9_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_10_15 >> MUL_DIGIT_BITS) + m_10_16 +
            ((m_10_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_11_14 >> MUL_DIGIT_BITS) + m_11_15 +
            ((m_11_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_13 >> MUL_DIGIT_BITS) + m_12_14 +
            ((m_12_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_12 >> MUL_DIGIT_BITS) + m_13_13 +
            ((m_13_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_11 >> MUL_DIGIT_BITS) + m_14_12 +
            ((m_14_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_10 >> MUL_DIGIT_BITS) + m_15_11 +
            ((m_15_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_9 >> MUL_DIGIT_BITS) + m_16_10 +
            ((m_16_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_8 >> MUL_DIGIT_BITS) + m_17_9 +
            ((m_17_10 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_7 >> MUL_DIGIT_BITS) + m_18_8 +
            ((m_18_9 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) + c12;
        final long c13 = d13 >> DIGIT_BITS;
        final long d14 =
            (m_9_18 >> MUL_DIGIT_BITS) +
            (m_10_17 >> MUL_DIGIT_BITS) + m_10_18 +
            (m_11_16 >> MUL_DIGIT_BITS) + m_11_17 +
            ((m_11_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_12_15 >> MUL_DIGIT_BITS) + m_12_16 +
            ((m_12_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_13_14 >> MUL_DIGIT_BITS) + m_13_15 +
            ((m_13_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_13 >> MUL_DIGIT_BITS) + m_14_14 +
            ((m_14_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_12 >> MUL_DIGIT_BITS) + m_15_13 +
            ((m_15_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_11 >> MUL_DIGIT_BITS) + m_16_12 +
            ((m_16_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_10 >> MUL_DIGIT_BITS) + m_17_11 +
            ((m_17_12 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_9 >> MUL_DIGIT_BITS) + m_18_10 +
            ((m_18_11 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c13;
        final long c14 = d14 >> DIGIT_BITS;
        final long d15 =
            (m_11_18 >> MUL_DIGIT_BITS) +
            (m_12_17 >> MUL_DIGIT_BITS) + m_12_18 +
            (m_13_16 >> MUL_DIGIT_BITS) + m_13_17 +
            ((m_13_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_14_15 >> MUL_DIGIT_BITS) + m_14_16 +
            ((m_14_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_15_14 >> MUL_DIGIT_BITS) + m_15_15 +
            ((m_15_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_13 >> MUL_DIGIT_BITS) + m_16_14 +
            ((m_16_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_12 >> MUL_DIGIT_BITS) + m_17_13 +
            ((m_17_14 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_11 >> MUL_DIGIT_BITS) + m_18_12 +
            ((m_18_13 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c14;
        final long c15 = d15 >> DIGIT_BITS;
        final long d16 =
            (m_13_18 >> MUL_DIGIT_BITS) +
            (m_14_17 >> MUL_DIGIT_BITS) + m_14_18 +
            (m_15_16 >> MUL_DIGIT_BITS) + m_15_17 +
            ((m_15_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_16_15 >> MUL_DIGIT_BITS) + m_16_16 +
            ((m_16_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_17_14 >> MUL_DIGIT_BITS) + m_17_15 +
            ((m_17_16 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_13 >> MUL_DIGIT_BITS) + m_18_14 +
            ((m_18_15 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c15;
        final long c16 = d16 >> DIGIT_BITS;
        final long d17 =
            (m_15_18 >> MUL_DIGIT_BITS) +
            (m_16_17 >> MUL_DIGIT_BITS) + m_16_18 +
            (m_17_16 >> MUL_DIGIT_BITS) + m_17_17 +
            ((m_17_18 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            (m_18_15 >> MUL_DIGIT_BITS) + m_18_16 +
            ((m_18_17 & MUL_DIGIT_MASK) << MUL_DIGIT_BITS) +
            c16;
        final long c17 = d17 >> DIGIT_BITS;
        final long d18 =
            (m_17_18 >> MUL_DIGIT_BITS) +
            (m_18_17 >> MUL_DIGIT_BITS) + m_18_18 + c17;

        // Modular reduction by a pseudo-mersenne prime of the form 2^n - c.

        // These are the n low-order bits.
        final long l0_0 = d0 & DIGIT_MASK;
        final long l1_0 = d1 & DIGIT_MASK;
        final long l2_0 = d2 & DIGIT_MASK;
        final long l3_0 = d3 & DIGIT_MASK;
        final long l4_0 = d4 & DIGIT_MASK;
        final long l5_0 = d5 & DIGIT_MASK;
        final long l6_0 = d6 & DIGIT_MASK;
        final long l7_0 = d7 & DIGIT_MASK;
        final long l8_0 = d8 & DIGIT_MASK;
        final long l9_0 = d9 & HIGH_DIGIT_MASK;

        // Shift the high bits down into another n-bit number.
        final long h0_0 = ((d9 & DIGIT_MASK) >> 25) |
                          ((d10 & HIGH_DIGIT_MASK) << 29);
        final long h1_0 = ((d10 & DIGIT_MASK) >> 25) |
                          ((d11 & HIGH_DIGIT_MASK) << 29);
        final long h2_0 = ((d11 & DIGIT_MASK) >> 25) |
                          ((d12 & HIGH_DIGIT_MASK) << 29);
        final long h3_0 = ((d12 & DIGIT_MASK) >> 25) |
                          ((d13 & HIGH_DIGIT_MASK) << 29);
        final long h4_0 = ((d13 & DIGIT_MASK) >> 25) |
                          ((d14 & HIGH_DIGIT_MASK) << 29);
        final long h5_0 = ((d14 & DIGIT_MASK) >> 25) |
                          ((d15 & HIGH_DIGIT_MASK) << 29);
        final long h6_0 = ((d15 & DIGIT_MASK) >> 25) |
                          ((d16 & HIGH_DIGIT_MASK) << 29);
        final long h7_0 = ((d16 & DIGIT_MASK) >> 25) |
                          ((d17 & HIGH_DIGIT_MASK) << 29);
        final long h8_0 = ((d17 & DIGIT_MASK) >> 25) |
                          ((d18 & HIGH_DIGIT_MASK) << 29);
        final long h9_0 = d18 >> 25;

        // Multiply by C
        final long hc0_0 = h0_0 * C_VAL;
        final long hc1_0 = h1_0 * C_VAL;
        final long hc2_0 = h2_0 * C_VAL;
        final long hc3_0 = h3_0 * C_VAL;
        final long hc4_0 = h4_0 * C_VAL;
        final long hc5_0 = h5_0 * C_VAL;
        final long hc6_0 = h6_0 * C_VAL;
        final long hc7_0 = h7_0 * C_VAL;
        final long hc8_0 = h8_0 * C_VAL;
        final long hc9_0 = h9_0 * C_VAL;

        // Add h and l.
        final long kin_0 = hc9_0 >> 25;
        final long s0_0 = l0_0 + hc0_0 + (kin_0 * C_VAL);
        final long k0_0 = s0_0 >> DIGIT_BITS;
        final long s1_0 = l1_0 + hc1_0 + k0_0;
        final long k1_0 = s1_0 >> DIGIT_BITS;
        final long s2_0 = l2_0 + hc2_0 + k1_0;
        final long k2_0 = s2_0 >> DIGIT_BITS;
        final long s3_0 = l3_0 + hc3_0 + k2_0;
        final long k3_0 = s3_0 >> DIGIT_BITS;
        final long s4_0 = l4_0 + hc4_0 + k3_0;
        final long k4_0 = s4_0 >> DIGIT_BITS;
        final long s5_0 = l5_0 + hc5_0 + k4_0;
        final long k5_0 = s5_0 >> DIGIT_BITS;
        final long s6_0 = l6_0 + hc6_0 + k5_0;
        final long k6_0 = s6_0 >> DIGIT_BITS;
        final long s7_0 = l7_0 + hc7_0 + k6_0;
        final long k7_0 = s7_0 >> DIGIT_BITS;
        final long s8_0 = l8_0 + hc8_0 + k7_0;
        final long k8_0 = s8_0 >> DIGIT_BITS;
        final long s9_0 = l9_0 + (hc9_0 & HIGH_DIGIT_MASK) + k8_0;

        digits[0] = s0_0 & DIGIT_MASK;
        digits[1] = s1_0 & DIGIT_MASK;
        digits[2] = s2_0 & DIGIT_MASK;
        digits[3] = s3_0 & DIGIT_MASK;
        digits[4] = s4_0 & DIGIT_MASK;
        digits[5] = s5_0 & DIGIT_MASK;
        digits[6] = s6_0 & DIGIT_MASK;
        digits[7] = s7_0 & DIGIT_MASK;
        digits[8] = s8_0 & DIGIT_MASK;
        digits[9] = s9_0;
    }

    /**
     * Low-level digits multiplicative inverse (reciprocal).
     *
     * @param digits The digits array to invert.
     */
    private static void invDigits(final long[] digits) {
        // First digit is 1.
        final long[] sqval = Arrays.copyOf(digits, NUM_DIGITS);

        // Second digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // Third, fourth, fifth, sixth digits are 0.
        squareDigits(sqval);
        squareDigits(sqval);
        squareDigits(sqval);
        squareDigits(sqval);

        // Seventh digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // Eigth digit is 0.
        squareDigits(sqval);

        // All the remaining digits are 1.
        for(int i = 8; i < 511; i++) {
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


    private static void sqrtPowerDigits(final long[] digits) {
        // First digit is 1.
        final long[] sqval = Arrays.copyOf(digits, NUM_DIGITS);
        squareDigits(sqval);

        // Second and third digits are 0.
        squareDigits(sqval);
        squareDigits(sqval);

        // Fourth digit is 1.
        mulDigits(digits, sqval, digits);
        squareDigits(sqval);

        // Fifth digit is 0.

        // All the remaining digits are 1.
        for(int i = 5; i < 508; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }
    }

    private static void invSqrtPowerDigits(final long[] digits) {
        // First and second digits are 1.
        final long[] sqval = Arrays.copyOf(digits, NUM_DIGITS);
        squareDigits(sqval);

        mulDigits(digits, sqval, digits);
        squareDigits(sqval);

        // Third digit is 0.
        squareDigits(sqval);

        // Fourth and fifth digits are 1.
        mulDigits(digits, sqval, digits);
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);
        squareDigits(sqval);

        // Sixth digit is 0.
        squareDigits(sqval);

        // Seventh digit is 1.
        mulDigits(digits, sqval, digits);
        squareDigits(sqval);

        // Eighth digit is 0.
        squareDigits(sqval);

        // All digits up to 508 are 1.
        for(int i = 8; i < 508; i++) {
            mulDigits(digits, sqval, digits);
            squareDigits(sqval);
        }

        // Digit 508 is 0.
        squareDigits(sqval);

        // Last two digits are 1.
        mulDigits(digits, sqval, digits);
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);
    }


    private static void legendrePowerDigits(final long[] digits) {
        // First digit is 0.
        squareDigits(digits);

        // Second digit is 1.
        final long[] sqval = Arrays.copyOf(digits, NUM_DIGITS);

        // Third, fourth, and fifth digits are 0.
        squareDigits(sqval);
        squareDigits(sqval);
        squareDigits(sqval);

        // Sixth digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // Seventh digit is 0.
        squareDigits(sqval);

        // All the remaining digits are 1.
        for(int i = 7; i < 510; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }
    }

    private static void legendreQuarticPowerDigits(final long[] digits) {
        // First digit is 1.
        final long[] sqval = Arrays.copyOf(digits, NUM_DIGITS);

        // Second, third, and fourth digits are 0.
        squareDigits(sqval);
        squareDigits(sqval);
        squareDigits(sqval);

        // Fifth digit is 1.
        squareDigits(sqval);
        mulDigits(digits, sqval, digits);

        // Sixth digit is 0.
        squareDigits(sqval);

        // All the remaining digits are 1.
        for(int i = 6; i < 509; i++) {
            squareDigits(sqval);
            mulDigits(digits, sqval, digits);
        }
    }

    private static String digitsToString(final long[] digits) {
        final long[] digitscpy = Arrays.copyOf(digits, NUM_DIGITS);
        final byte[] bytes = new byte[PACKED_BYTES];

        normalizeDigits(digitscpy);
        packDigits(digitscpy, bytes, 0);

        return PrimeField.packedToString(bytes);
    }
}
