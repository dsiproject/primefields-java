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

public class ModE255M19TwistedEdwardsCurveTest
    extends PrimeFieldTwistedEdwardsCurveTest<ModE255M19> {
    public static final ModE255M19 BASE_X = new ModE255M19(0x9);

    public static final ModE255M19 BASE_Y =
        new ModE255M19(new byte[] {
                (byte)0xd9, (byte)0xd3, (byte)0xce, (byte)0x7e,
                (byte)0xa2, (byte)0xc5, (byte)0xe9, (byte)0x29,
                (byte)0xb2, (byte)0x61, (byte)0x7c, (byte)0x6d,
                (byte)0x7e, (byte)0x4d, (byte)0x3d, (byte)0x92,
                (byte)0x4c, (byte)0xd1, (byte)0x48, (byte)0x77,
                (byte)0x2c, (byte)0xdd, (byte)0x1e, (byte)0xe0,
                (byte)0xb4, (byte)0x86, (byte)0xa0, (byte)0xb8,
                (byte)0xa1, (byte)0x19, (byte)0xae, (byte)0x20
            });

    private static final ModE255M19 DBL_X =
        new ModE255M19(new byte[] {
                (byte)0xfb, (byte)0x4e, (byte)0x68, (byte)0xdd,
                (byte)0x9c, (byte)0x46, (byte)0xae, (byte)0x5c,
                (byte)0x5c, (byte)0x0b, (byte)0x35, (byte)0x1e,
                (byte)0xed, (byte)0x5c, (byte)0x3f, (byte)0x8f,
                (byte)0x14, (byte)0x71, (byte)0x15, (byte)0x7d,
                (byte)0x68, (byte)0x0c, (byte)0x75, (byte)0xd9,
                (byte)0xb7, (byte)0xf1, (byte)0x73, (byte)0x18,
                (byte)0xd5, (byte)0x42, (byte)0xd3, (byte)0x20
            });

    private static final ModE255M19 DBL_Y =
        new ModE255M19(new byte[] {
                (byte)0x63, (byte)0xf5, (byte)0x2d, (byte)0xc7,
                (byte)0x65, (byte)0x04, (byte)0xd7, (byte)0xbc,
                (byte)0x1e, (byte)0x39, (byte)0x8d, (byte)0x8d,
                (byte)0xb2, (byte)0x9e, (byte)0x65, (byte)0x77,
                (byte)0xf3, (byte)0xa2, (byte)0x2b, (byte)0x5d,
                (byte)0x94, (byte)0x00, (byte)0x0a, (byte)0x05,
                (byte)0xae, (byte)0xe8, (byte)0x00, (byte)0x17,
                (byte)0x01, (byte)0x7e, (byte)0xb5, (byte)0x13
            });

    private static final ModE255M19 TPL_X =
        new ModE255M19(new byte[] {
                (byte)0x12, (byte)0x3c, (byte)0x71, (byte)0xfb,
                (byte)0xaf, (byte)0x03, (byte)0x0a, (byte)0xc0,
                (byte)0x59, (byte)0x08, (byte)0x1c, (byte)0x62,
                (byte)0x67, (byte)0x4e, (byte)0x82, (byte)0xf8,
                (byte)0x64, (byte)0xba, (byte)0x1b, (byte)0xc2,
                (byte)0x91, (byte)0x4d, (byte)0x53, (byte)0x45,
                (byte)0xe6, (byte)0xab, (byte)0x57, (byte)0x6d,
                (byte)0x1a, (byte)0xbc, (byte)0x12, (byte)0x1c
            });

    private static final ModE255M19 TPL_Y =
        new ModE255M19(new byte[] {
                (byte)0x2b, (byte)0x22, (byte)0x41, (byte)0x9c,
                (byte)0x01, (byte)0x65, (byte)0xc6, (byte)0x75,
                (byte)0xcf, (byte)0xf7, (byte)0x1e, (byte)0xf7,
                (byte)0x70, (byte)0xf5, (byte)0x6a, (byte)0x53,
                (byte)0x8c, (byte)0x33, (byte)0x2c, (byte)0x53,
                (byte)0x46, (byte)0xa4, (byte)0xee, (byte)0xac,
                (byte)0xae, (byte)0x7e, (byte)0x38, (byte)0xbe,
                (byte)0x5c, (byte)0x85, (byte)0x86, (byte)0x29
            });

    private static final ModE255M19 QUAD_X =
        new ModE255M19(new byte[] {
                (byte)0xef, (byte)0x13, (byte)0x00, (byte)0x55,
                (byte)0xe4, (byte)0x85, (byte)0xee, (byte)0x0f,
                (byte)0x23, (byte)0x2a, (byte)0x5d, (byte)0xcd,
                (byte)0xdf, (byte)0x05, (byte)0x18, (byte)0xfe,
                (byte)0x5f, (byte)0x31, (byte)0x5b, (byte)0xa1,
                (byte)0x74, (byte)0xd0, (byte)0xd1, (byte)0xe7,
                (byte)0x7d, (byte)0x9d, (byte)0x68, (byte)0xe0,
                (byte)0xb7, (byte)0x98, (byte)0xce, (byte)0x79
            });

    private static final ModE255M19 QUAD_Y =
        new ModE255M19(new byte[] {
                (byte)0x30, (byte)0x1c, (byte)0xde, (byte)0x46,
                (byte)0x9f, (byte)0x53, (byte)0x65, (byte)0xa5,
                (byte)0x77, (byte)0x10, (byte)0x36, (byte)0xda,
                (byte)0x13, (byte)0xfb, (byte)0xc0, (byte)0x73,
                (byte)0x5d, (byte)0x27, (byte)0x7d, (byte)0x42,
                (byte)0x73, (byte)0x68, (byte)0xe2, (byte)0x8f,
                (byte)0x5c, (byte)0xc7, (byte)0xbd, (byte)0x4e,
                (byte)0xbf, (byte)0xf5, (byte)0x5a, (byte)0x07
            });

    public ModE255M19TwistedEdwardsCurveTest() {
        super(486664, 486660);
    }

    @Override
    @DataProvider(name = "sanityPoints")
    protected Object[][] getSanityPoints() {
        return new ModE255M19[][] {
            new ModE255M19[] {
                BASE_X,
                BASE_Y,
            },
            new ModE255M19[] {
                DBL_X,
                DBL_Y,
            },
            new ModE255M19[] {
                TPL_X,
                TPL_Y,
            },
            new ModE255M19[] {
                QUAD_X,
                QUAD_Y,
            }
        };
    }

    @Override
    @DataProvider(name = "addPoints")
    protected Object[][] getAddPoints() {
        return new ModE255M19[][] {
            new ModE255M19[] {
                BASE_X,
                BASE_Y,
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE255M19[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y,
                TPL_X,
                TPL_Y
            },
            new ModE255M19[] {
                DBL_X,
                DBL_Y,
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y
            },
            new ModE255M19[] {
                DBL_X,
                DBL_Y,
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE255M19[] {
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE255M19[] {
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
        return new ModE255M19[][] {
            new ModE255M19[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE255M19[] {
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            }
        };
    }
}
