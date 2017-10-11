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
import org.testng.annotations.Test;

public class ModE221M3Test {
    private static final long[] TWO_DATA = new long[] { 2, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x03fffffffffffffbL, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] THREE_DATA = new long[] { 3, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x03fffffffffffffaL, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] FOUR_DATA = new long[] { 4, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x03fffffffffffff9L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] FIVE_DATA = new long[] { 5, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x03fffffffffffff8L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] SIX_DATA = new long[] { 6, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x03fffffffffffff7L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] SEVEN_DATA = new long[] { 7, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x03fffffffffffff6L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] EIGHT_DATA = new long[] { 8, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x03fffffffffffff5L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] NINE_DATA = new long[] { 9, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x03fffffffffffff4L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] SIXTEEN_DATA = new long[] { 16, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x03ffffffffffffedL, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };
    private static final long[] TWENTY_FIVE_DATA = new long[] { 25, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x03ffffffffffffe3L, 0x03ffffffffffffffL,
                     0x03ffffffffffffffL, 0x00007fffffffffffL };

    private static ModE221M3 two() {
        return ModE221M3.create(TWO_DATA);
    }

    private static ModE221M3 mtwo() {
        return ModE221M3.create(M_TWO_DATA);
    }

    private static ModE221M3 three() {
        return ModE221M3.create(THREE_DATA);
    }

    private static ModE221M3 mthree() {
        return ModE221M3.create(M_THREE_DATA);
    }

    private static ModE221M3 four() {
        return ModE221M3.create(FOUR_DATA);
    }

    private static ModE221M3 mfour() {
        return ModE221M3.create(M_FOUR_DATA);
    }

    private static ModE221M3 five() {
        return ModE221M3.create(FIVE_DATA);
    }

    private static ModE221M3 mfive() {
        return ModE221M3.create(M_FIVE_DATA);
    }

    private static ModE221M3 six() {
        return ModE221M3.create(SIX_DATA);
    }

    private static ModE221M3 msix() {
        return ModE221M3.create(M_SIX_DATA);
    }

    private static ModE221M3 seven() {
        return ModE221M3.create(SEVEN_DATA);
    }

    private static ModE221M3 mseven() {
        return ModE221M3.create(M_SEVEN_DATA);
    }

    private static ModE221M3 eight() {
        return ModE221M3.create(EIGHT_DATA);
    }

    private static ModE221M3 meight() {
        return ModE221M3.create(M_EIGHT_DATA);
    }

    private static ModE221M3 nine() {
        return ModE221M3.create(NINE_DATA);
    }

    private static ModE221M3 mnine() {
        return ModE221M3.create(M_NINE_DATA);
    }

    private static ModE221M3 sixteen() {
        return ModE221M3.create(SIXTEEN_DATA);
    }

    private static ModE221M3 msixteen() {
        return ModE221M3.create(M_SIXTEEN_DATA);
    }

    private static ModE221M3 twentyFive() {
        return ModE221M3.create(TWENTY_FIVE_DATA);
    }

    private static ModE221M3 mtwentyFive() {
        return ModE221M3.create(M_TWENTY_FIVE_DATA);
    }

    @Test
    public static final void testConstants() {
        Assert.assertEquals(new ModE221M3(0), ModE221M3.zero());
        Assert.assertEquals(new ModE221M3(1), ModE221M3.one());
        Assert.assertEquals(new ModE221M3(-1), ModE221M3.mone());
        Assert.assertEquals(new ModE221M3(2), two());
        Assert.assertEquals(new ModE221M3(-2), mtwo());
        Assert.assertEquals(new ModE221M3(3), three());
        Assert.assertEquals(new ModE221M3(-3), mthree());
        Assert.assertEquals(new ModE221M3(4), four());
        Assert.assertEquals(new ModE221M3(-4), mfour());
        Assert.assertEquals(new ModE221M3(5), five());
        Assert.assertEquals(new ModE221M3(-5), mfive());
        Assert.assertEquals(new ModE221M3(6), six());
        Assert.assertEquals(new ModE221M3(-6), msix());
        Assert.assertEquals(new ModE221M3(7), seven());
        Assert.assertEquals(new ModE221M3(-7), mseven());
        Assert.assertEquals(new ModE221M3(8), eight());
        Assert.assertEquals(new ModE221M3(-8), meight());
        Assert.assertEquals(new ModE221M3(9), nine());
        Assert.assertEquals(new ModE221M3(-9), mnine());
        Assert.assertEquals(new ModE221M3(16), sixteen());
        Assert.assertEquals(new ModE221M3(25), twentyFive());
    }

    private static final byte[][] PACK_UNPACK_TEST_CASES = new byte[][] {
        new byte[] { (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00 },
        new byte[] { (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x1f },
        new byte[] { (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                     (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                     (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                     (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                     (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                     (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                     (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0x0a },
        new byte[] { (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                     (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                     (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                     (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                     (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                     (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                     (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x15 },
        new byte[] { (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                     (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                     (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                     (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                     (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                     (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                     (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0x0a },
        new byte[] { (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                     (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                     (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                     (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                     (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                     (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                     (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00 },
        new byte[] { (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0x1f },
        new byte[] { (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x15 }
    };

    private static void packUnpackTestCase(final byte[] testcase) {
        final ModE221M3 unpacked = new ModE221M3(testcase);
        final byte[] packed = unpacked.packed();

        for(int i = 0; i < ModE221M3.PACKED_BYTES; i++) {
            Assert.assertEquals(packed[i], testcase[i]);
        }
    }

    @Test
    public static void packUnpackTest() {
        for(int i = 0; i < PACK_UNPACK_TEST_CASES.length; i++) {
            packUnpackTestCase(PACK_UNPACK_TEST_CASES[i]);
        }
    }

    private static void unpackPackTestCase(final long[] testcase) {
        final ModE221M3 expected = new ModE221M3(testcase);
        final byte[] packed = expected.packed();
        final ModE221M3 actual = new ModE221M3(packed);

        Assert.assertEquals(actual, expected);
    }

    private static final long[][] UNPACK_PACK_TEST_CASES = new long[][] {
        new long[] { 0x03ffffffffffffffL, 0x0000000000000000L,
                     0x03ffffffffffffffL, 0x0000000000000000L },
        new long[] { 0x0000000000000000L, 0x03ffffffffffffffL,
                     0x0000000000000000L, 0x00007fffffffffffL },
        new long[] { 0x02aaaaaaaaaaaaaaL, 0x0155555555555555L,
                     0x02aaaaaaaaaaaaaaL, 0x0000555555555555L },
        new long[] { 0x0155555555555555L, 0x02aaaaaaaaaaaaaaL,
                     0x0155555555555555L, 0x00002aaaaaaaaaaaL },
        new long[] { 0x02aaaaaaaaaaaaaaL, 0x0000000000000000L,
                     0x02aaaaaaaaaaaaaaL, 0x0000000000000000L },
        new long[] { 0x0000000000000000L, 0x02aaaaaaaaaaaaaaL,
                     0x0000000000000000L, 0x00002aaaaaaaaaaaL },
        new long[] { 0x03ffffffffffffffL, 0x0155555555555555L,
                     0x03ffffffffffffffL, 0x0000555555555555L },
        new long[] { 0x0155555555555555L, 0x03ffffffffffffffL,
                     0x0155555555555555L, 0x00007fffffffffffL }
    };

    @Test
    public static void unpackPackTest() {
        for(int i = 0; i < UNPACK_PACK_TEST_CASES.length; i++) {
            unpackPackTestCase(UNPACK_PACK_TEST_CASES[i]);
        }
    }

    private static final ModE221M3[][] startTier = new ModE221M3[][] {
        new ModE221M3[] { ModE221M3.zero() },
        new ModE221M3[] { ModE221M3.one() },
        new ModE221M3[] { ModE221M3.mone() },
        new ModE221M3[] { two() },
        new ModE221M3[] { mtwo() },
        new ModE221M3[] { four() },
        new ModE221M3[] { mfour() }
    };

    @Test
    public static void addTest() {
        final ModE221M3[][] tierOne = Utils.addTier(startTier);
        final ModE221M3[][] tierTwo = Utils.addTier(tierOne);
        final ModE221M3[][] tierThree = Utils.addTier(tierTwo);
    }

    @Test
    public static void subTest() {
        final ModE221M3[][] tierOne = Utils.subTier(startTier);
        final ModE221M3[][] tierTwo = Utils.subTier(tierOne);
        final ModE221M3[][] tierThree = Utils.subTier(tierTwo);
    }

    @Test
    public static void mulTest() {
        final ModE221M3[][] tierOne = Utils.mulTier(startTier);
        final ModE221M3[][] tierTwo = Utils.mulTier(tierOne);
        final ModE221M3[][] tierThree = Utils.mulTier(tierTwo);
    }

    @Test
    public static void divTest() {
        final ModE221M3[][] tierOne = Utils.divTier(startTier);
        final ModE221M3[][] tierTwo = Utils.divTier(tierOne);
        final ModE221M3[][] tierThree = Utils.divTier(tierTwo);
    }

    @Test
    public static void squareTest() {
        final ModE221M3 s0 = ModE221M3.zero();
        final ModE221M3 s1 = ModE221M3.one();
        final ModE221M3 s2 = two();
        final ModE221M3 s3 = three();
        final ModE221M3 s4 = four();
        final ModE221M3 s5 = five();
        final ModE221M3 sm1 = ModE221M3.mone();
        final ModE221M3 sm2 = mtwo();
        final ModE221M3 sm3 = mthree();
        final ModE221M3 sm4 = mfour();
        final ModE221M3 sm5 = mfive();

        s0.square();
        s1.square();
        s2.square();
        s3.square();
        s4.square();
        s5.square();
        sm1.square();
        sm2.square();
        sm3.square();
        sm4.square();
        sm5.square();

        Assert.assertEquals(s0, ModE221M3.zero());
        Assert.assertEquals(s1, ModE221M3.one());
        Assert.assertEquals(s2, four());
        Assert.assertEquals(s3, nine());
        Assert.assertEquals(s4, sixteen());
        Assert.assertEquals(s5, twentyFive());
        Assert.assertEquals(sm1, ModE221M3.one());
        Assert.assertEquals(sm2, four());
        Assert.assertEquals(sm3, nine());
        Assert.assertEquals(sm4, sixteen());
        Assert.assertEquals(sm5, twentyFive());
    }

    @Test
    public static void legendreTest() {
        Assert.assertEquals(ModE221M3.zero().legendre(), 0);
        Assert.assertEquals(ModE221M3.one().legendre(), 1);
        Assert.assertEquals(two().legendre(), -1);
        Assert.assertEquals(three().legendre(), -1);
        Assert.assertEquals(four().legendre(), 1);
        Assert.assertEquals(five().legendre(), 1);
        Assert.assertEquals(six().legendre(), 1);
        Assert.assertEquals(seven().legendre(), 1);
        Assert.assertEquals(eight().legendre(), -1);
        Assert.assertEquals(nine().legendre(), 1);
        Assert.assertEquals(sixteen().legendre(), 1);
        Assert.assertEquals(twentyFive().legendre(), 1);
        Assert.assertEquals(ModE221M3.mone().legendre(), 1);
        Assert.assertEquals(mtwo().legendre(), -1);
        Assert.assertEquals(mthree().legendre(), -1);
        Assert.assertEquals(mfour().legendre(), 1);
        Assert.assertEquals(mfive().legendre(), 1);
        Assert.assertEquals(msix().legendre(), 1);
        Assert.assertEquals(mseven().legendre(), 1);
        Assert.assertEquals(meight().legendre(), -1);
        Assert.assertEquals(mnine().legendre(), 1);
        Assert.assertEquals(msixteen().legendre(), 1);
        Assert.assertEquals(mtwentyFive().legendre(), -1);
    }

    @Test
    public static void legendreQuarticTest() {
        Assert.assertEquals(ModE221M3.zero().legendreQuartic(), 0);
        Assert.assertEquals(ModE221M3.one().legendreQuartic(), 1);
        Assert.assertEquals(four().legendreQuartic(), -1);
        Assert.assertEquals(five().legendreQuartic(), -1);
        Assert.assertEquals(six().legendreQuartic(), -1);
        Assert.assertEquals(seven().legendreQuartic(), 1);
        Assert.assertEquals(nine().legendreQuartic(), -1);
        Assert.assertEquals(sixteen().legendreQuartic(), 1);
        Assert.assertEquals(twentyFive().legendreQuartic(), 1);
        Assert.assertEquals(ModE221M3.mone().legendreQuartic(), -1);
        Assert.assertEquals(mfour().legendreQuartic(), 1);
        Assert.assertEquals(mfive().legendreQuartic(), 1);
        Assert.assertEquals(msix().legendreQuartic(), 1);
        Assert.assertEquals(mseven().legendreQuartic(), -1);
        Assert.assertEquals(mnine().legendreQuartic(), 1);
        Assert.assertEquals(msixteen().legendreQuartic(), -1);
    }

    @Test
    public static void sqrtTest() {
        final ModE221M3 s0 = ModE221M3.zero();
        final ModE221M3 s1 = ModE221M3.one();
        final ModE221M3 s16 = sixteen();
        final ModE221M3 s25 = twentyFive();

        s0.sqrt();
        s1.sqrt();
        s16.sqrt();
        s25.sqrt();

        Assert.assertEquals(ModE221M3.zero(), s0);
        Assert.assertEquals(ModE221M3.one(), s1);
        Assert.assertEquals(mfour(), s16);
        Assert.assertEquals(mfive(), s25);
    }

    @Test
    public static void invSqrtTest() {
        final ModE221M3 srm4 = mfour();
        final ModE221M3 srm5 = mfive();
        final ModE221M3 s16 = sixteen();
        final ModE221M3 s25 = twentyFive();

        srm4.inv();
        srm5.inv();
        s16.invSqrt();
        s25.invSqrt();

        Assert.assertEquals(s16, srm4);
        Assert.assertEquals(s25, srm5);
    }

    private static final int[] primes = new int[] {
        2, 3, 71, 103, 809,
        3907, 7919, 50821, 93719, 199933
    };

    private static final ModE221M3[][] primePowers = new ModE221M3[4][10];
    private static final ModE221M3[] values = new ModE221M3[10000];

    static {
        for(int i = 0; i < primePowers.length; i++) {
            for(int j = 0; j < primePowers[i].length; j++) {
                final int power = 1 << i;
                final ModE221M3 val = new ModE221M3(primes[j]);

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
                        final ModE221M3 val = primePowers[0][i].clone();
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

    @Test
    public static void addSubTest() {
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values.length; j++) {
                Utils.addSubTest(values[i], values[j]);
            }
        }
    }

    @Test
    public static void mulDivTest() {
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values.length; j++) {
                Utils.mulDivTest(values[i], values[j]);
            }
        }
    }

    @Test
    public static void mulSquareTest() {
        for(int i = 0; i < values.length; i++) {
            Utils.mulSquareTest(values[i]);
        }
    }

    @Test
    public static void invSqrtMulTest() {
        for(int i = 0; i < values.length; i++) {
            Utils.invSqrtMulTest(values[i]);
        }
    }

    @Test
    public static void quadraticResidueTest() {
        for(int i = 0; i < values.length; i++) {
            Utils.quadraticResidueTest(values[i]);
        }
    }

    @Test
    public static void quarticResidueTest() {
        for(int i = 0; i < values.length; i++) {
            Utils.quarticResidueTest(values[i]);
        }
    }

    @Test
    public static void absTest() {
        for(int i = 0; i < values.length; i++) {
            Utils.absTest(values[i]);
        }
    }
}
