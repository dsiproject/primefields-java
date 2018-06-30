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

public class ModE222M117Test extends PrimeFieldUnitTest<ModE222M117> {
    private static final long[] TWO_DATA = new long[] { 2, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x03ffffffffffff89L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] THREE_DATA = new long[] { 3, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x03ffffffffffff88L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] FOUR_DATA = new long[] { 4, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x03ffffffffffff87L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] FIVE_DATA = new long[] { 5, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x03ffffffffffff86L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] SIX_DATA = new long[] { 6, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x03ffffffffffff85L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] SEVEN_DATA = new long[] { 7, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x03ffffffffffff84L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] EIGHT_DATA = new long[] { 8, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x03ffffffffffff83L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] NINE_DATA = new long[] { 9, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x03ffffffffffff82L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] SIXTEEN_DATA = new long[] { 16, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x03ffffffffffff7bL, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };
    private static final long[] TWENTY_FIVE_DATA = new long[] { 25, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x03ffffffffffff72L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x0000ffffffffffffL };

    private static ModE222M117 two() {
        return ModE222M117.create(TWO_DATA);
    }

    private static ModE222M117 mtwo() {
        return ModE222M117.create(M_TWO_DATA);
    }

    private static ModE222M117 three() {
        return ModE222M117.create(THREE_DATA);
    }

    private static ModE222M117 mthree() {
        return ModE222M117.create(M_THREE_DATA);
    }

    private static ModE222M117 four() {
        return ModE222M117.create(FOUR_DATA);
    }

    private static ModE222M117 mfour() {
        return ModE222M117.create(M_FOUR_DATA);
    }

    private static ModE222M117 five() {
        return ModE222M117.create(FIVE_DATA);
    }

    private static ModE222M117 mfive() {
        return ModE222M117.create(M_FIVE_DATA);
    }

    private static ModE222M117 six() {
        return ModE222M117.create(SIX_DATA);
    }

    private static ModE222M117 msix() {
        return ModE222M117.create(M_SIX_DATA);
    }

    private static ModE222M117 seven() {
        return ModE222M117.create(SEVEN_DATA);
    }

    private static ModE222M117 mseven() {
        return ModE222M117.create(M_SEVEN_DATA);
    }

    private static ModE222M117 eight() {
        return ModE222M117.create(EIGHT_DATA);
    }

    private static ModE222M117 meight() {
        return ModE222M117.create(M_EIGHT_DATA);
    }

    private static ModE222M117 nine() {
        return ModE222M117.create(NINE_DATA);
    }

    private static ModE222M117 mnine() {
        return ModE222M117.create(M_NINE_DATA);
    }

    private static ModE222M117 sixteen() {
        return ModE222M117.create(SIXTEEN_DATA);
    }

    private static ModE222M117 msixteen() {
        return ModE222M117.create(M_SIXTEEN_DATA);
    }

    private static ModE222M117 twentyFive() {
        return ModE222M117.create(TWENTY_FIVE_DATA);
    }

    private static ModE222M117 mtwentyFive() {
        return ModE222M117.create(M_TWENTY_FIVE_DATA);
    }

    @Override
    protected ModE222M117 unpack(final byte[] data) {
        return new ModE222M117(data);
    }

    @Override
    protected ModE222M117 unpackStream(final InputStream data)
        throws IOException {
        return new ModE222M117(data);
    }

    private static final Object[][] TEST_CONSTANTS_TEST_CASES = new Object[][] {
        new Object[] { new ModE222M117(0), ModE222M117.zero() },
        new Object[] { new ModE222M117(1), ModE222M117.one() },
        new Object[] { new ModE222M117(-1), ModE222M117.mone() },
        new Object[] { new ModE222M117(2), two() },
        new Object[] { new ModE222M117(-2), mtwo() },
        new Object[] { new ModE222M117(3), three() },
        new Object[] { new ModE222M117(-3), mthree() },
        new Object[] { new ModE222M117(4), four() },
        new Object[] { new ModE222M117(-4), mfour() },
        new Object[] { new ModE222M117(5), five() },
        new Object[] { new ModE222M117(-5), mfive() },
        new Object[] { new ModE222M117(6), six() },
        new Object[] { new ModE222M117(-6), msix() },
        new Object[] { new ModE222M117(7), seven() },
        new Object[] { new ModE222M117(-7), mseven() },
        new Object[] { new ModE222M117(8), eight() },
        new Object[] { new ModE222M117(-8), meight() },
        new Object[] { new ModE222M117(9), nine() },
        new Object[] { new ModE222M117(-9), mnine() },
        new Object[] { new ModE222M117(16), sixteen() },
        new Object[] { new ModE222M117(-16), msixteen() },
        new Object[] { new ModE222M117(25), twentyFive() },
        new Object[] { new ModE222M117(-25), mtwentyFive() }
    };

    @Override
    @DataProvider(name = "testConstants")
    public Object[][] testConstantsProvider() {
        return TEST_CONSTANTS_TEST_CASES;
    }

    private static final Object[][] SET_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE222M117(
                new long[] { 0x03ffffffffffffffL, 0x0000000000000000L,
                             0x03ffffffffffffffL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0000000000000000L, 0x03ffffffffffffffL,
                             0x0000000000000000L, 0x0000ffffffffffffL })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x02aaaaaaaaaaaaaaL, 0x0155555555555555L,
                             0x02aaaaaaaaaaaaaaL, 0x0000555555555555L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0155555555555555L, 0x02aaaaaaaaaaaaaaL,
                             0x0155555555555555L, 0x0000aaaaaaaaaaaaL })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x02aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x02aaaaaaaaaaaaaaL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0000000000000000L, 0x02aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x0000aaaaaaaaaaaaL })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x03ffffffffffffffL, 0x0155555555555555L,
                             0x03ffffffffffffffL, 0x0000555555555555L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0155555555555555L, 0x03ffffffffffffffL,
                             0x0155555555555555L, 0x0000ffffffffffffL })
        }
    };

    @Override
    protected ModE222M117 createEmpty() {
        return new ModE222M117(0);
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
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00 }
        },
        new Object[] {
            new byte[] { (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x3f }
        },
        new Object[] {
            new byte[] { (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
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
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x15 }
        },
        new Object[] {
            new byte[] { (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
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
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00 }
        },
        new Object[] {
            new byte[] { (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
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
            new ModE222M117(
                new long[] { 0x03ffffffffffffffL, 0x0000000000000000L,
                             0x03ffffffffffffffL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0000000000000000L, 0x03ffffffffffffffL,
                             0x0000000000000000L, 0x0000ffffffffffffL })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x02aaaaaaaaaaaaaaL, 0x0155555555555555L,
                             0x02aaaaaaaaaaaaaaL, 0x0000555555555555L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0155555555555555L, 0x02aaaaaaaaaaaaaaL,
                             0x0155555555555555L, 0x0000aaaaaaaaaaaaL })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x02aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x02aaaaaaaaaaaaaaL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0000000000000000L, 0x02aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x0000aaaaaaaaaaaaL })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x03ffffffffffffffL, 0x0155555555555555L,
                             0x03ffffffffffffffL, 0x0000555555555555L })
        },
        new Object[] {
            new ModE222M117(
                new long[] { 0x0155555555555555L, 0x03ffffffffffffffL,
                             0x0155555555555555L, 0x0000ffffffffffffL })
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

    private static final ModE222M117[][] startTier = new ModE222M117[][] {
        new ModE222M117[] { ModE222M117.zero() },
        new ModE222M117[] { ModE222M117.one() },
        new ModE222M117[] { ModE222M117.mone() },
        new ModE222M117[] { two() },
        new ModE222M117[] { mtwo() },
        new ModE222M117[] { four() },
        new ModE222M117[] { mfour() }
    };

    @Test
    public void addTest() {
        final ModE222M117[][] tierOne = addTier(startTier);
        final ModE222M117[][] tierTwo = addTier(tierOne);
        //final ModE222M117[][] tierThree = addTier(tierTwo);
    }

    @Test
    public void subTest() {
        final ModE222M117[][] tierOne = subTier(startTier);
        final ModE222M117[][] tierTwo = subTier(tierOne);
        //final ModE222M117[][] tierThree = subTier(tierTwo);
    }

    @Test
    public void mulTest() {
        final ModE222M117[][] tierOne = mulTier(startTier);
        final ModE222M117[][] tierTwo = mulTier(tierOne);
        //final ModE222M117[][] tierThree = mulTier(tierTwo);
    }

    @Test
    public void divTest() {
        final ModE222M117[][] tierOne = divTier(startTier);
        final ModE222M117[][] tierTwo = divTier(tierOne);
        //final ModE222M117[][] tierThree = divTier(tierTwo);
    }

    private static final Object[][] SQUARE_TEST_CASES = new Object[][] {
        new Object[] { ModE222M117.zero(), ModE222M117.zero() },
        new Object[] { ModE222M117.one(), ModE222M117.one() },
        new Object[] { two(), four() },
        new Object[] { three(), nine() },
        new Object[] { four(), sixteen() },
        new Object[] { five(), twentyFive() },
        new Object[] { ModE222M117.mone(), ModE222M117.one() },
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
        new Object[] { ModE222M117.zero(), new Integer(0) },
        new Object[] { ModE222M117.one(), new Integer(1) },
        new Object[] { two(), new Integer(-1) },
        new Object[] { three(), new Integer(-1) },
        new Object[] { four(), new Integer(1) },
        new Object[] { five(), new Integer(-1) },
        new Object[] { six(), new Integer(1) },
        new Object[] { seven(), new Integer(1) },
        new Object[] { eight(), new Integer(-1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE222M117.mone(), new Integer(-1) },
        new Object[] { mtwo(), new Integer(1) },
        new Object[] { mthree(), new Integer(1) },
        new Object[] { mfour(), new Integer(-1) },
        new Object[] { mfive(), new Integer(1) },
        new Object[] { msix(), new Integer(-1) },
        new Object[] { mseven(), new Integer(-1) },
        new Object[] { meight(), new Integer(1) },
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
        new Object[] { ModE222M117.zero(), ModE222M117.zero() },
        new Object[] { ModE222M117.one(), ModE222M117.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), mfive() },
    };

    @Override
    @DataProvider(name = "sqrt", parallel = true)
    public Object[][] sqrtProvider() {
        return SQRT_TEST_CASES;
    }

    private static final Object[][] INV_SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE222M117.zero(), ModE222M117.zero() },
        new Object[] { ModE222M117.one(), ModE222M117.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), mfive() },
    };

    static {
        for(int i = 0; i < INV_SQRT_TEST_CASES.length; i++) {
            ((ModE222M117)INV_SQRT_TEST_CASES[i][1]).inv();
        }
    };

    @Override
    @DataProvider(name = "invSqrt", parallel = true)
    public Object[][] invSqrtProvider() {
        return INV_SQRT_TEST_CASES;
    }

    private static final Object[][] ABS_TEST_CASES = new Object[][] {
        new Object[] { ModE222M117.zero(), ModE222M117.zero() },
        new Object[] { ModE222M117.one(), ModE222M117.one() },
        new Object[] { ModE222M117.mone(), ModE222M117.one() },
        new Object[] { two(), two() },
        new Object[] { mtwo(), two() },
        new Object[] {
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc5L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc5L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
        },
        new Object[] {
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc6L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc5L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
        },
        new Object[] {
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc7L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc4L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
        },
    };

    @DataProvider(name = "abs")
    public Object[][] absProvider() {
        return ABS_TEST_CASES;
    }

    private static final Object[][] SIGNUM_TEST_CASES = new Object[][] {
        new Object[] { ModE222M117.zero(), 1 },
        new Object[] { ModE222M117.one(), 1 },
        new Object[] { ModE222M117.mone(), -1 },
        new Object[] { two(), 1 },
        new Object[] { mtwo(), -1 },
        new Object[] {
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc5L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
            1
        },
        new Object[] {
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc6L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
            -1
        },
        new Object[] {
            new ModE222M117(
                 new long[] { 0x03ffffffffffffc7L, 0x03ffffffffffffffL,
                              0x03ffffffffffffffL, 0x00007fffffffffffL }),
            -1
        },
    };

    @DataProvider(name = "signum")
    public Object[][] signumProvider() {
        return SIGNUM_TEST_CASES;
    }
}
