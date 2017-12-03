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

public class ModE222M117StressTest extends PrimeFieldStressTest<ModE222M117> {
    private static final int[] primes = new int[] {
        2, 3, 71, 103, 809,
        3907, 7919, 50821, 93719, 199933
    };

    private static final ModE222M117[] values;

    static {
        final ModE222M117[][] primePowers = new ModE222M117[4][10];

        values = new ModE222M117[10000];

        for(int i = 0; i < primePowers.length; i++) {
            for(int j = 0; j < primePowers[i].length; j++) {
                final int power = 1 << i;
                final ModE222M117 val = new ModE222M117(primes[j]);

                for(int k = 1; k < power; k++) {
                    val.square();
                }

                primePowers[i][j] = val;
            }
        }

        for(int i = 0; i < primePowers[0].length; i++) {
            for(int j = 0; j < primePowers[1].length; j++) {
                for(int k = 0; k < primePowers[2].length; k++) {
                    for(int l = 0; l < primePowers[3].length; l++) {
                        final ModE222M117 val = primePowers[0][i].clone();
                        final int idx = (1000 * i) + (100 * j) + (10 * k) + l;

                        val.add(primePowers[1][j]);
                        val.add(primePowers[2][k]);
                        val.add(primePowers[3][l]);

                        values[idx] = val;
                    }
                }
            }
        }
    }

    public ModE222M117StressTest() {
        super(values);
    }
}
