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

public class ModE382M105EdwardsCurveTest
    extends PrimeFieldEdwardsCurveTest<ModE382M105> {
    private static final ModE382M105 BASE_X =
        new ModE382M105(new byte[] {
                (byte)0x03, (byte)0x96, (byte)0xf7, (byte)0x70,
                (byte)0x94, (byte)0xcc, (byte)0xc0, (byte)0xeb,
                (byte)0x98, (byte)0x53, (byte)0x10, (byte)0xe8,
                (byte)0xbc, (byte)0x7d, (byte)0x51, (byte)0x93,
                (byte)0x11, (byte)0x84, (byte)0x64, (byte)0x53,
                (byte)0xb8, (byte)0xba, (byte)0x23, (byte)0x29,
                (byte)0x35, (byte)0x64, (byte)0x0b, (byte)0x2b,
                (byte)0x03, (byte)0x40, (byte)0xf8, (byte)0x68,
                (byte)0xae, (byte)0x20, (byte)0x8d, (byte)0x6e,
                (byte)0xe9, (byte)0x5b, (byte)0xf0, (byte)0xe5,
                (byte)0x91, (byte)0x03, (byte)0xb2, (byte)0xea,
                (byte)0xd0, (byte)0x8d, (byte)0x6f, (byte)0x19
            });

    private static final ModE382M105 BASE_Y = new ModE382M105(0x11);

    private static final ModE382M105 DBL_X =
        new ModE382M105(new byte[] {
                (byte)0x8e, (byte)0x86, (byte)0x71, (byte)0xbc,
                (byte)0x91, (byte)0xa4, (byte)0x6d, (byte)0xaf,
                (byte)0x3b, (byte)0x85, (byte)0xa0, (byte)0xd8,
                (byte)0xd8, (byte)0xff, (byte)0x4a, (byte)0x92,
                (byte)0x73, (byte)0x54, (byte)0x48, (byte)0x11,
                (byte)0x76, (byte)0x27, (byte)0x4c, (byte)0x6e,
                (byte)0x7b, (byte)0x7d, (byte)0x26, (byte)0x38,
                (byte)0x88, (byte)0x92, (byte)0x59, (byte)0xf9,
                (byte)0x6f, (byte)0x67, (byte)0x2a, (byte)0x7e,
                (byte)0x6d, (byte)0xb0, (byte)0x3a, (byte)0x8a,
                (byte)0x12, (byte)0xbe, (byte)0x47, (byte)0x39,
                (byte)0x64, (byte)0xda, (byte)0xed, (byte)0x20
            });

    private static final ModE382M105 DBL_Y =
        new ModE382M105(new byte[] {
                (byte)0xa4, (byte)0x76, (byte)0x68, (byte)0xc9,
                (byte)0x27, (byte)0x2c, (byte)0x8a, (byte)0x38,
                (byte)0xc9, (byte)0xd7, (byte)0xa8, (byte)0x89,
                (byte)0x00, (byte)0x7d, (byte)0x5d, (byte)0xed,
                (byte)0xe7, (byte)0x7f, (byte)0xd7, (byte)0x9f,
                (byte)0xfd, (byte)0x62, (byte)0xf7, (byte)0xa2,
                (byte)0x6d, (byte)0x81, (byte)0xd6, (byte)0x72,
                (byte)0x62, (byte)0xa3, (byte)0x27, (byte)0x46,
                (byte)0xa3, (byte)0x87, (byte)0x6a, (byte)0x9b,
                (byte)0xe3, (byte)0x30, (byte)0x81, (byte)0x7e,
                (byte)0x91, (byte)0x95, (byte)0x28, (byte)0x3d,
                (byte)0x73, (byte)0x9a, (byte)0x60, (byte)0x06
            });

    private static final ModE382M105 TPL_X =
        new ModE382M105(new byte[] {
                (byte)0xca, (byte)0x8f, (byte)0xd0, (byte)0x0b,
                (byte)0x81, (byte)0x84, (byte)0x21, (byte)0x6a,
                (byte)0xf3, (byte)0x9c, (byte)0x91, (byte)0x44,
                (byte)0x6f, (byte)0x08, (byte)0x80, (byte)0x11,
                (byte)0x00, (byte)0x8c, (byte)0x27, (byte)0x59,
                (byte)0x83, (byte)0x42, (byte)0xc0, (byte)0x91,
                (byte)0x24, (byte)0x61, (byte)0x40, (byte)0x6d,
                (byte)0x89, (byte)0xf3, (byte)0x82, (byte)0x6e,
                (byte)0xef, (byte)0xf1, (byte)0x86, (byte)0x77,
                (byte)0x9b, (byte)0xd6, (byte)0xa8, (byte)0xd3,
                (byte)0x43, (byte)0xa2, (byte)0xaa, (byte)0x94,
                (byte)0xdb, (byte)0xba, (byte)0x97, (byte)0x1e
            });

    private static final ModE382M105 TPL_Y =
        new ModE382M105(new byte[] {
                (byte)0x97, (byte)0x29, (byte)0x91, (byte)0x18,
                (byte)0xbf, (byte)0xb4, (byte)0xd0, (byte)0x12,
                (byte)0x64, (byte)0x1c, (byte)0xd7, (byte)0x82,
                (byte)0x4e, (byte)0x59, (byte)0x9e, (byte)0x24,
                (byte)0x03, (byte)0xfc, (byte)0x09, (byte)0x78,
                (byte)0xe7, (byte)0xf6, (byte)0xb0, (byte)0x16,
                (byte)0xec, (byte)0x42, (byte)0xb0, (byte)0x69,
                (byte)0x53, (byte)0x5d, (byte)0x9f, (byte)0xd6,
                (byte)0x75, (byte)0x57, (byte)0xd7, (byte)0xcb,
                (byte)0x42, (byte)0x53, (byte)0xcb, (byte)0x0e,
                (byte)0x87, (byte)0x48, (byte)0x77, (byte)0x04,
                (byte)0x50, (byte)0x43, (byte)0x81, (byte)0x36
            });

    private static final ModE382M105 QUAD_X =
        new ModE382M105(new byte[] {
                (byte)0x2e, (byte)0x85, (byte)0xf6, (byte)0xbb,
                (byte)0x58, (byte)0xf4, (byte)0xfe, (byte)0x41,
                (byte)0x75, (byte)0x52, (byte)0xa6, (byte)0x09,
                (byte)0xee, (byte)0x3c, (byte)0xd7, (byte)0x10,
                (byte)0x5e, (byte)0xcc, (byte)0xef, (byte)0xcb,
                (byte)0x94, (byte)0x3c, (byte)0xb0, (byte)0x1d,
                (byte)0xda, (byte)0x18, (byte)0x04, (byte)0x21,
                (byte)0x71, (byte)0x80, (byte)0x7c, (byte)0xad,
                (byte)0xb8, (byte)0xe0, (byte)0x8a, (byte)0x75,
                (byte)0x24, (byte)0x21, (byte)0xfe, (byte)0xd6,
                (byte)0xfa, (byte)0x9d, (byte)0x0a, (byte)0x27,
                (byte)0x1d, (byte)0x10, (byte)0x2f, (byte)0x2f
            });

    private static final ModE382M105 QUAD_Y =
        new ModE382M105(new byte[] {
                (byte)0xd6, (byte)0x5c, (byte)0xc9, (byte)0x8d,
                (byte)0xb6, (byte)0xc1, (byte)0xd5, (byte)0x36,
                (byte)0x51, (byte)0x79, (byte)0x0d, (byte)0x62,
                (byte)0xf7, (byte)0x3b, (byte)0x58, (byte)0x77,
                (byte)0xaf, (byte)0xaa, (byte)0x50, (byte)0xdd,
                (byte)0x4a, (byte)0xde, (byte)0x66, (byte)0x42,
                (byte)0x69, (byte)0xca, (byte)0xb5, (byte)0xdd,
                (byte)0x5a, (byte)0xf5, (byte)0xbe, (byte)0x5f,
                (byte)0x11, (byte)0x27, (byte)0xd2, (byte)0x7c,
                (byte)0x65, (byte)0x71, (byte)0xa9, (byte)0xbc,
                (byte)0x56, (byte)0x06, (byte)0x0d, (byte)0xef,
                (byte)0x0b, (byte)0xe1, (byte)0x2f, (byte)0x2b
            });

    public ModE382M105EdwardsCurveTest() {
        super(-67254);
    }

    @Override
    @DataProvider(name = "addPoints")
    protected Object[][] getAddPoints() {
        return new ModE382M105[][] {
            new ModE382M105[] {
                BASE_X,
                BASE_Y,
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE382M105[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y,
                TPL_X,
                TPL_Y
            },
            new ModE382M105[] {
                DBL_X,
                DBL_Y,
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y
            },
            new ModE382M105[] {
                DBL_X,
                DBL_Y,
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE382M105[] {
                BASE_X,
                BASE_Y,
                TPL_X,
                TPL_Y,
                QUAD_X,
                QUAD_Y
            },
            new ModE382M105[] {
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
        return new ModE382M105[][] {
            new ModE382M105[] {
                BASE_X,
                BASE_Y,
                DBL_X,
                DBL_Y
            },
            new ModE382M105[] {
                DBL_X,
                DBL_Y,
                QUAD_X,
                QUAD_Y
            }
        };
    }
}
