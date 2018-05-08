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

public class ModE221M3TwistedEdwardsCurveTest
    extends PrimeFieldTwistedEdwardsCurveTest<ModE221M3> {
    private static final ModE221M3 BASE_X = new ModE221M3(0x4);

    private static final ModE221M3 BASE_Y =
        new ModE221M3(new byte[] {
                (byte)0x6d, (byte)0x05, (byte)0xc5, (byte)0x08,
                (byte)0x6c, (byte)0xdc, (byte)0x07, (byte)0x07,
                (byte)0xf1, (byte)0xbf, (byte)0x1d, (byte)0xe6,
                (byte)0x28, (byte)0xc2, (byte)0x37, (byte)0xca,
                (byte)0x4e, (byte)0xf1, (byte)0xce, (byte)0xd1,
                (byte)0x71, (byte)0x95, (byte)0x93, (byte)0xa4,
                (byte)0xd2, (byte)0xcd, (byte)0x7a, (byte)0x0f
            });

    private static final ModE221M3 DBL_X =
        new ModE221M3(new byte[] {
                (byte)0x08, (byte)0x0f, (byte)0x59, (byte)0x0a,
                (byte)0x22, (byte)0x75, (byte)0x1f, (byte)0x89,
                (byte)0x86, (byte)0xe2, (byte)0x33, (byte)0xd0,
                (byte)0x5b, (byte)0x6c, (byte)0x4f, (byte)0x59,
                (byte)0xde, (byte)0x82, (byte)0x6d, (byte)0x33,
                (byte)0x34, (byte)0xfe, (byte)0xbc, (byte)0x09,
                (byte)0x15, (byte)0xc2, (byte)0xcd, (byte)0x10
            });

    private static final ModE221M3 DBL_Y =
        new ModE221M3(new byte[] {
                (byte)0xe0, (byte)0x32, (byte)0x13, (byte)0x28,
                (byte)0xed, (byte)0xea, (byte)0x69, (byte)0xce,
                (byte)0x31, (byte)0x26, (byte)0x32, (byte)0x36,
                (byte)0x2d, (byte)0x7a, (byte)0xc0, (byte)0x60,
                (byte)0x67, (byte)0x5f, (byte)0xb4, (byte)0x1a,
                (byte)0xae, (byte)0x6c, (byte)0x3c, (byte)0xe9,
                (byte)0x4c, (byte)0x01, (byte)0xd7, (byte)0x07
            });

    private static final ModE221M3 TPL_X =
        new ModE221M3(new byte[] {
                (byte)0x5d, (byte)0x40, (byte)0x52, (byte)0x45,
                (byte)0x88, (byte)0xc2, (byte)0xcf, (byte)0xef,
                (byte)0x2c, (byte)0x3b, (byte)0x75, (byte)0xf1,
                (byte)0x89, (byte)0x4d, (byte)0xd2, (byte)0xa7,
                (byte)0x19, (byte)0x1f, (byte)0x79, (byte)0xd9,
                (byte)0xd2, (byte)0xdb, (byte)0x0b, (byte)0x7d,
                (byte)0xbe, (byte)0xcb, (byte)0x2b, (byte)0x19
            });

    private static final ModE221M3 TPL_Y =
        new ModE221M3(new byte[] {
                (byte)0x36, (byte)0xe0, (byte)0x32, (byte)0xdd,
                (byte)0x76, (byte)0x73, (byte)0xd8, (byte)0x04,
                (byte)0x7a, (byte)0x42, (byte)0x31, (byte)0x5c,
                (byte)0x49, (byte)0x1d, (byte)0x1c, (byte)0xd9,
                (byte)0x88, (byte)0x4b, (byte)0x3c, (byte)0xf3,
                (byte)0xf5, (byte)0xcb, (byte)0x33, (byte)0xbc,
                (byte)0xcf, (byte)0x8c, (byte)0x83, (byte)0x17
            });

    private static final ModE221M3 QUAD_X =
        new ModE221M3(new byte[] {
                (byte)0xdc, (byte)0x10, (byte)0xad, (byte)0xe6,
                (byte)0xee, (byte)0x15, (byte)0x18, (byte)0x50,
                (byte)0x4d, (byte)0xc0, (byte)0x69, (byte)0x9b,
                (byte)0x87, (byte)0x8d, (byte)0x65, (byte)0x6c,
                (byte)0xd3, (byte)0xbf, (byte)0x3a, (byte)0xb8,
                (byte)0x09, (byte)0x5f, (byte)0x1d, (byte)0x8f,
                (byte)0xa4, (byte)0xd6, (byte)0xb5, (byte)0x19
            });

    private static final ModE221M3 QUAD_Y =
        new ModE221M3(new byte[] {
                (byte)0x4e, (byte)0xcb, (byte)0x43, (byte)0xa7,
                (byte)0x1a, (byte)0x5c, (byte)0x03, (byte)0xa3,
                (byte)0xc1, (byte)0x2f, (byte)0xb7, (byte)0x34,
                (byte)0x0a, (byte)0x1d, (byte)0x22, (byte)0x52,
                (byte)0x47, (byte)0x13, (byte)0x60, (byte)0xdd,
                (byte)0x50, (byte)0x67, (byte)0xf4, (byte)0x3d,
                (byte)0x4a, (byte)0xce, (byte)0x0e, (byte)0x02
            });

    public ModE221M3TwistedEdwardsCurveTest() {
        super(117052, 117048);
    }

    @Override
    @DataProvider(name = "sanityPoints")
    protected Object[][] getSanityPoints() {
        return new ModE221M3[][] {
            new ModE221M3[] {
                BASE_X,
                BASE_Y,
            },
            new ModE221M3[] {
                DBL_X,
                DBL_Y,
            },
            new ModE221M3[] {
                TPL_X,
                TPL_Y,
            },
            new ModE221M3[] {
                QUAD_X,
                QUAD_Y,
            }
        };
    }

    @Override
    @DataProvider(name = "addPoints")
    protected Object[][] getAddPoints() {
        return new ModE221M3[][] {
            new ModE221M3[] {
                BASE_X,
                BASE_Y,
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE221M3[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y,
                TPL_X,
                TPL_Y
            },
            new ModE221M3[] {
                DBL_X,
                DBL_Y,
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y
            },
            new ModE221M3[] {
                DBL_X,
                DBL_Y,
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE221M3[] {
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE221M3[] {
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
        return new ModE221M3[][] {
            new ModE221M3[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE221M3[] {
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            }
        };
    }
}
