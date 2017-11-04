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

public class ModE414M17Test extends PrimeFieldUnitTest<ModE414M17> {
    private static final long[] TWO_DATA =
        new long[] { 2, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x00ffffffffffffedL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] THREE_DATA =
        new long[] { 3, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x00ffffffffffffecL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] FOUR_DATA =
        new long[] { 4, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x00ffffffffffffebL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] FIVE_DATA =
        new long[] { 5, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x00ffffffffffffeaL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] SIX_DATA =
        new long[] { 6, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x00ffffffffffffe9L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] SEVEN_DATA =
        new long[] { 7, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x00ffffffffffffe8L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] EIGHT_DATA =
        new long[] { 8, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x00ffffffffffffe7L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] NINE_DATA =
        new long[] { 9, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x00ffffffffffffe6L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] SIXTEEN_DATA =
        new long[] { 16, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x00ffffffffffffdfL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };
    private static final long[] TWENTY_FIVE_DATA =
        new long[] { 25, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x00ffffffffffffd6L, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00ffffffffffffffL,
                     0x00ffffffffffffffL, 0x00000000003fffffL };

    private static ModE414M17 two() {
        return ModE414M17.create(TWO_DATA);
    }

    private static ModE414M17 mtwo() {
        return ModE414M17.create(M_TWO_DATA);
    }

    private static ModE414M17 three() {
        return ModE414M17.create(THREE_DATA);
    }

    private static ModE414M17 mthree() {
        return ModE414M17.create(M_THREE_DATA);
    }

    private static ModE414M17 four() {
        return ModE414M17.create(FOUR_DATA);
    }

    private static ModE414M17 mfour() {
        return ModE414M17.create(M_FOUR_DATA);
    }

    private static ModE414M17 five() {
        return ModE414M17.create(FIVE_DATA);
    }

    private static ModE414M17 mfive() {
        return ModE414M17.create(M_FIVE_DATA);
    }

    private static ModE414M17 six() {
        return ModE414M17.create(SIX_DATA);
    }

    private static ModE414M17 msix() {
        return ModE414M17.create(M_SIX_DATA);
    }

    private static ModE414M17 seven() {
        return ModE414M17.create(SEVEN_DATA);
    }

    private static ModE414M17 mseven() {
        return ModE414M17.create(M_SEVEN_DATA);
    }

    private static ModE414M17 eight() {
        return ModE414M17.create(EIGHT_DATA);
    }

    private static ModE414M17 meight() {
        return ModE414M17.create(M_EIGHT_DATA);
    }

    private static ModE414M17 nine() {
        return ModE414M17.create(NINE_DATA);
    }

    private static ModE414M17 mnine() {
        return ModE414M17.create(M_NINE_DATA);
    }

    private static ModE414M17 sixteen() {
        return ModE414M17.create(SIXTEEN_DATA);
    }

    private static ModE414M17 msixteen() {
        return ModE414M17.create(M_SIXTEEN_DATA);
    }

    private static ModE414M17 twentyFive() {
        return ModE414M17.create(TWENTY_FIVE_DATA);
    }

    private static ModE414M17 mtwentyFive() {
        return ModE414M17.create(M_TWENTY_FIVE_DATA);
    }

    @Override
    protected ModE414M17 unpack(final byte[] data) {
        return new ModE414M17(data);
    }

    private static final Object[][] TEST_CONSTANTS_TEST_CASES = new Object[][] {
        new Object[] { new ModE414M17(0), ModE414M17.zero() },
        new Object[] { new ModE414M17(1), ModE414M17.one() },
        new Object[] { new ModE414M17(-1), ModE414M17.mone() },
        new Object[] { new ModE414M17(2), two() },
        new Object[] { new ModE414M17(-2), mtwo() },
        new Object[] { new ModE414M17(3), three() },
        new Object[] { new ModE414M17(-3), mthree() },
        new Object[] { new ModE414M17(4), four() },
        new Object[] { new ModE414M17(-4), mfour() },
        new Object[] { new ModE414M17(5), five() },
        new Object[] { new ModE414M17(-5), mfive() },
        new Object[] { new ModE414M17(6), six() },
        new Object[] { new ModE414M17(-6), msix() },
        new Object[] { new ModE414M17(7), seven() },
        new Object[] { new ModE414M17(-7), mseven() },
        new Object[] { new ModE414M17(8), eight() },
        new Object[] { new ModE414M17(-8), meight() },
        new Object[] { new ModE414M17(9), nine() },
        new Object[] { new ModE414M17(-9), mnine() },
        new Object[] { new ModE414M17(16), sixteen() },
        new Object[] { new ModE414M17(-16), msixteen() },
        new Object[] { new ModE414M17(25), twentyFive() },
        new Object[] { new ModE414M17(-25), mtwentyFive() }
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
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x15 }
        }
    };

    @DataProvider(name = "packUnpack")
    public Object[][] packUnpackProvider() {
        return PACK_UNPACK_TEST_CASES;
    }


    private static final Object[][] UNPACK_PACK_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE414M17(
                new long[] { 0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L,
                             0x00ffffffffffffffL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00ffffffffffffffL,
                             0x0000000000000000L, 0x00000000003fffffL })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0055555555555555L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000155555L })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00aaaaaaaaaaaaaaL,
                             0x0055555555555555L, 0x00000000002aaaaaL })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x00aaaaaaaaaaaaaaL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00aaaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00000000002aaaaaL })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0055555555555555L,
                             0x00ffffffffffffffL, 0x0000000000155555L })
        },
        new Object[] {
            new ModE414M17(
                new long[] { 0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL,
                             0x0055555555555555L, 0x00ffffffffffffffL })
        }
    };

    @DataProvider(name = "unpackPack")
    public Object[][] unpackPackProvider() {
        return UNPACK_PACK_TEST_CASES;
    }

    private static final ModE414M17[][] startTier = new ModE414M17[][] {
        new ModE414M17[] { ModE414M17.zero() },
        new ModE414M17[] { ModE414M17.one() },
        new ModE414M17[] { ModE414M17.mone() },
        new ModE414M17[] { two() },
        new ModE414M17[] { mtwo() },
        new ModE414M17[] { four() },
        new ModE414M17[] { mfour() }
    };

    @Test
    public void addTest() {
        final ModE414M17[][] tierOne = addTier(startTier);
        final ModE414M17[][] tierTwo = addTier(tierOne);
        //final ModE414M17[][] tierThree = addTier(tierTwo);
    }

    @Test
    public void subTest() {
        final ModE414M17[][] tierOne = subTier(startTier);
        final ModE414M17[][] tierTwo = subTier(tierOne);
        //final ModE414M17[][] tierThree = subTier(tierTwo);
    }

    @Test
    public void mulTest() {
        final ModE414M17[][] tierOne = mulTier(startTier);
        final ModE414M17[][] tierTwo = mulTier(tierOne);
        //final ModE414M17[][] tierThree = mulTier(tierTwo);
    }

    @Test
    public void divTest() {
        final ModE414M17[][] tierOne = divTier(startTier);
        final ModE414M17[][] tierTwo = divTier(tierOne);
        //final ModE414M17[][] tierThree = divTier(tierTwo);
    }

    private static final Object[][] SQUARE_TEST_CASES = new Object[][] {
        new Object[] { ModE414M17.zero(), ModE414M17.zero() },
        new Object[] { ModE414M17.one(), ModE414M17.one() },
        new Object[] { two(), four() },
        new Object[] { three(), nine() },
        new Object[] { four(), sixteen() },
        new Object[] { five(), twentyFive() },
        new Object[] { ModE414M17.mone(), ModE414M17.one() },
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
        new Object[] { ModE414M17.zero(), new Integer(0) },
        new Object[] { ModE414M17.one(), new Integer(1) },
        new Object[] { two(), new Integer(1) },
        new Object[] { three(), new Integer(1) },
        new Object[] { four(), new Integer(1) },
        new Object[] { five(), new Integer(-1) },
        new Object[] { six(), new Integer(1) },
        new Object[] { seven(), new Integer(1) },
        new Object[] { eight(), new Integer(1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE414M17.mone(), new Integer(-1) },
        new Object[] { mtwo(), new Integer(-1) },
        new Object[] { mthree(), new Integer(-1) },
        new Object[] { mfour(), new Integer(-1) },
        new Object[] { mfive(), new Integer(1) },
        new Object[] { msix(), new Integer(-1) },
        new Object[] { mseven(), new Integer(-1) },
        new Object[] { meight(), new Integer(-1) },
        new Object[] { mnine(), new Integer(-1) },
        new Object[] { msixteen(), new Integer(-1) },
        new Object[] { mtwentyFive(), new Integer(-1) },
    };

    @DataProvider(name = "legendre", parallel = true)
    public Object[][] legendreProvider() {
        return LEGENDRE_TEST_CASES;
    }

    private static final Object[][] SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE414M17.zero(), ModE414M17.zero() },
        new Object[] { ModE414M17.one(), ModE414M17.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), mfive() },
    };

    @DataProvider(name = "sqrt", parallel = true)
    public Object[][] sqrtProvider() {
        return SQRT_TEST_CASES;
    }

    private static final Object[][] INV_SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE414M17.zero(), ModE414M17.zero() },
        new Object[] { ModE414M17.one(), ModE414M17.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), mfive() },
    };

    static {
        for(int i = 0; i < INV_SQRT_TEST_CASES.length; i++) {
            ((ModE414M17)INV_SQRT_TEST_CASES[i][1]).inv();
        }
    };

    @DataProvider(name = "invSqrt", parallel = true)
    public Object[][] invSqrtProvider() {
        return INV_SQRT_TEST_CASES;
    }
}
