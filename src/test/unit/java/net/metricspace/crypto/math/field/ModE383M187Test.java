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

public class ModE383M187Test extends PrimeField1Mod4UnitTest<ModE383M187> {
    private static final long[] TWO_DATA = new long[] { 2, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x00ffffffffffff43L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] THREE_DATA = new long[] { 3, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x00ffffffffffff42L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] FOUR_DATA = new long[] { 4, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x00ffffffffffff41L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] FIVE_DATA = new long[] { 5, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x00ffffffffffff40L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] SIX_DATA = new long[] { 6, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x00ffffffffffff3fL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] SEVEN_DATA = new long[] { 7, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x00ffffffffffff3eL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] EIGHT_DATA = new long[] { 8, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x00ffffffffffff3dL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] NINE_DATA = new long[] { 9, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x00ffffffffffff3cL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] SIXTEEN_DATA =
        new long[] { 16, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x00ffffffffffff35L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };
    private static final long[] TWENTY_FIVE_DATA =
        new long[] { 25, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x00ffffffffffff2cL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00007fffffffffffL };

    private static ModE383M187 two() {
        return ModE383M187.create(TWO_DATA);
    }

    private static ModE383M187 mtwo() {
        return ModE383M187.create(M_TWO_DATA);
    }

    private static ModE383M187 three() {
        return ModE383M187.create(THREE_DATA);
    }

    private static ModE383M187 mthree() {
        return ModE383M187.create(M_THREE_DATA);
    }

    private static ModE383M187 four() {
        return ModE383M187.create(FOUR_DATA);
    }

    private static ModE383M187 mfour() {
        return ModE383M187.create(M_FOUR_DATA);
    }

    private static ModE383M187 five() {
        return ModE383M187.create(FIVE_DATA);
    }

    private static ModE383M187 mfive() {
        return ModE383M187.create(M_FIVE_DATA);
    }

    private static ModE383M187 six() {
        return ModE383M187.create(SIX_DATA);
    }

    private static ModE383M187 msix() {
        return ModE383M187.create(M_SIX_DATA);
    }

    private static ModE383M187 seven() {
        return ModE383M187.create(SEVEN_DATA);
    }

    private static ModE383M187 mseven() {
        return ModE383M187.create(M_SEVEN_DATA);
    }

    private static ModE383M187 eight() {
        return ModE383M187.create(EIGHT_DATA);
    }

    private static ModE383M187 meight() {
        return ModE383M187.create(M_EIGHT_DATA);
    }

    private static ModE383M187 nine() {
        return ModE383M187.create(NINE_DATA);
    }

    private static ModE383M187 mnine() {
        return ModE383M187.create(M_NINE_DATA);
    }

    private static ModE383M187 sixteen() {
        return ModE383M187.create(SIXTEEN_DATA);
    }

    private static ModE383M187 msixteen() {
        return ModE383M187.create(M_SIXTEEN_DATA);
    }

    private static ModE383M187 twentyFive() {
        return ModE383M187.create(TWENTY_FIVE_DATA);
    }

    private static ModE383M187 mtwentyFive() {
        return ModE383M187.create(M_TWENTY_FIVE_DATA);
    }

    @Override
    protected ModE383M187 unpack(final byte[] data) {
        return new ModE383M187(data);
    }

    private static final Object[][] TEST_CONSTANTS_TEST_CASES = new Object[][] {
        new Object[] { new ModE383M187(0), ModE383M187.zero() },
        new Object[] { new ModE383M187(1), ModE383M187.one() },
        new Object[] { new ModE383M187(-1), ModE383M187.mone() },
        new Object[] { new ModE383M187(2), two() },
        new Object[] { new ModE383M187(-2), mtwo() },
        new Object[] { new ModE383M187(3), three() },
        new Object[] { new ModE383M187(-3), mthree() },
        new Object[] { new ModE383M187(4), four() },
        new Object[] { new ModE383M187(-4), mfour() },
        new Object[] { new ModE383M187(5), five() },
        new Object[] { new ModE383M187(-5), mfive() },
        new Object[] { new ModE383M187(6), six() },
        new Object[] { new ModE383M187(-6), msix() },
        new Object[] { new ModE383M187(7), seven() },
        new Object[] { new ModE383M187(-7), mseven() },
        new Object[] { new ModE383M187(8), eight() },
        new Object[] { new ModE383M187(-8), meight() },
        new Object[] { new ModE383M187(9), nine() },
        new Object[] { new ModE383M187(-9), mnine() },
        new Object[] { new ModE383M187(16), sixteen() },
        new Object[] { new ModE383M187(-16), msixteen() },
        new Object[] { new ModE383M187(25), twentyFive() },
        new Object[] { new ModE383M187(-25), mtwentyFive() }
    };

    @DataProvider(name = "testConstants")
    public Object[][] testConstantsProvider() {
        return TEST_CONSTANTS_TEST_CASES;
    }


    private static final Object[][] PACK_UNPACK_TEST_CASES = new Object [][] {
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
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55 }
        }
    };

    @DataProvider(name = "packUnpack")
    public Object[][] packUnpackProvider() {
        return PACK_UNPACK_TEST_CASES;
    }


    private static final Object[][] UNPACK_PACK_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE383M187(
                new long[] { 0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00007fffffffffffL })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00002aaaaaaaaaaaL })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0000555555555555L })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00002aaaaaaaaaaaL })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00007fffffffffffL })
        },
        new Object[] {
            new ModE383M187(
                new long[] { 0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0000555555555555L })
        }
    };

    @DataProvider(name = "unpackPack")
    public Object[][] unpackPackProvider() {
        return UNPACK_PACK_TEST_CASES;
    }

    private static final ModE383M187[][] startTier = new ModE383M187[][] {
        new ModE383M187[] { ModE383M187.zero() },
        new ModE383M187[] { ModE383M187.one() },
        new ModE383M187[] { ModE383M187.mone() },
        new ModE383M187[] { two() },
        new ModE383M187[] { mtwo() },
        new ModE383M187[] { four() },
        new ModE383M187[] { mfour() }
    };

    @Test
    public void addTest() {
        final ModE383M187[][] tierOne = addTier(startTier);
        final ModE383M187[][] tierTwo = addTier(tierOne);
        //final ModE383M187[][] tierThree = addTier(tierTwo);
    }

    @Test
    public void subTest() {
        final ModE383M187[][] tierOne = subTier(startTier);
        final ModE383M187[][] tierTwo = subTier(tierOne);
        //final ModE383M187[][] tierThree = subTier(tierTwo);
    }

    @Test
    public void mulTest() {
        final ModE383M187[][] tierOne = mulTier(startTier);
        final ModE383M187[][] tierTwo = mulTier(tierOne);
        //final ModE383M187[][] tierThree = mulTier(tierTwo);
    }

    @Test
    public void divTest() {
        final ModE383M187[][] tierOne = divTier(startTier);
        final ModE383M187[][] tierTwo = divTier(tierOne);
        //final ModE383M187[][] tierThree = divTier(tierTwo);
    }

    private static final Object[][] SQUARE_TEST_CASES = new Object[][] {
        new Object[] { ModE383M187.zero(), ModE383M187.zero() },
        new Object[] { ModE383M187.one(), ModE383M187.one() },
        new Object[] { two(), four() },
        new Object[] { three(), nine() },
        new Object[] { four(), sixteen() },
        new Object[] { five(), twentyFive() },
        new Object[] { ModE383M187.mone(), ModE383M187.one() },
        new Object[] { mtwo(), four() },
        new Object[] { mthree(), nine() },
        new Object[] { mfour(), sixteen() },
        new Object[] { mfive(), twentyFive() },
    };

    @DataProvider(name = "square")
    public Object[][] squareProvider() {
        return SQUARE_TEST_CASES;
    }

    private static final Object[][] LEGENDRE_TEST_CASES = new Object[][] {
        new Object[] { ModE383M187.zero(), new Integer(0) },
        new Object[] { ModE383M187.one(), new Integer(1) },
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
        new Object[] { ModE383M187.mone(), new Integer(1) },
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

    @DataProvider(name = "legendre", parallel = true)
    public Object[][] legendreProvider() {
        return LEGENDRE_TEST_CASES;
    }

    private static final Object[][] QUARTIC_LEGENDRE_TEST_CASES =
        new Object[][] {
        new Object[] { ModE383M187.zero(), new Integer(0) },
        new Object[] { ModE383M187.one(), new Integer(1) },
        new Object[] { three(), new Integer(-1) },
        new Object[] { four(), new Integer(-1) },
        new Object[] { five(), new Integer(-1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE383M187.mone(), new Integer(-1) },
        new Object[] { mthree(), new Integer(1) },
        new Object[] { mfour(), new Integer(1) },
        new Object[] { mfive(), new Integer(1) },
        new Object[] { mnine(), new Integer(-1) },
        new Object[] { msixteen(), new Integer(-1) },
        new Object[] { mtwentyFive(), new Integer(-1) },
    };

    @DataProvider(name = "legendreQuartic", parallel = true)
    public Object[][] legendreQuarticProvider() {
        return QUARTIC_LEGENDRE_TEST_CASES;
    }

    private static final Object[][] SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE383M187.zero(), ModE383M187.zero() },
        new Object[] { ModE383M187.one(), ModE383M187.one() },
        new Object[] { sixteen(), mfour() },
        new Object[] { twentyFive(), mfive() },
    };

    @DataProvider(name = "sqrt", parallel = true)
    public Object[][] sqrtProvider() {
        return SQRT_TEST_CASES;
    }

    private static final Object[][] INV_SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE383M187.zero(), ModE383M187.zero() },
        new Object[] { ModE383M187.one(), ModE383M187.one() },
        new Object[] { sixteen(), mfour() },
        new Object[] { twentyFive(), mfive() },
    };

    static {
        for(int i = 0; i < INV_SQRT_TEST_CASES.length; i++) {
            ((ModE383M187)INV_SQRT_TEST_CASES[i][1]).inv();
        }
    };

    @DataProvider(name = "invSqrt", parallel = true)
    public Object[][] invSqrtProvider() {
        return INV_SQRT_TEST_CASES;
    }
}
