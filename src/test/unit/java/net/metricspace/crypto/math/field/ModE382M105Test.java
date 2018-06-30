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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ModE382M105Test extends PrimeFieldUnitTest<ModE382M105> {
    private static final long[] TWO_DATA = new long[] { 2, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x00ffffffffffff95L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] THREE_DATA = new long[] { 3, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x00ffffffffffff94L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] FOUR_DATA = new long[] { 4, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x00ffffffffffff93L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] FIVE_DATA = new long[] { 5, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x00ffffffffffff92L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] SIX_DATA = new long[] { 6, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x00ffffffffffff91L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] SEVEN_DATA = new long[] { 7, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x00ffffffffffff90L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] EIGHT_DATA = new long[] { 8, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x00ffffffffffff8fL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] NINE_DATA = new long[] { 9, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x00ffffffffffff8eL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] SIXTEEN_DATA =
        new long[] { 16, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x00ffffffffffff87L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };
    private static final long[] TWENTY_FIVE_DATA =
        new long[] { 25, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x00ffffffffffff7eL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00003fffffffffffL };

    private static ModE382M105 two() {
        return ModE382M105.create(TWO_DATA);
    }

    private static ModE382M105 mtwo() {
        return ModE382M105.create(M_TWO_DATA);
    }

    private static ModE382M105 three() {
        return ModE382M105.create(THREE_DATA);
    }

    private static ModE382M105 mthree() {
        return ModE382M105.create(M_THREE_DATA);
    }

    private static ModE382M105 four() {
        return ModE382M105.create(FOUR_DATA);
    }

    private static ModE382M105 mfour() {
        return ModE382M105.create(M_FOUR_DATA);
    }

    private static ModE382M105 five() {
        return ModE382M105.create(FIVE_DATA);
    }

    private static ModE382M105 mfive() {
        return ModE382M105.create(M_FIVE_DATA);
    }

    private static ModE382M105 six() {
        return ModE382M105.create(SIX_DATA);
    }

    private static ModE382M105 msix() {
        return ModE382M105.create(M_SIX_DATA);
    }

    private static ModE382M105 seven() {
        return ModE382M105.create(SEVEN_DATA);
    }

    private static ModE382M105 mseven() {
        return ModE382M105.create(M_SEVEN_DATA);
    }

    private static ModE382M105 eight() {
        return ModE382M105.create(EIGHT_DATA);
    }

    private static ModE382M105 meight() {
        return ModE382M105.create(M_EIGHT_DATA);
    }

    private static ModE382M105 nine() {
        return ModE382M105.create(NINE_DATA);
    }

    private static ModE382M105 mnine() {
        return ModE382M105.create(M_NINE_DATA);
    }

    private static ModE382M105 sixteen() {
        return ModE382M105.create(SIXTEEN_DATA);
    }

    private static ModE382M105 msixteen() {
        return ModE382M105.create(M_SIXTEEN_DATA);
    }

    private static ModE382M105 twentyFive() {
        return ModE382M105.create(TWENTY_FIVE_DATA);
    }

    private static ModE382M105 mtwentyFive() {
        return ModE382M105.create(M_TWENTY_FIVE_DATA);
    }

    @Override
    protected ModE382M105 unpack(final byte[] data) {
        return new ModE382M105(data);
    }

    @Override
    protected ModE382M105 unpackStream(final InputStream data)
        throws IOException {
        return new ModE382M105(data);
    }

    private static final Object[][] TEST_CONSTANTS_TEST_CASES = new Object[][] {
        new Object[] { new ModE382M105(0), ModE382M105.zero() },
        new Object[] { new ModE382M105(1), ModE382M105.one() },
        new Object[] { new ModE382M105(-1), ModE382M105.mone() },
        new Object[] { new ModE382M105(2), two() },
        new Object[] { new ModE382M105(-2), mtwo() },
        new Object[] { new ModE382M105(3), three() },
        new Object[] { new ModE382M105(-3), mthree() },
        new Object[] { new ModE382M105(4), four() },
        new Object[] { new ModE382M105(-4), mfour() },
        new Object[] { new ModE382M105(5), five() },
        new Object[] { new ModE382M105(-5), mfive() },
        new Object[] { new ModE382M105(6), six() },
        new Object[] { new ModE382M105(-6), msix() },
        new Object[] { new ModE382M105(7), seven() },
        new Object[] { new ModE382M105(-7), mseven() },
        new Object[] { new ModE382M105(8), eight() },
        new Object[] { new ModE382M105(-8), meight() },
        new Object[] { new ModE382M105(9), nine() },
        new Object[] { new ModE382M105(-9), mnine() },
        new Object[] { new ModE382M105(16), sixteen() },
        new Object[] { new ModE382M105(-16), msixteen() },
        new Object[] { new ModE382M105(25), twentyFive() },
        new Object[] { new ModE382M105(-25), mtwentyFive() }
    };

    @Override
    @DataProvider(name = "testConstants")
    public Object[][] testConstantsProvider() {
        return TEST_CONSTANTS_TEST_CASES;
    }

    private static final Object[][] SET_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00003fffffffffffL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00002aaaaaaaaaaaL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0000155555555555L })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00002aaaaaaaaaaaL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00003fffffffffffL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0000155555555555L })
        }
    };

    @Override
    protected ModE382M105 createEmpty() {
        return new ModE382M105(0);
    }

    @Override
    @DataProvider(name = "setEmpty")
    public Object[][] setEmptyProvider() {
        return SET_TEST_CASES;
    }

    private static final Object[][] UNPACK_PACK_TEST_CASES = new Object [][] {
        new Object[] {
            new byte[] { (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00 }
        },
        new Object[] {
            new byte[] { (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x1f,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x3f }
        },
        new Object[] {
            new byte[] { (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0x2a }
        },
        new Object[] {
            new byte[] { (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x15 }
        },
        new Object[] {
            new byte[] { (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0x2a }
        },
        new Object[] {
            new byte[] { (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00 }
        },
        new Object[] {
            new byte[] { (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0x3f }
        },
        new Object[] {
            new byte[] { (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x15 }
        }
    };

    @Override
    @DataProvider(name = "unpackPack")
    public Object[][] unpackPackProvider() {
        return UNPACK_PACK_TEST_CASES;
    }

    private static final Object[][] PACK_UNPACK_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00003fffffffffffL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00002aaaaaaaaaaaL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0000155555555555L })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00002aaaaaaaaaaaL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00003fffffffffffL })
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0000155555555555L })
        }
    };

    @Override
    @DataProvider(name = "packUnpack")
    public Object[][] packUnpackProvider() {
        return PACK_UNPACK_TEST_CASES;
    }

    @DataProvider(name = "mask")
    public Object[][] maskProvider() {
        return PACK_UNPACK_TEST_CASES;
    }

    private static final Object[][] OR_TEST_CASES =
        new Object[PACK_UNPACK_TEST_CASES.length *
                   PACK_UNPACK_TEST_CASES.length][2];

    static {
        final int len = PACK_UNPACK_TEST_CASES.length;

        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                OR_TEST_CASES[(i * len) + j][0] = PACK_UNPACK_TEST_CASES[i][0];
                OR_TEST_CASES[(i * len) + j][1] = PACK_UNPACK_TEST_CASES[j][0];
            }
        }
    };

    @DataProvider(name = "or")
    public Object[][] orProvider() {
        return OR_TEST_CASES;
    }

    private static final ModE382M105[][] startTier = new ModE382M105[][] {
        new ModE382M105[] { ModE382M105.zero() },
        new ModE382M105[] { ModE382M105.one() },
        new ModE382M105[] { ModE382M105.mone() },
        new ModE382M105[] { two() },
        new ModE382M105[] { mtwo() },
        new ModE382M105[] { four() },
        new ModE382M105[] { mfour() }
    };

    @Test
    public void addTest() {
        final ModE382M105[][] tierOne = addTier(startTier);
        final ModE382M105[][] tierTwo = addTier(tierOne);
        //final ModE382M105[][] tierThree = addTier(tierTwo);
    }

    @Test
    public void subTest() {
        final ModE382M105[][] tierOne = subTier(startTier);
        final ModE382M105[][] tierTwo = subTier(tierOne);
        //final ModE382M105[][] tierThree = subTier(tierTwo);
    }

    @Test
    public void mulTest() {
        final ModE382M105[][] tierOne = mulTier(startTier);
        final ModE382M105[][] tierTwo = mulTier(tierOne);
        //final ModE382M105[][] tierThree = mulTier(tierTwo);
    }

    @Test
    public void divTest() {
        final ModE382M105[][] tierOne = divTier(startTier);
        final ModE382M105[][] tierTwo = divTier(tierOne);
        //final ModE382M105[][] tierThree = divTier(tierTwo);
    }

    private static final Object[][] SQUARE_TEST_CASES = new Object[][] {
        new Object[] { ModE382M105.zero(), ModE382M105.zero() },
        new Object[] { ModE382M105.one(), ModE382M105.one() },
        new Object[] { two(), four() },
        new Object[] { three(), nine() },
        new Object[] { four(), sixteen() },
        new Object[] { five(), twentyFive() },
        new Object[] { ModE382M105.mone(), ModE382M105.one() },
        new Object[] { mtwo(), four() },
        new Object[] { mthree(), nine() },
        new Object[] { mfour(), sixteen() },
        new Object[] { mfive(), twentyFive() },
    };

    @Override
    @DataProvider(name = "square")
    public Object[][] squareProvider() {
        return SQUARE_TEST_CASES;
    }

    private static final Object[][] LEGENDRE_TEST_CASES = new Object[][] {
        new Object[] { ModE382M105.zero(), new Integer(0) },
        new Object[] { ModE382M105.one(), new Integer(1) },
        new Object[] { two(), new Integer(1) },
        new Object[] { three(), new Integer(-1) },
        new Object[] { four(), new Integer(1) },
        new Object[] { five(), new Integer(1) },
        new Object[] { six(), new Integer(-1) },
        new Object[] { seven(), new Integer(-1) },
        new Object[] { eight(), new Integer(1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE382M105.mone(), new Integer(-1) },
        new Object[] { mtwo(), new Integer(-1) },
        new Object[] { mthree(), new Integer(1) },
        new Object[] { mfour(), new Integer(-1) },
        new Object[] { mfive(), new Integer(-1) },
        new Object[] { msix(), new Integer(1) },
        new Object[] { mseven(), new Integer(1) },
        new Object[] { meight(), new Integer(-1) },
        new Object[] { mnine(), new Integer(-1) },
        new Object[] { msixteen(), new Integer(-1) },
        new Object[] { mtwentyFive(), new Integer(-1) },
    };

    @Override
    @DataProvider(name = "legendre", parallel = true)
    public Object[][] legendreProvider() {
        return LEGENDRE_TEST_CASES;
    }

    private static final Object[][] SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE382M105.zero(), ModE382M105.zero() },
        new Object[] { ModE382M105.one(), ModE382M105.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), five() },
    };

    @Override
    @DataProvider(name = "sqrt", parallel = true)
    public Object[][] sqrtProvider() {
        return SQRT_TEST_CASES;
    }

    private static final Object[][] INV_SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE382M105.zero(), ModE382M105.zero() },
        new Object[] { ModE382M105.one(), ModE382M105.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), five() },
    };

    static {
        for(int i = 0; i < INV_SQRT_TEST_CASES.length; i++) {
            ((ModE382M105)INV_SQRT_TEST_CASES[i][1]).inv();
        }
    };

    @Override
    @DataProvider(name = "invSqrt", parallel = true)
    public Object[][] invSqrtProvider() {
        return INV_SQRT_TEST_CASES;
    }

    private static final Object[][] ABS_TEST_CASES = new Object[][] {
        new Object[] { ModE382M105.zero(), ModE382M105.zero() },
        new Object[] { ModE382M105.one(), ModE382M105.one() },
        new Object[] { ModE382M105.mone(), ModE382M105.one() },
        new Object[] { two(), two() },
        new Object[] { mtwo(), two() },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffcbL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
            new ModE382M105(
                new long[] { 0x00ffffffffffffcbL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffccL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
            new ModE382M105(
                new long[] { 0x00ffffffffffffcbL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffcdL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
            new ModE382M105(
                new long[] { 0x00ffffffffffffcaL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
        },
    };

    @DataProvider(name = "abs")
    public Object[][] absProvider() {
        return ABS_TEST_CASES;
    }

    private static final Object[][] SIGNUM_TEST_CASES = new Object[][] {
        new Object[] { ModE382M105.zero(), 1 },
        new Object[] { ModE382M105.one(), 1 },
        new Object[] { ModE382M105.mone(), -1 },
        new Object[] { two(), 1 },
        new Object[] { mtwo(), -1 },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffcbL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
            1
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffccL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
            -1
        },
        new Object[] {
            new ModE382M105(
                new long[] { 0x00ffffffffffffcdL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x00001fffffffffffL }),
            -1
        },
    };

    @DataProvider(name = "signum")
    public Object[][] signumProvider() {
        return SIGNUM_TEST_CASES;
    }
}
