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

public abstract class PrimeField1Mod4StressTest<P extends PrimeField1Mod4<P>>
    extends PrimeFieldStressTest<P> {
    protected PrimeField1Mod4StressTest(final P[] argData) {
        super(argData);
    }

    /**
     * Test quartic residue properties of a number.  This tests that
     * {@code legendre(a) == 1} or {@code legendre(a) == -1}, and if
     * {@code legendre(a) == 1}, then {@code sqrt(a) ^ 2 == a}.
     */
    @Test(description = "Test properties of quartic residues")
    public void quarticResidueTest() {
        for(int i = 0; i < argData.length; i++) {
            doQuarticResidueTest(argData[i]);
        }
    }

    private void doQuarticResidueTest(final P a) {
        final int leg = a.legendre();

        if (leg == 1) {
            // If we are a quadratic residue, proceed
            final int qleg = a.legendreQuartic();
            final P b = a.clone();

            a.sqrt();

            final int sqrtleg = a.legendre();

            // legendre(sqrt(a)) == legendreQuartic(a)
            Assert.assertEquals(qleg, sqrtleg);

            if (qleg == 1) {
                // If we are a quartic residue, proceed

                a.sqrt();
                a.square();
                a.square();

                Assert.assertEquals(a, b);
            } else {
                // Otherwise, quartic legendre must be -1
                Assert.assertEquals(qleg, -1);
            }
        } else {
                // Otherwise, legendre must be -1
            Assert.assertEquals(leg, -1);
        }
    }
}
