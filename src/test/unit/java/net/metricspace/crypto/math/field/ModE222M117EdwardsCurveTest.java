/* Copyright (c) 2018, Eric McCorkle.  All rights reserved.
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

import org.testng.annotations.DataProvider;

public class ModE222M117EdwardsCurveTest
    extends PrimeFieldEdwardsCurveTest<ModE222M117> {
    private static final ModE222M117 BASE_X =
        new ModE222M117(new byte[] {
                (byte)0x51, (byte)0x4e, (byte)0xb5, (byte)0x3e,
                (byte)0xbc, (byte)0xb2, (byte)0x6e, (byte)0x73,
                (byte)0xb3, (byte)0xda, (byte)0x3a, (byte)0xc2,
                (byte)0x07, (byte)0x6d, (byte)0x31, (byte)0x03,
                (byte)0xc3, (byte)0x68, (byte)0x97, (byte)0x5c,
                (byte)0xe5, (byte)0x89, (byte)0xa3, (byte)0x56,
                (byte)0xb1, (byte)0x2b, (byte)0xb1, (byte)0x19,
            });

    private static final ModE222M117 BASE_Y = new ModE222M117(0x1c);

    private static final ModE222M117 DBL_X =
        new ModE222M117(new byte[] {
                (byte)0xe5, (byte)0x78, (byte)0xbc, (byte)0xc3,
                (byte)0xb5, (byte)0x88, (byte)0x97, (byte)0x23,
                (byte)0x27, (byte)0x0f, (byte)0x16, (byte)0xd4,
                (byte)0x09, (byte)0x76, (byte)0x59, (byte)0xe7,
                (byte)0xda, (byte)0xcf, (byte)0x2f, (byte)0x62,
                (byte)0xfc, (byte)0xe1, (byte)0x93, (byte)0xd6,
                (byte)0xb8, (byte)0x71, (byte)0x63, (byte)0x08
            });

    private static final ModE222M117 DBL_Y =
        new ModE222M117(new byte[] {
                (byte)0x1d, (byte)0x1b, (byte)0xde, (byte)0x72,
                (byte)0xe6, (byte)0xa4, (byte)0xfa, (byte)0xd0,
                (byte)0x55, (byte)0x00, (byte)0x1b, (byte)0x06,
                (byte)0xe8, (byte)0xdd, (byte)0x73, (byte)0xe2,
                (byte)0x33, (byte)0xe2, (byte)0x52, (byte)0xcd,
                (byte)0x3b, (byte)0xe8, (byte)0xfa, (byte)0x1e,
                (byte)0x88, (byte)0x4e, (byte)0x7f, (byte)0x08
            });

    private static final ModE222M117 TPL_X =
        new ModE222M117(new byte[] {
                (byte)0x76, (byte)0xe4, (byte)0x75, (byte)0x89,
                (byte)0x60, (byte)0xf8, (byte)0xfe, (byte)0x8d,
                (byte)0x0b, (byte)0x02, (byte)0x1f, (byte)0x57,
                (byte)0x6d, (byte)0x0a, (byte)0x95, (byte)0xb6,
                (byte)0x2c, (byte)0xf4, (byte)0x65, (byte)0xca,
                (byte)0x01, (byte)0xf8, (byte)0x76, (byte)0xc9,
                (byte)0x50, (byte)0x01, (byte)0xd5, (byte)0x0f
            });

    private static final ModE222M117 TPL_Y =
        new ModE222M117(new byte[] {
                (byte)0x30, (byte)0x28, (byte)0x37, (byte)0xf2,
                (byte)0xb2, (byte)0x97, (byte)0xa7, (byte)0xda,
                (byte)0x1e, (byte)0x06, (byte)0x16, (byte)0x25,
                (byte)0x3f, (byte)0x97, (byte)0x22, (byte)0xbe,
                (byte)0x89, (byte)0xbc, (byte)0x55, (byte)0x8f,
                (byte)0x67, (byte)0x28, (byte)0x7f, (byte)0xbe,
                (byte)0x5d, (byte)0xa9, (byte)0xc1, (byte)0x35
            });

    private static final ModE222M117 QUAD_X =
        new ModE222M117(new byte[] {
                (byte)0xe7, (byte)0xa9, (byte)0x8e, (byte)0x73,
                (byte)0x57, (byte)0x7a, (byte)0xde, (byte)0xad,
                (byte)0xbb, (byte)0x72, (byte)0x63, (byte)0x52,
                (byte)0x37, (byte)0x2f, (byte)0xed, (byte)0x74,
                (byte)0x1f, (byte)0x9a, (byte)0x7e, (byte)0x59,
                (byte)0x96, (byte)0xf2, (byte)0xf9, (byte)0xdb,
                (byte)0xfc, (byte)0xd5, (byte)0x90, (byte)0x2c
            });

    private static final ModE222M117 QUAD_Y =
        new ModE222M117(new byte[] {
                (byte)0x43, (byte)0x4c, (byte)0xa4, (byte)0x4e,
                (byte)0xed, (byte)0x44, (byte)0xe4, (byte)0x8b,
                (byte)0x8f, (byte)0x07, (byte)0xac, (byte)0x96,
                (byte)0x29, (byte)0x73, (byte)0xb9, (byte)0xfa,
                (byte)0x13, (byte)0xf7, (byte)0xf7, (byte)0x3b,
                (byte)0xbc, (byte)0x6d, (byte)0x6c, (byte)0x29,
                (byte)0xfc, (byte)0x1c, (byte)0xc8, (byte)0x0d
            });

    public ModE222M117EdwardsCurveTest() {
        super(160102);
    }

    @Override
    @DataProvider(name = "addPoints")
    protected Object[][] getAddPoints() {
        return new ModE222M117[][] {
            new ModE222M117[] {
                BASE_X,
                BASE_Y,
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE222M117[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y,
                TPL_X,
                TPL_Y
            },
            new ModE222M117[] {
                DBL_X,
                DBL_Y,
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y
            },
            new ModE222M117[] {
                DBL_X,
                DBL_Y,
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE222M117[] {
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE222M117[] {
                TPL_X,
                TPL_Y,
                BASE_X,
                BASE_Y,
                QUAD_X,
                QUAD_Y
            }
        };
    }

    @Override
    @DataProvider(name = "doublePoints")
    protected Object[][] getDoublePoints() {
        return new ModE222M117[][] {
            new ModE222M117[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE222M117[] {
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            }
        };
    }
}
