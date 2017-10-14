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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = "stress")
public abstract class PrimeFieldStressTest<P extends PrimeField<P>> {
    protected final P[] argData;

    protected PrimeFieldStressTest(final P[] argData) {
        this.argData = argData;
    }

    /**
     * Test {@code a + b - a == b}.
     *
     * @param a The LHS.
     * @param b The RHS.
     */
    @Test(description = "Test that a + b - a == b")
    public void addSubTest() {
        for(int i = 0; i < argData.length; i++) {
            for(int j = 0; j < argData.length; j++) {
                doAddSubTest(argData[i], argData[j]);
            }
        }
    }


    private void doAddSubTest(final P a,
                                     final P b) {
        final P val = a.clone();

        val.add(b);
        val.sub(a);

        Assert.assertEquals(val, b);
    }

    /**
     * Test {@code (a * b) / a == b}.
     *
     * @param a The LHS.
     * @param b The RHS.
     */
    /*
    @Test(description = "Test that a * b / a == b")
    public void mulDivTest() {
        for(int i = 0; i < argData.length; i++) {
            for(int j = 0; j < argData.length; j++) {
                doMulDivTest(argData[i], argData[j]);
            }
        }
    }
    */
    private void doMulDivTest(final P a,
                                  final P b) {
        final P val = a.clone();

        val.mul(b);
        val.div(a);

        Assert.assertEquals(val, b);
    }

    /**
     * Test {@code a * a == a.square()}.
     *
     * @param a The number to test.
     */
    @Test(description = "Test that a * a == a.square()")
    public void mulSquareTest() {
        for(int i = 0; i < argData.length; i++) {
            doMulSquareTest(argData[i]);
        }
    }

    private void doMulSquareTest(final P a) {
        final P val = a.clone();
        final P b = a.clone();

        val.square();
        b.mul(b);

        Assert.assertEquals(val, b);
    }

    /**
     * Test {@code a.invSqrt() == a.sqrt().inv()}.
     *
     * @param a The number to test.
     */
    @Test(description = "Test that a.invSqrt() == a.sqrt().inv()")
    public void invSqrtMulTest() {
        for(int i = 0; i < argData.length; i++) {
            doInvSqrtMulTest(argData[i]);
        }
    }

    private void doInvSqrtMulTest(final P a) {
        final int leg = a.legendre();

        if (leg == 1) {
            final P val = a.clone();
            final P b = a.clone();

            val.invSqrt();
            b.sqrt();
            b.inv();

            Assert.assertEquals(val, b);
        }
    }

    /**
     * Test quadratic residue properties of a number.  This tests that
     * {@code legendre(a) == 1} or {@code legendre(a) == -1}, and if
     * {@code legendre(a) == 1}, then {@code sqrt(a) ^ 2 == a}.
     *
     * @param a The number to test.
     */
    @Test(description = "Test properties of quadratic residues")
    public void quadraticResidueTest() {
        for(int i = 0; i < argData.length; i++) {
            doQuadraticResidueTest(argData[i]);
        }
    }

    private void doQuadraticResidueTest(final P a) {
        final int leg = a.legendre();

        if (leg == 1) {
            // Either legendre(a) == 1 and sqrt and square are inverses...
            final P b = a.clone();

            a.sqrt();
            a.square();

            Assert.assertEquals(a, b);
        } else {
            // Or legendre(a) == -1
            Assert.assertEquals(leg, -1);
        }
    }

    @Test(description = "Test properties of abstract values")
    public void absTest() {
        for(int i = 0; i < argData.length; i++) {
            doAbsTest(argData[i]);
        }
    }

    private void doAbsTest(final P a) {
        final long signval = a.sign();

        if (signval == 0) {
            final P val = a.clone();

            val.abs();
            Assert.assertEquals(val, a);
        } else {
            Assert.assertEquals(signval, 1);

            final P aval = a.clone();
            final P bval = a.clone();

            aval.abs();
            bval.mul((short)-1);

            Assert.assertEquals(aval, bval);
        }
    }
}
