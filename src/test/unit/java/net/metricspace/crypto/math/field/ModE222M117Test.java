package net.metricspace.crypto.math.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ModE222M117Test {
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

    @Test
    public static final void testConstants() {
        Assert.assertEquals(new ModE222M117(0), ModE222M117.zero());
        Assert.assertEquals(new ModE222M117(1), ModE222M117.one());
        Assert.assertEquals(new ModE222M117(-1), ModE222M117.mone());
        Assert.assertEquals(new ModE222M117(2), two());
        Assert.assertEquals(new ModE222M117(-2), mtwo());
        Assert.assertEquals(new ModE222M117(3), three());
        Assert.assertEquals(new ModE222M117(-3), mthree());
        Assert.assertEquals(new ModE222M117(4), four());
        Assert.assertEquals(new ModE222M117(-4), mfour());
        Assert.assertEquals(new ModE222M117(5), five());
        Assert.assertEquals(new ModE222M117(-5), mfive());
        Assert.assertEquals(new ModE222M117(6), six());
        Assert.assertEquals(new ModE222M117(-6), msix());
        Assert.assertEquals(new ModE222M117(7), seven());
        Assert.assertEquals(new ModE222M117(-7), mseven());
        Assert.assertEquals(new ModE222M117(8), eight());
        Assert.assertEquals(new ModE222M117(-8), meight());
        Assert.assertEquals(new ModE222M117(9), nine());
        Assert.assertEquals(new ModE222M117(-9), mnine());
        Assert.assertEquals(new ModE222M117(16), sixteen());
        Assert.assertEquals(new ModE222M117(25), twentyFive());
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
                     (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x3f },
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
                     (byte)0x55, (byte)0xff, (byte)0x55, (byte)0x3f },
        new byte[] { (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                     (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x15 }
    };

    private static void packUnpackTestCase(final byte[] testcase) {
        final ModE222M117 unpacked = new ModE222M117(testcase);
        final byte[] packed = unpacked.packed();

        for(int i = 0; i < ModE222M117.PACKED_BYTES; i++) {
            Assert.assertEquals(packed[i], testcase[i]);
        }
    }

    @Test
    public static void packUnpackTest() {
        for(int i = 0; i < PACK_UNPACK_TEST_CASES.length; i++) {
            packUnpackTestCase(PACK_UNPACK_TEST_CASES[i]);
        }
    }

    private static final long[][] UNPACK_PACK_TEST_CASES = new long[][] {
        new long[] { 0x03ffffffffffffffL, 0x0000000000000000L,
                     0x03ffffffffffffffL, 0x0000000000000000L },
        new long[] { 0x0000000000000000L, 0x03ffffffffffffffL,
                     0x0000000000000000L, 0x0000ffffffffffffL },
        new long[] { 0x02aaaaaaaaaaaaaaL, 0x0155555555555555L,
                     0x02aaaaaaaaaaaaaaL, 0x0000555555555555L },
        new long[] { 0x0155555555555555L, 0x02aaaaaaaaaaaaaaL,
                     0x0155555555555555L, 0x0000aaaaaaaaaaaaL },
        new long[] { 0x02aaaaaaaaaaaaaaL, 0x0000000000000000L,
                     0x02aaaaaaaaaaaaaaL, 0x0000000000000000L },
        new long[] { 0x0000000000000000L, 0x02aaaaaaaaaaaaaaL,
                     0x0000000000000000L, 0x0000aaaaaaaaaaaaL },
        new long[] { 0x03ffffffffffffffL, 0x0155555555555555L,
                     0x03ffffffffffffffL, 0x0000555555555555L },
        new long[] { 0x0155555555555555L, 0x03ffffffffffffffL,
                     0x0155555555555555L, 0x0000ffffffffffffL }
    };

    @Test
    public static void unpackPackTest() {
        for(int i = 0; i < UNPACK_PACK_TEST_CASES.length; i++) {
            unpackPackTestCase(UNPACK_PACK_TEST_CASES[i]);
        }
    }

    private static void unpackPackTestCase(final long[] testcase) {
        final ModE222M117 expected = new ModE222M117(testcase);
        final byte[] packed = expected.packed();
        final ModE222M117 actual = new ModE222M117(packed);

        Assert.assertEquals(actual, expected);
    }

    private static final int ZERO_IDX = 0;
    private static final int ONE_IDX = 1;
    private static final int MONE_IDX = 2;
    private static final int TWO_IDX = 3;
    private static final int MTWO_IDX = 4;
    private static final int FOUR_IDX = 5;
    private static final int MFOUR_IDX = 6;

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
    public static void addTest() {
        final ModE222M117[][] tierOne = Utils.addTier(startTier);
        final ModE222M117[][] tierTwo = Utils.addTier(tierOne);
        final ModE222M117[][] tierThree = Utils.addTier(tierTwo);
    }

    @Test
    public static void subTest() {
        final ModE222M117[][] tierOne = Utils.subTier(startTier);
        final ModE222M117[][] tierTwo = Utils.subTier(tierOne);
        final ModE222M117[][] tierThree = Utils.subTier(tierTwo);
    }

    @Test
    public static void mulTest() {
        final ModE222M117[][] tierOne = Utils.mulTier(startTier);
        final ModE222M117[][] tierTwo = Utils.mulTier(tierOne);
        final ModE222M117[][] tierThree = Utils.mulTier(tierTwo);
    }

    @Test
    public static void divTest() {
        final ModE222M117[][] tierOne = Utils.divTier(startTier);
        final ModE222M117[][] tierTwo = Utils.divTier(tierOne);
        final ModE222M117[][] tierThree = Utils.divTier(tierTwo);
    }

    @Test
    public static void squareTest() {
        final ModE222M117 s0 = ModE222M117.zero();
        final ModE222M117 s1 = ModE222M117.one();
        final ModE222M117 s2 = two();
        final ModE222M117 s3 = three();
        final ModE222M117 s4 = four();
        final ModE222M117 s5 = five();
        final ModE222M117 sm1 = ModE222M117.mone();
        final ModE222M117 sm2 = mtwo();
        final ModE222M117 sm3 = mthree();
        final ModE222M117 sm4 = mfour();
        final ModE222M117 sm5 = mfive();

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

        Assert.assertEquals(s0, ModE222M117.zero());
        Assert.assertEquals(s1, ModE222M117.one());
        Assert.assertEquals(s2, four());
        Assert.assertEquals(s3, nine());
        Assert.assertEquals(s4, sixteen());
        Assert.assertEquals(s5, twentyFive());
        Assert.assertEquals(sm1, ModE222M117.one());
        Assert.assertEquals(sm2, four());
        Assert.assertEquals(sm3, nine());
        Assert.assertEquals(sm4, sixteen());
        Assert.assertEquals(sm5, twentyFive());
    }

    @Test
    public static void legendreTest() {
        Assert.assertEquals(ModE222M117.zero().legendre(), 0);
        Assert.assertEquals(ModE222M117.one().legendre(), 1);
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
        Assert.assertEquals(ModE222M117.mone().legendre(), 1);
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
    public static void sqrtTest() {
        final ModE222M117 s0 = ModE222M117.zero();
        final ModE222M117 s1 = ModE222M117.one();
        final ModE222M117 s16 = sixteen();
        final ModE222M117 s25 = twentyFive();

        s0.sqrt();
        s1.sqrt();
        s16.sqrt();
        s25.sqrt();

        Assert.assertEquals(ModE222M117.zero(), s0);
        Assert.assertEquals(ModE222M117.one(), s1);
        Assert.assertEquals(mfour(), s16);
        Assert.assertEquals(mfive(), s25);
    }
}
