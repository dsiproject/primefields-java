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

/**
 * Common superclass for elements of a finite integer field, modulo a
 * prime number {@code P} such that {@code P mod 4 = 1}.
 * <p>
 * This class exposes the quartic legendre symbol.
 *
 * @param <Val> The type of arguments to arithmetic functions,
 *              typically the leaf subclass.
 */
public abstract class PrimeField1Mod4<Val extends PrimeField1Mod4<Val>>
    extends PrimeField<Val> {
    /**
     * Initialize with a digits array.
     *
     * This constructor initializes the {@code digits} field.  The
     * created object takes possession of the array.  To initialize
     * from an array without taking possession, use {@link #set}.
     *
     * @param digits The {@code digits} field.
     * @see #set(long[])
     */
    protected PrimeField1Mod4(final long[] digits) {
        super(digits);
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
     *
     * @return {@code 1} if the value is a quartic residue, {@code -1}
     *         if not.
     * @see #legendre
     */
    public abstract byte legendreQuartic();
}
