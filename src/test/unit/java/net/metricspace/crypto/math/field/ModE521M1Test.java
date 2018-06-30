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

public class ModE521M1Test extends PrimeFieldUnitTest<ModE521M1> {
    private static final long[] TWO_DATA =
        new long[] { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWO_DATA =
        new long[] { 0x003ffffffffffffdL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] THREE_DATA =
        new long[] { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_THREE_DATA =
        new long[] { 0x003ffffffffffffcL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] FOUR_DATA =
        new long[] { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FOUR_DATA =
        new long[] { 0x003ffffffffffffbL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] FIVE_DATA =
        new long[] { 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_FIVE_DATA =
        new long[] { 0x003ffffffffffffaL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] SIX_DATA =
        new long[] { 6, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIX_DATA =
        new long[] { 0x003ffffffffffff9L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] SEVEN_DATA =
        new long[] { 7, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SEVEN_DATA =
        new long[] { 0x003ffffffffffff8L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] EIGHT_DATA =
        new long[] { 8, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_EIGHT_DATA =
        new long[] { 0x003ffffffffffff7L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] NINE_DATA =
        new long[] { 9, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_NINE_DATA =
        new long[] { 0x003ffffffffffff6L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] SIXTEEN_DATA =
        new long[] { 16, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_SIXTEEN_DATA =
        new long[] { 0x003fffffffffffefL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };
    private static final long[] TWENTY_FIVE_DATA =
        new long[] { 25, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private static final long[] M_TWENTY_FIVE_DATA =
        new long[] { 0x003fffffffffffe6L, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x003fffffffffffffL,
                     0x003fffffffffffffL, 0x00000007ffffffffL };

    private static ModE521M1 two() {
        return ModE521M1.create(TWO_DATA);
    }

    private static ModE521M1 mtwo() {
        return ModE521M1.create(M_TWO_DATA);
    }

    private static ModE521M1 three() {
        return ModE521M1.create(THREE_DATA);
    }

    private static ModE521M1 mthree() {
        return ModE521M1.create(M_THREE_DATA);
    }

    private static ModE521M1 four() {
        return ModE521M1.create(FOUR_DATA);
    }

    private static ModE521M1 mfour() {
        return ModE521M1.create(M_FOUR_DATA);
    }

    private static ModE521M1 five() {
        return ModE521M1.create(FIVE_DATA);
    }

    private static ModE521M1 mfive() {
        return ModE521M1.create(M_FIVE_DATA);
    }

    private static ModE521M1 six() {
        return ModE521M1.create(SIX_DATA);
    }

    private static ModE521M1 msix() {
        return ModE521M1.create(M_SIX_DATA);
    }

    private static ModE521M1 seven() {
        return ModE521M1.create(SEVEN_DATA);
    }

    private static ModE521M1 mseven() {
        return ModE521M1.create(M_SEVEN_DATA);
    }

    private static ModE521M1 eight() {
        return ModE521M1.create(EIGHT_DATA);
    }

    private static ModE521M1 meight() {
        return ModE521M1.create(M_EIGHT_DATA);
    }

    private static ModE521M1 nine() {
        return ModE521M1.create(NINE_DATA);
    }

    private static ModE521M1 mnine() {
        return ModE521M1.create(M_NINE_DATA);
    }

    private static ModE521M1 sixteen() {
        return ModE521M1.create(SIXTEEN_DATA);
    }

    private static ModE521M1 msixteen() {
        return ModE521M1.create(M_SIXTEEN_DATA);
    }

    private static ModE521M1 twentyFive() {
        return ModE521M1.create(TWENTY_FIVE_DATA);
    }

    private static ModE521M1 mtwentyFive() {
        return ModE521M1.create(M_TWENTY_FIVE_DATA);
    }

    @Override
    protected ModE521M1 unpack(final byte[] data) {
        return new ModE521M1(data);
    }

    @Override
    protected ModE521M1 unpackStream(final InputStream stream)
        throws IOException {
        return new ModE521M1(stream);
    }

    private static final Object[][] TEST_CONSTANTS_TEST_CASES = new Object[][] {
        new Object[] { new ModE521M1(0), ModE521M1.zero() },
        new Object[] { new ModE521M1(1), ModE521M1.one() },
        new Object[] { new ModE521M1(-1), ModE521M1.mone() },
        new Object[] { new ModE521M1(2), two() },
        new Object[] { new ModE521M1(-2), mtwo() },
        new Object[] { new ModE521M1(3), three() },
        new Object[] { new ModE521M1(-3), mthree() },
        new Object[] { new ModE521M1(4), four() },
        new Object[] { new ModE521M1(-4), mfour() },
        new Object[] { new ModE521M1(5), five() },
        new Object[] { new ModE521M1(-5), mfive() },
        new Object[] { new ModE521M1(6), six() },
        new Object[] { new ModE521M1(-6), msix() },
        new Object[] { new ModE521M1(7), seven() },
        new Object[] { new ModE521M1(-7), mseven() },
        new Object[] { new ModE521M1(8), eight() },
        new Object[] { new ModE521M1(-8), meight() },
        new Object[] { new ModE521M1(9), nine() },
        new Object[] { new ModE521M1(-9), mnine() },
        new Object[] { new ModE521M1(16), sixteen() },
        new Object[] { new ModE521M1(-16), msixteen() },
        new Object[] { new ModE521M1(25), twentyFive() },
        new Object[] { new ModE521M1(-25), mtwentyFive() }
    };

    @Override
    @DataProvider(name = "testConstants")
    public Object[][] testConstantsProvider() {
        return TEST_CONSTANTS_TEST_CASES;
    }

    private static final Object[][] SET_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x00000007ffffffffL })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0000000555555555L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x00000002aaaaaaaaL })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00000002aaaaaaaaL })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0000000555555555L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x00000007ffffffffL })
        }
    };

    @Override
    protected ModE521M1 createEmpty() {
        return new ModE521M1(0);
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
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                         (byte)0xff, (byte)0x00 }
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
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0xff, (byte)0x00, (byte)0xff,
                         (byte)0x00, (byte)0x01 }
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
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0xaa, (byte)0x55, (byte)0xaa,
                         (byte)0x55, (byte)0x00 }
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
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x55, (byte)0xaa, (byte)0x55,
                         (byte)0xaa, (byte)0x01 }
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
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0xaa, (byte)0x00, (byte)0xaa,
                         (byte)0x00, (byte)0x00 }
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
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00, (byte)0xaa, (byte)0x00,
                         (byte)0xaa, (byte)0x00 }
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
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0xff, (byte)0x55, (byte)0xff,
                         (byte)0x55, (byte)0x01 }
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
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x55, (byte)0xff, (byte)0x55,
                         (byte)0xff, (byte)0x01 }
        }
    };

    @Override
    @DataProvider(name = "unpackPack")
    public Object[][] unpackPackProvider() {
        return UNPACK_PACK_TEST_CASES;
    }

    private static final Object[][] PACK_UNPACK_TEST_CASES = new Object[][] {
        new Object[] {
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L,
                             0x003fffffffffffffL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x003fffffffffffffL,
                             0x0000000000000000L, 0x00000007ffffffffL })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0015555555555555L,
                             0x002aaaaaaaaaaaaaL, 0x0000000555555555L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x002aaaaaaaaaaaaaL,
                             0x0015555555555555L, 0x00000002aaaaaaaaL })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L,
                             0x002aaaaaaaaaaaaaL, 0x0000000000000000L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x002aaaaaaaaaaaaaL,
                             0x0000000000000000L, 0x00000002aaaaaaaaL })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0015555555555555L,
                             0x003fffffffffffffL, 0x0000000555555555L })
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x003fffffffffffffL,
                             0x0015555555555555L, 0x00000007ffffffffL })
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

    private static final ModE521M1[][] startTier = new ModE521M1[][] {
        new ModE521M1[] { ModE521M1.zero() },
        new ModE521M1[] { ModE521M1.one() },
        new ModE521M1[] { ModE521M1.mone() },
        new ModE521M1[] { two() },
        new ModE521M1[] { mtwo() },
        new ModE521M1[] { four() },
        new ModE521M1[] { mfour() }
    };

    @Test
    public void addTest() {
        final ModE521M1[][] tierOne = addTier(startTier);
        final ModE521M1[][] tierTwo = addTier(tierOne);
        //final ModE521M1[][] tierThree = addTier(tierTwo);
    }

    @Test
    public void subTest() {
        final ModE521M1[][] tierOne = subTier(startTier);
        final ModE521M1[][] tierTwo = subTier(tierOne);
        //final ModE521M1[][] tierThree = subTier(tierTwo);
    }

    @Test
    public void mulTest() {
        final ModE521M1[][] tierOne = mulTier(startTier);
        final ModE521M1[][] tierTwo = mulTier(tierOne);
        //final ModE521M1[][] tierThree = mulTier(tierTwo);
    }

    @Test
    public void divTest() {
        final ModE521M1[][] tierOne = divTier(startTier);
        final ModE521M1[][] tierTwo = divTier(tierOne);
        //final ModE521M1[][] tierThree = divTier(tierTwo);
    }

    private static final Object[][] SQUARE_TEST_CASES = new Object[][] {
        new Object[] { ModE521M1.zero(), ModE521M1.zero() },
        new Object[] { ModE521M1.one(), ModE521M1.one() },
        new Object[] { two(), four() },
        new Object[] { three(), nine() },
        new Object[] { four(), sixteen() },
        new Object[] { five(), twentyFive() },
        new Object[] { ModE521M1.mone(), ModE521M1.one() },
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
        new Object[] { ModE521M1.zero(), new Integer(0) },
        new Object[] { ModE521M1.one(), new Integer(1) },
        new Object[] { two(), new Integer(1) },
        new Object[] { three(), new Integer(-1) },
        new Object[] { four(), new Integer(1) },
        new Object[] { five(), new Integer(1) },
        new Object[] { six(), new Integer(-1) },
        new Object[] { seven(), new Integer(1) },
        new Object[] { eight(), new Integer(1) },
        new Object[] { nine(), new Integer(1) },
        new Object[] { sixteen(), new Integer(1) },
        new Object[] { twentyFive(), new Integer(1) },
        new Object[] { ModE521M1.mone(), new Integer(-1) },
        new Object[] { mtwo(), new Integer(-1) },
        new Object[] { mthree(), new Integer(1) },
        new Object[] { mfour(), new Integer(-1) },
        new Object[] { mfive(), new Integer(-1) },
        new Object[] { msix(), new Integer(1) },
        new Object[] { mseven(), new Integer(-1) },
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
        new Object[] { ModE521M1.zero(), ModE521M1.zero() },
        new Object[] { ModE521M1.one(), ModE521M1.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), five() },
    };

    @Override
    @DataProvider(name = "sqrt", parallel = true)
    public Object[][] sqrtProvider() {
        return SQRT_TEST_CASES;
    }

    private static final Object[][] INV_SQRT_TEST_CASES = new Object[][] {
        new Object[] { ModE521M1.zero(), ModE521M1.zero() },
        new Object[] { ModE521M1.one(), ModE521M1.one() },
        new Object[] { sixteen(), four() },
        new Object[] { twentyFive(), five() },
    };

    static {
        for(int i = 0; i < INV_SQRT_TEST_CASES.length; i++) {
            ((ModE521M1)INV_SQRT_TEST_CASES[i][1]).inv();
        }
    };

    @Override
    @DataProvider(name = "invSqrt", parallel = true)
    public Object[][] invSqrtProvider() {
        return INV_SQRT_TEST_CASES;
    }
    private static final Object[][] ABS_TEST_CASES = new Object[][] {
        new Object[] { ModE521M1.zero(), ModE521M1.zero() },
        new Object[] { ModE521M1.one(), ModE521M1.one() },
        new Object[] { ModE521M1.mone(), ModE521M1.one() },
        new Object[] { two(), two() },
        new Object[] { mtwo(), two() },
        new Object[] {
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x00000003ffffffffL }),
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x00000003ffffffffL }),
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000400000000L }),
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x00000003ffffffffL }),
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000001L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000400000000L }),
            new ModE521M1(
                new long[] { 0x003ffffffffffffeL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x00000003ffffffffL }),
        },
    };

    @DataProvider(name = "abs")
    public Object[][] absProvider() {
        return ABS_TEST_CASES;
    }

    private static final Object[][] SIGNUM_TEST_CASES = new Object[][] {
        new Object[] { ModE521M1.zero(), 1 },
        new Object[] { ModE521M1.one(), 1 },
        new Object[] { ModE521M1.mone(), -1 },
        new Object[] { two(), 1 },
        new Object[] { mtwo(), -1 },
        new Object[] {
            new ModE521M1(
                new long[] { 0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x003fffffffffffffL,
                             0x003fffffffffffffL, 0x00000003ffffffffL }),
            1
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000400000000L }),
            -1
        },
        new Object[] {
            new ModE521M1(
                new long[] { 0x0000000000000001L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000000000000L,
                             0x0000000000000000L, 0x0000000400000000L }),
            -1
        },
    };

    @DataProvider(name = "signum")
    public Object[][] signumProvider() {
        return SIGNUM_TEST_CASES;
    }
}
