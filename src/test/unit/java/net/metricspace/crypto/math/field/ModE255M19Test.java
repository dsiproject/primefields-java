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

public class ModE255M19Test extends PrimeField1Mod4UnitTest<ModE255M19> {
    private static final long[] TWO_DATA = new long[] { 2, 0, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x00ffffffffffffebL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] THREE_DATA = new long[] { 3, 0, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x00ffffffffffffeaL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] FOUR_DATA = new long[] { 4, 0, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x00ffffffffffffe9L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] FIVE_DATA = new long[] { 5, 0, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x00ffffffffffffe8L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] SIX_DATA = new long[] { 6, 0, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x00ffffffffffffe7L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] SEVEN_DATA = new long[] { 7, 0, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x00ffffffffffffe6L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] EIGHT_DATA = new long[] { 8, 0, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x00ffffffffffffe5L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] NINE_DATA = new long[] { 9, 0, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x00ffffffffffffe4L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] SIXTEEN_DATA = new long[] { 16, 0, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x00ffffffffffffddL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };
    private static final long[] TWENTY_FIVE_DATA =
        new long[] { 25, 0, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x00ffffffffffffd4L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x000000007fffffffL };

    private static ModE255M19 two() {
        return ModE255M19.create(TWO_DATA);
    }

    private static ModE255M19 mtwo() {
        return ModE255M19.create(M_TWO_DATA);
    }

    private static ModE255M19 three() {
        return ModE255M19.create(THREE_DATA);
    }

    private static ModE255M19 mthree() {
        return ModE255M19.create(M_THREE_DATA);
    }

    private static ModE255M19 four() {
        return ModE255M19.create(FOUR_DATA);
    }

    private static ModE255M19 mfour() {
        return ModE255M19.create(M_FOUR_DATA);
    }

    private static ModE255M19 five() {
        return ModE255M19.create(FIVE_DATA);
    }

    private static ModE255M19 mfive() {
        return ModE255M19.create(M_FIVE_DATA);
    }

    private static ModE255M19 six() {
        return ModE255M19.create(SIX_DATA);
    }

    private static ModE255M19 msix() {
        return ModE255M19.create(M_SIX_DATA);
    }

    private static ModE255M19 seven() {
        return ModE255M19.create(SEVEN_DATA);
    }

    private static ModE255M19 mseven() {
        return ModE255M19.create(M_SEVEN_DATA);
    }

    private static ModE255M19 eight() {
        return ModE255M19.create(EIGHT_DATA);
    }

    private static ModE255M19 meight() {
        return ModE255M19.create(M_EIGHT_DATA);
    }

    private static ModE255M19 nine() {
        return ModE255M19.create(NINE_DATA);
    }

    private static ModE255M19 mnine() {
        return ModE255M19.create(M_NINE_DATA);
    }

    private static ModE255M19 sixteen() {
        return ModE255M19.create(SIXTEEN_DATA);
    }

    private static ModE255M19 msixteen() {
        return ModE255M19.create(M_SIXTEEN_DATA);
    }

    private static ModE255M19 twentyFive() {
        return ModE255M19.create(TWENTY_FIVE_DATA);
    }

    private static ModE255M19 mtwentyFive() {
        return ModE255M19.create(M_TWENTY_FIVE_DATA);
    }

    @Override
    protected ModE255M19 unpack(final byte[] data) {
        return new ModE255M19(data);
    }

    @Override
    protected ModE255M19 unpackStream(final InputStream data)
        throws IOException {
        return new ModE255M19(data);
    }

    private static final Object[][] TEST_CONSTANTS_TEST_CASES = new Object[][] {
        new Object[] { new ModE255M19(0), ModE255M19.zero() },
        new Object[] { new ModE255M19(1), ModE255M19.one() },
        new Object[] { new ModE255M19(-1), ModE255M19.mone() },
        new Object[] { new ModE255M19(2), two() },
        new Object[] { new ModE255M19(-2), mtwo() },
        new Object[] { new ModE255M19(3), three() },
        new Object[] { new ModE255M19(-3), mthree() },
        new Object[] { new ModE255M19(4), four() },
        new Object[] { new ModE255M19(-4), mfour() },
        new Object[] { new ModE255M19(5), five() },
        new Object[] { new ModE255M19(-5), mfive() },
        new Object[] { new ModE255M19(6), six() },
        new Object[] { new ModE255M19(-6), msix() },
        new Object[] { new ModE255M19(7), seven() },
        new Object[] { new ModE255M19(-7), mseven() },
        new Object[] { new ModE255M19(8), eight() },
        new Object[] { new ModE255M19(-8), meight() },
        new Object[] { new ModE255M19(9), nine() },
        new Object[] { new ModE255M19(-9), mnine() },
        new Object[] { new ModE255M19(16), sixteen() },
        new Object[] { new ModE255M19(-16), msixteen() },
        new Object[] { new ModE255M19(25), twentyFive() },
        new Object[] { new ModE255M19(-25), mtwentyFive() }
    };

    @Override
    @DataProvider(name = "testConstants")
    public Object[][] testConstantsProvider() {
        return TEST_CONSTANTS_TEST_CASES;
    }

    private static final Object[][] SET_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE255M19(
                new long[] { 0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x000000007fffffffL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x000000002aaaaaaaL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000055555555L })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x000000002aaaaaaaL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x000000007fffffffL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0000000055555555L })
        }
    };

    @Override
    protected ModE255M19 createEmpty() {
        return new ModE255M19(0);
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
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00 }
        },
        new Object[] {
            new byte[] { (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x1f,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x7f }
        },
        new Object[] {
            new byte[] { (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
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
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55 }
        },
        new Object[] {
            new byte[] { (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
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
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0x7f }
        },
        new Object[] {
            new byte[] { (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55 }
        }
    };

    @Override
    @DataProvider(name = "unpackPack")
    public Object[][] unpackPackProvider() {
        return UNPACK_PACK_TEST_CASES;
    }

    private static final Object[][] PACK_UNPACK_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE255M19(
                new long[] { 0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x000000007fffffffL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x000000002aaaaaaaL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000055555555L })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x000000002aaaaaaaL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x000000007fffffffL })
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0000000055555555L })
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

    private static final ModE255M19[][] startTier = new ModE255M19[][] {
        new ModE255M19[] { ModE255M19.zero() },
        new ModE255M19[] { ModE255M19.one() },
        new ModE255M19[] { ModE255M19.mone() },
        new ModE255M19[] { two() },
        new ModE255M19[] { mtwo() },
        new ModE255M19[] { four() },
        new ModE255M19[] { mfour() }
    };

    @Test
    public void addTest() {
        final ModE255M19[][] tierOne = addTier(startTier);
        final ModE255M19[][] tierTwo = addTier(tierOne);
        //final ModE255M19[][] tierThree = addTier(tierTwo);
    }

    @Test
    public void subTest() {
        final ModE255M19[][] tierOne = subTier(startTier);
        final ModE255M19[][] tierTwo = subTier(tierOne);
        //final ModE255M19[][] tierThree = subTier(tierTwo);
    }

    @Test
    public void mulTest() {
        final ModE255M19[][] tierOne = mulTier(startTier);
        final ModE255M19[][] tierTwo = mulTier(tierOne);
        //final ModE255M19[][] tierThree = mulTier(tierTwo);
    }

    @Test
    public void divTest() {
        final ModE255M19[][] tierOne = divTier(startTier);
        final ModE255M19[][] tierTwo = divTier(tierOne);
        //final ModE255M19[][] tierThree = divTier(tierTwo);
    }

    private static final Object[][] SQUARE_TEST_CASES = new Object[][] {
        new Object[] { ModE255M19.zero(), ModE255M19.zero() },
        new Object[] { ModE255M19.one(), ModE255M19.one() },
        new Object[] { two(), four() },
        new Object[] { three(), nine() },
        new Object[] { four(), sixteen() },
        new Object[] { five(), twentyFive() },
        new Object[] { ModE255M19.mone(), ModE255M19.one() },
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
        new Object[] { ModE255M19.zero(), new Integer(0) },
        new Object[] { ModE255M19.one(), new Integer(1) },
        new Object[] { two(), new Integer(-1) },
        new Object[] { three(), new Integer(1) },
        new Object[] { four(), new Integer(1) },
        new Object[] { five(), new Integer(1) },
        new Object[] { six(), new Integer(-1) },
        new Object[] { seven(), new Integer(-1) },
        new Object[] { eight(), new Integer(-1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE255M19.mone(), new Integer(1) },
        new Object[] { mtwo(), new Integer(-1) },
        new Object[] { mthree(), new Integer(1) },
        new Object[] { mfour(), new Integer(1) },
        new Object[] { mfive(), new Integer(1) },
        new Object[] { msix(), new Integer(-1) },
        new Object[] { mseven(), new Integer(-1) },
        new Object[] { meight(), new Integer(-1) },
        new Object[] { mnine(), new Integer(1) },
        new Object[] { msixteen(), new Integer(1) },
        new Object[] { mtwentyFive(), new Integer(1) },
    };

    @Override
    @DataProvider(name = "legendre", parallel = true)
    public Object[][] legendreProvider() {
        return LEGENDRE_TEST_CASES;
    }

    private static final Object[][] QUARTIC_LEGENDRE_TEST_CASES =
        new Object[][] {
        new Object[] { ModE255M19.zero(), new Integer(0) },
        new Object[] { ModE255M19.one(), new Integer(1) },
        new Object[] { three(), new Integer(1) },
        new Object[] { four(), new Integer(-1) },
        new Object[] { five(), new Integer(1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE255M19.mone(), new Integer(-1) },
        new Object[] { mthree(), new Integer(-1) },
        new Object[] { mfour(), new Integer(1) },
        new Object[] { mfive(), new Integer(-1) },
        new Object[] { mnine(), new Integer(-1) },
        new Object[] { msixteen(), new Integer(-1) },
        new Object[] { mtwentyFive(), new Integer(-1) },
    };

    @Override
    @DataProvider(name = "legendreQuartic", parallel = true)
    public Object[][] legendreQuarticProvider() {
        return QUARTIC_LEGENDRE_TEST_CASES;
    }

    private static final Object[][] SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE255M19.zero(), ModE255M19.zero() },
        new Object[] { ModE255M19.one(), ModE255M19.one() },
        new Object[] { sixteen(), mfour() },
        new Object[] { twentyFive(), five() },
    };

    @Override
    @DataProvider(name = "sqrt", parallel = true)
    public Object[][] sqrtProvider() {
        return SQRT_TEST_CASES;
    }

    private static final Object[][] INV_SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE255M19.zero(), ModE255M19.zero() },
        new Object[] { ModE255M19.one(), ModE255M19.one() },
        new Object[] { sixteen(), mfour() },
        new Object[] { twentyFive(), five() },
    };

    static {
        for(int i = 0; i < INV_SQRT_TEST_CASES.length; i++) {
            ((ModE255M19)INV_SQRT_TEST_CASES[i][1]).inv();
        }
    };

    @Override
    @DataProvider(name = "invSqrt", parallel = true)
    public Object[][] invSqrtProvider() {
        return INV_SQRT_TEST_CASES;
    }

    private static final Object[][] ABS_TEST_CASES = new Object[][] {
        new Object[] { ModE255M19.zero(), ModE255M19.zero() },
        new Object[] { ModE255M19.one(), ModE255M19.one() },
        new Object[] { ModE255M19.mone(), ModE255M19.one() },
        new Object[] { two(), two() },
        new Object[] { mtwo(), two() },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00fffffffffffff6L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
            new ModE255M19(
                new long[] { 0x00fffffffffffff6L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00fffffffffffff7L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
            new ModE255M19(
                new long[] { 0x00fffffffffffff6L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00fffffffffffff8L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
            new ModE255M19(
                new long[] { 0x00fffffffffffff5L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
        },
    };

    @DataProvider(name = "abs")
    public Object[][] absProvider() {
        return ABS_TEST_CASES;
    }

    private static final Object[][] SIGNUM_TEST_CASES = new Object[][] {
        new Object[] { ModE255M19.zero(), 1 },
        new Object[] { ModE255M19.one(), 1 },
        new Object[] { ModE255M19.mone(), -1 },
        new Object[] { two(), 1 },
        new Object[] { mtwo(), -1 },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00fffffffffffff6L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
            1
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00fffffffffffff7L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
            -1
        },
        new Object[] {
            new ModE255M19(
                new long[] { 0x00fffffffffffff8L, 0x00ffffffffffffffL,
                             0x00ffffffffffffffL, 0x00ffffffffffffffL,
                             0x000000003fffffffL }),
            -1
        },
    };

    @DataProvider(name = "signum")
    public Object[][] signumProvider() {
        return SIGNUM_TEST_CASES;
    }
}
