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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Utilities for writing tests against {@code PrimeField} instances.
 */
@Test(groups = "unit")
public abstract class PrimeFieldUnitTest<P extends PrimeField<P>> {
    public static final int ZERO_IDX = 0;
    public static final int ONE_IDX = 1;
    public static final int MONE_IDX = 2;
    public static final int TWO_IDX = 3;
    public static final int MTWO_IDX = 4;
    public static final int FOUR_IDX = 5;
    public static final int MFOUR_IDX = 6;

    @DataProvider(name = "testConstants")
    public abstract Object[][] testConstantsProvider();

    @Test(dataProvider = "testConstants",
          description = "Test value of constants against " +
          "creation from small numbers")
    public void testConstants(final P expected,
                              final P actual) {
        Assert.assertEquals(expected, actual);
    }

    protected abstract P createEmpty();

    @DataProvider(name = "setEmpty")
    public abstract Object[][] setEmptyProvider();

    @Test(dataProvider = "setEmpty",
          description = "Test set functionality")
    public void setEmptyTest(final P expected) {
        final P actual = createEmpty();

        actual.set(expected);

        Assert.assertEquals(actual, expected);
    }

    protected abstract P unpack(final byte[] data);

    protected abstract P unpackStream(final InputStream stream)
        throws IOException;

    @DataProvider(name = "abs")
    public abstract Object[][] absProvider();

    @Test(dataProvider = "abs",
          description = "Test abs functionality")
    public void absTest(final P val,
                        final P expected) {
        final P absval = val.clone();

        absval.abs();

        Assert.assertEquals(absval, expected);
    }

    @DataProvider(name = "signum")
    public abstract Object[][] signumProvider();

    @Test(dataProvider = "signum",
          description = "Test signum and sign functionality")
    public void absTest(final P val,
                        final Integer expected) {
        final int signum = val.signum();
        final int sign = val.sign();
        final int expectedsign = expected == 1 ? 0 : 1;

        Assert.assertEquals(sign, expectedsign);
        Assert.assertEquals(signum, expected.intValue());
    }

    @DataProvider(name = "mask")
    public abstract Object[][] maskProvider();

    @Test(dataProvider = "mask",
          description = "Test mask functionality")
    public void maskTest(final P val) {
        final P onemask = val.clone();
        final P zeromask = val.clone();

        onemask.mask(1);
        zeromask.mask(0);

        Assert.assertEquals(zeromask.isZero(), 1);
        Assert.assertEquals(val, onemask);
    }

    @DataProvider(name = "or")
    public abstract Object[][] orProvider();

    @Test(dataProvider = "or",
          description = "Test mask functionality")
    public void orLeftTest(final P a, final P b) {
        final P onemask = a.clone();
        final P zeromask = b.clone();

        onemask.mask(1);
        zeromask.mask(0);
        onemask.or(zeromask);

        Assert.assertEquals(a, onemask);
    }

    @Test(dataProvider = "or",
          description = "Test mask functionality")
    public void orRightTest(final P a, final P b) {
        final P zeromask = a.clone();
        final P onemask = b.clone();

        zeromask.mask(0);
        onemask.mask(1);
        zeromask.or(onemask);

        Assert.assertEquals(b, zeromask);
    }

    @DataProvider(name = "unpackPack")
    public abstract Object[][] unpackPackProvider();

    @Test(dataProvider = "unpackPack",
          description = "Test unpacking then packing values")
    public void unpackPackTest(final byte[] testcase) {
        final P unpacked = unpack(testcase);
        final byte[] packed = unpacked.packed();

        Assert.assertEquals(packed.length, testcase.length);

        for(int i = 0; i < testcase.length; i++) {
            Assert.assertEquals(packed[i], testcase[i]);
        }

        final byte[] target = new byte[packed.length + 2];

        for(int i = 0; i < target.length; i++) {
            target[i] = (byte)0xff;
        }

        unpacked.pack(target);

        for(int i = 0; i < testcase.length; i++) {
            Assert.assertEquals(target[i], testcase[i]);
        }

        Assert.assertEquals(target[testcase.length + 0], (byte)0xff);
        Assert.assertEquals(target[testcase.length + 1], (byte)0xff);

        for(int i = 0; i < target.length; i++) {
            target[i] = (byte)0xff;
        }

        unpacked.pack(target, 1);

        Assert.assertEquals(target[0], (byte)0xff);

        for(int i = 0; i < testcase.length; i++) {
            Assert.assertEquals(target[i + 1], testcase[i]);
        }

        Assert.assertEquals(target[testcase.length + 1], (byte)0xff);
    }

    @Test(dataProvider = "unpackPack",
          description = "Test unpacking from a stream")
    public void unpackStreamTest(final byte[] testcase)
        throws IOException {
        final P unpacked = unpack(testcase);
        final InputStream stream = new ByteArrayInputStream(testcase);
        final P unpackedStream = unpackStream(stream);

        Assert.assertEquals(unpackedStream, unpacked);
    }

    @Test(dataProvider = "unpackPack",
          description = "Test bit values")
    public void bitTest(final byte[] testcase)
        throws IOException {
        final P unpacked = unpack(testcase);

        for(int i = 0; i < unpacked.numBits(); i++) {
            final int byteidx = i / 8;
            final int bitidx = i % 8;
            final int expected = (testcase[byteidx] >> bitidx) & 0x1;

            Assert.assertEquals(unpacked.bit(i), expected);
        }
    }

    @DataProvider(name = "packUnpack")
    public abstract Object[][] packUnpackProvider();

    @Test(dataProvider = "packUnpack",
          description = "Test packing then unpacking values")
    public void packUnpackTestCase(final P expected) {
        final byte[] packed = expected.packed();
        final P actual = unpack(packed);

        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "packUnpack",
          description = "Test packing to a stream")
    public void packStreamTestCase(final P expected)
        throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();

        expected.pack(stream);

        Assert.assertEquals(stream.toByteArray(), expected.packed());
    }

    @DataProvider(name = "square")
    public abstract Object[][] squareProvider();

    @Test(dataProvider = "square",
          description = "Test square")
    public void squareTest(final P a,
                           final P expected) {
        final P actual = a.clone();

        actual.square();

        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "legendre", parallel = true)
    public abstract Object[][] legendreProvider();

    @Test(dataProvider = "legendre",
          description = "Test legendre symbol")
    public void legendreTest(final P a,
                             final int expected) {
        Assert.assertEquals(a.legendre(), expected);
    }

    @DataProvider(name = "sqrt", parallel = true)
    public abstract Object[][] sqrtProvider();

    @Test(dataProvider = "sqrt",
          description = "Test sqrt")
    public void sqrtTest(final P a,
                         final P expected) {
        final P actual = a.clone();

        actual.sqrt();

        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "invSqrt", parallel = true)
    public abstract Object[][] invSqrtProvider();

    @Test(dataProvider = "invSqrt",
          description = "Test inverse sqrt")
    public void invSqrtTest(final P a,
                            final P expected) {
        final P actual = a.clone();

        actual.invSqrt();

        Assert.assertEquals(actual, expected);
    }

    public P[][] addTier(final P[][] vals) {
        final P[][] out = Arrays.copyOf(vals, vals.length);
        final int nzeros =
            (vals[ZERO_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[MONE_IDX].length) +
            (vals[MONE_IDX].length * vals[ONE_IDX].length) +
            (vals[TWO_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[TWO_IDX].length) +
            (vals[FOUR_IDX].length * vals[MFOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[FOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[ONE_IDX].length +
            vals[MONE_IDX].length + vals[TWO_IDX].length +
            vals[MTWO_IDX].length + vals[FOUR_IDX].length +
            vals[MFOUR_IDX].length;
        final int nones =
            (vals[ZERO_IDX].length * vals[ONE_IDX].length) +
            (vals[ONE_IDX].length * vals[ZERO_IDX].length) +
            (vals[MONE_IDX].length * vals[TWO_IDX].length) +
            (vals[TWO_IDX].length * vals[MONE_IDX].length) +
            vals[ZERO_IDX].length + vals[ONE_IDX].length +
            vals[MONE_IDX].length + vals[TWO_IDX].length;
        final int nmones =
            (vals[ZERO_IDX].length * vals[MONE_IDX].length) +
            (vals[MONE_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[ONE_IDX].length) +
            vals[ZERO_IDX].length + vals[MONE_IDX].length +
            vals[ONE_IDX].length + vals[MTWO_IDX].length;
        final int ntwos =
            (vals[ZERO_IDX].length * vals[TWO_IDX].length) +
            (vals[TWO_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[ONE_IDX].length) +
            (vals[FOUR_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[FOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[TWO_IDX].length +
            vals[ONE_IDX].length + vals[FOUR_IDX].length +
            vals[MTWO_IDX].length;
        final int nmtwos =
            (vals[ZERO_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[ZERO_IDX].length) +
            (vals[MONE_IDX].length * vals[MONE_IDX].length) +
            (vals[FOUR_IDX].length * vals[TWO_IDX].length) +
            (vals[TWO_IDX].length * vals[FOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[MTWO_IDX].length +
            vals[MONE_IDX].length + vals[FOUR_IDX].length +
            vals[TWO_IDX].length;
        final int nfours =
            (vals[ZERO_IDX].length * vals[FOUR_IDX].length) +
            (vals[FOUR_IDX].length * vals[ZERO_IDX].length) +
            (vals[TWO_IDX].length * vals[TWO_IDX].length) +
            vals[ZERO_IDX].length + vals[FOUR_IDX].length +
            vals[TWO_IDX].length;
        final int nmfours =
            (vals[ZERO_IDX].length * vals[MFOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[ZERO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MTWO_IDX].length) +
            vals[ZERO_IDX].length + vals[MFOUR_IDX].length +
            vals[MTWO_IDX].length;
        int idx;

        out[ZERO_IDX] = Arrays.copyOf(vals[ZERO_IDX], nzeros);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.add(vals[MONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.add(vals[ONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.add(vals[MTWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.add(vals[TWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.add(vals[MFOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.add(vals[FOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }


        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.add(-1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.add(1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.add(-2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.add(2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.add(-4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.add(4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }


        out[ONE_IDX] = Arrays.copyOf(vals[ONE_IDX], nones);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[ONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.add(vals[TWO_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.add(vals[MONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.add(0);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.add(2);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.add(-1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        out[MONE_IDX] = Arrays.copyOf(vals[MONE_IDX], nmones);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[MONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.add(vals[MTWO_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.add(vals[ONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(-1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.add(0);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.add(-2);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.add(1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        out[TWO_IDX] = Arrays.copyOf(vals[TWO_IDX], ntwos);

        idx = 0;
        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[TWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.add(vals[ONE_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.add(vals[FOUR_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.add(vals[MTWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }


        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.add(0);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.add(1);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.add(4);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.add(-2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        out[MTWO_IDX] = Arrays.copyOf(vals[MTWO_IDX], nmtwos);

        idx = 0;
        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[MTWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.add(vals[MONE_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.add(vals[MFOUR_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.add(vals[TWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }


        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.add(0);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(-2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.add(-1);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.add(-4);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.add(2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        out[FOUR_IDX] = Arrays.copyOf(vals[FOUR_IDX], nfours);

        idx = 0;
        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[FOUR_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.add(vals[TWO_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.add(0);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(4);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.add(2);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        out[MFOUR_IDX] = Arrays.copyOf(vals[MFOUR_IDX], nmfours);

        idx = 0;
        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.add(vals[ZERO_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.add(vals[MFOUR_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.add(vals[MTWO_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.add(0);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.add(-4);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.add(-2);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        return out;
    }

    public P[][] subTier(final P[][] vals) {
        final P[][] out = Arrays.copyOf(vals, vals.length);
        final int nzeros =
            (vals[ZERO_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[ONE_IDX].length) +
            (vals[MONE_IDX].length * vals[MONE_IDX].length) +
            (vals[TWO_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MTWO_IDX].length) +
            (vals[FOUR_IDX].length * vals[FOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[MFOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[ONE_IDX].length +
            vals[MONE_IDX].length + vals[TWO_IDX].length +
            vals[MTWO_IDX].length + vals[FOUR_IDX].length +
            vals[MFOUR_IDX].length;
        final int nones =
            (vals[ZERO_IDX].length * vals[MONE_IDX].length) +
            (vals[ONE_IDX].length * vals[ZERO_IDX].length) +
            (vals[MONE_IDX].length * vals[MTWO_IDX].length) +
            (vals[TWO_IDX].length * vals[ONE_IDX].length) +
            vals[ZERO_IDX].length + vals[ONE_IDX].length +
            vals[MONE_IDX].length + vals[TWO_IDX].length;
        final int nmones =
            (vals[ZERO_IDX].length * vals[ONE_IDX].length) +
            (vals[MONE_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MONE_IDX].length) +
            vals[ZERO_IDX].length + vals[MONE_IDX].length +
            vals[ONE_IDX].length + vals[MTWO_IDX].length;
        final int ntwos =
            (vals[ZERO_IDX].length * vals[MTWO_IDX].length) +
            (vals[TWO_IDX].length * vals[ZERO_IDX].length) +
            (vals[MONE_IDX].length * vals[MONE_IDX].length) +
            (vals[FOUR_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MFOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[TWO_IDX].length +
            vals[MONE_IDX].length + vals[FOUR_IDX].length +
            vals[MTWO_IDX].length;
        final int nmtwos =
            (vals[ZERO_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[ONE_IDX].length) +
            (vals[MFOUR_IDX].length * vals[MTWO_IDX].length) +
            (vals[TWO_IDX].length * vals[FOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[MTWO_IDX].length +
            vals[ONE_IDX].length + vals[MFOUR_IDX].length +
            vals[TWO_IDX].length;
        final int nfours =
            (vals[ZERO_IDX].length * vals[FOUR_IDX].length) +
            (vals[FOUR_IDX].length * vals[ZERO_IDX].length) +
            (vals[TWO_IDX].length * vals[MTWO_IDX].length) +
            vals[ZERO_IDX].length + vals[FOUR_IDX].length +
            vals[TWO_IDX].length;
        final int nmfours =
            (vals[ZERO_IDX].length * vals[MFOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[ZERO_IDX].length) +
            (vals[MTWO_IDX].length * vals[TWO_IDX].length) +
            vals[ZERO_IDX].length + vals[MFOUR_IDX].length +
            vals[MTWO_IDX].length;
        int idx;

        out[ZERO_IDX] = Arrays.copyOf(vals[ZERO_IDX], nzeros);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.sub(vals[ONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.sub(vals[MONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.sub(vals[TWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.sub(vals[MTWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.sub(vals[FOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.sub(vals[MFOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.sub(1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.sub(-1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.sub(2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.sub(-2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.sub(4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.sub(-4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        out[ONE_IDX] = Arrays.copyOf(vals[ONE_IDX], nones);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[MONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.sub(vals[MTWO_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.sub(vals[ONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(-1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.sub(0);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.sub(-2);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.sub(1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        out[MONE_IDX] = Arrays.copyOf(vals[MONE_IDX], nmones);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[ONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.sub(vals[TWO_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.sub(vals[MONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.sub(0);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.sub(2);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.sub(-1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        out[TWO_IDX] = Arrays.copyOf(vals[TWO_IDX], ntwos);

        idx = 0;
        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[MTWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.sub(vals[MONE_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.sub(vals[MFOUR_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.sub(vals[TWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.sub(0);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(-2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.sub(-1);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.sub(-4);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.sub(2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        out[MTWO_IDX] = Arrays.copyOf(vals[MTWO_IDX], nmtwos);

        idx = 0;
        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[TWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.sub(vals[ONE_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.sub(vals[FOUR_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.sub(vals[MTWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.sub(0);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.sub(1);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.sub(4);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.sub(-2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        out[FOUR_IDX] = Arrays.copyOf(vals[FOUR_IDX], nfours);

        idx = 0;
        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[MFOUR_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.sub(vals[MTWO_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.sub(0);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(-4);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.sub(-2);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        out[MFOUR_IDX] = Arrays.copyOf(vals[MFOUR_IDX], nmfours);

        idx = 0;
        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.sub(vals[ZERO_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.sub(vals[FOUR_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.sub(vals[TWO_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.sub(0);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.sub(4);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.sub(2);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        return out;
    }

    public P[][] mulTier(final P[][] vals) {
        final P[][] out = Arrays.copyOf(vals, vals.length);
        final int nzeros =
            (vals[ZERO_IDX].length * vals[ZERO_IDX].length) +
            (vals[ONE_IDX].length * vals[ZERO_IDX].length) +
            (vals[MONE_IDX].length * vals[ZERO_IDX].length) +
            (vals[TWO_IDX].length * vals[ZERO_IDX].length) +
            (vals[MTWO_IDX].length * vals[ZERO_IDX].length) +
            (vals[FOUR_IDX].length * vals[ZERO_IDX].length) +
            (vals[MFOUR_IDX].length * vals[ZERO_IDX].length) +
            (vals[ZERO_IDX].length * vals[ONE_IDX].length) +
            (vals[ZERO_IDX].length * vals[MONE_IDX].length) +
            (vals[ZERO_IDX].length * vals[TWO_IDX].length) +
            (vals[ZERO_IDX].length * vals[MTWO_IDX].length) +
            (vals[ZERO_IDX].length * vals[FOUR_IDX].length) +
            (vals[ZERO_IDX].length * vals[MFOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[ONE_IDX].length +
            vals[MONE_IDX].length + vals[TWO_IDX].length +
            vals[MTWO_IDX].length + vals[FOUR_IDX].length +
            vals[MFOUR_IDX].length + vals[ZERO_IDX].length +
            vals[ZERO_IDX].length + vals[ZERO_IDX].length +
            vals[ZERO_IDX].length + vals[ZERO_IDX].length +
            vals[ZERO_IDX].length;
        final int nones =
            (vals[ONE_IDX].length * vals[ONE_IDX].length) +
            (vals[MONE_IDX].length * vals[MONE_IDX].length) +
            vals[ONE_IDX].length + vals[MONE_IDX].length;
        final int nmones =
            (vals[ONE_IDX].length * vals[MONE_IDX].length) +
            (vals[MONE_IDX].length * vals[ONE_IDX].length) +
            vals[ONE_IDX].length + vals[MONE_IDX].length;
        final int ntwos =
            (vals[ONE_IDX].length * vals[TWO_IDX].length) +
            (vals[TWO_IDX].length * vals[ONE_IDX].length) +
            (vals[MONE_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MONE_IDX].length) +
            vals[ONE_IDX].length + vals[TWO_IDX].length +
            vals[MONE_IDX].length + vals[MTWO_IDX].length;
        final int nmtwos =
            (vals[ONE_IDX].length * vals[MTWO_IDX].length) +
            (vals[TWO_IDX].length * vals[MONE_IDX].length) +
            (vals[MONE_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[ONE_IDX].length) +
            vals[ONE_IDX].length + vals[TWO_IDX].length +
            vals[MONE_IDX].length + vals[MTWO_IDX].length;
        final int nfours =
            (vals[ONE_IDX].length * vals[FOUR_IDX].length) +
            (vals[FOUR_IDX].length * vals[ONE_IDX].length) +
            (vals[MONE_IDX].length * vals[MFOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[MONE_IDX].length) +
            (vals[TWO_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MTWO_IDX].length) +
            vals[ONE_IDX].length + vals[FOUR_IDX].length +
            vals[MONE_IDX].length + vals[MFOUR_IDX].length +
            vals[TWO_IDX].length + vals[MTWO_IDX].length;
        final int nmfours =
            (vals[ONE_IDX].length * vals[MFOUR_IDX].length) +
            (vals[FOUR_IDX].length * vals[MONE_IDX].length) +
            (vals[MONE_IDX].length * vals[FOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[ONE_IDX].length) +
            (vals[TWO_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[TWO_IDX].length) +
            vals[ONE_IDX].length + vals[FOUR_IDX].length +
            vals[MONE_IDX].length + vals[MFOUR_IDX].length +
            vals[TWO_IDX].length + vals[MTWO_IDX].length;
        int idx;

        out[ZERO_IDX] = Arrays.copyOf(vals[ZERO_IDX], nzeros);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ZERO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.mul(vals[ZERO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[TWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[MTWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[FOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.mul(vals[MFOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.mul((short)0);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)-1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)-2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.mul((short)-4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        out[ONE_IDX] = Arrays.copyOf(vals[ONE_IDX], nones);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul(-1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        out[MONE_IDX] = Arrays.copyOf(vals[MONE_IDX], nmones);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)-1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul((short)1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        out[TWO_IDX] = Arrays.copyOf(vals[TWO_IDX], ntwos);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[TWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[MTWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.mul((short)1);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul((short)-2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.mul((short)-1);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        out[MTWO_IDX] = Arrays.copyOf(vals[MTWO_IDX], nmtwos);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[MTWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[TWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)-2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.mul((short)-1);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul((short)2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.mul((short)1);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        out[FOUR_IDX] = Arrays.copyOf(vals[FOUR_IDX], nfours);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[FOUR_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[MFOUR_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.mul(vals[TWO_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.mul(vals[MTWO_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }


        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)4);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.mul((short)1);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul((short)-4);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.mul((short)-1);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.mul((short)2);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.mul((short)-2);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        out[MFOUR_IDX] = Arrays.copyOf(vals[MFOUR_IDX], nmfours);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.mul(vals[MFOUR_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.mul(vals[MONE_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.mul(vals[FOUR_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.mul(vals[ONE_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.mul(vals[MTWO_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.mul(vals[TWO_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.mul((short)-4);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.mul((short)-1);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.mul((short)4);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.mul((short)1);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.mul((short)-2);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.mul((short)2);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        return out;
    }

    public P[][] divTier(final P[][] vals) {
        final P[][] out = Arrays.copyOf(vals, vals.length);
        final int nzeros =
            (vals[ZERO_IDX].length * vals[ONE_IDX].length) +
            (vals[ZERO_IDX].length * vals[MONE_IDX].length) +
            (vals[ZERO_IDX].length * vals[TWO_IDX].length) +
            (vals[ZERO_IDX].length * vals[MTWO_IDX].length) +
            (vals[ZERO_IDX].length * vals[FOUR_IDX].length) +
            (vals[ZERO_IDX].length * vals[MFOUR_IDX].length) +
            vals[ZERO_IDX].length + vals[ZERO_IDX].length +
            vals[ZERO_IDX].length + vals[ZERO_IDX].length +
            vals[ZERO_IDX].length + vals[ZERO_IDX].length;
        final int nones =
            (vals[ONE_IDX].length * vals[ONE_IDX].length) +
            (vals[MONE_IDX].length * vals[MONE_IDX].length) +
            (vals[TWO_IDX].length * vals[TWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[MTWO_IDX].length) +
            (vals[FOUR_IDX].length * vals[FOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[MFOUR_IDX].length) +
            vals[ONE_IDX].length + vals[MONE_IDX].length +
            vals[TWO_IDX].length + vals[MTWO_IDX].length +
            vals[FOUR_IDX].length + vals[MFOUR_IDX].length;
        final int nmones =
            (vals[ONE_IDX].length * vals[MONE_IDX].length) +
            (vals[MONE_IDX].length * vals[ONE_IDX].length) +
            (vals[TWO_IDX].length * vals[MTWO_IDX].length) +
            (vals[MTWO_IDX].length * vals[TWO_IDX].length) +
            (vals[FOUR_IDX].length * vals[MFOUR_IDX].length) +
            (vals[MFOUR_IDX].length * vals[FOUR_IDX].length) +
            vals[ONE_IDX].length + vals[MONE_IDX].length +
            vals[TWO_IDX].length + vals[MTWO_IDX].length +
            vals[FOUR_IDX].length + vals[MFOUR_IDX].length;
        final int ntwos =
            (vals[TWO_IDX].length * vals[ONE_IDX].length) +
            (vals[MTWO_IDX].length * vals[MONE_IDX].length) +
            (vals[FOUR_IDX].length * vals[TWO_IDX].length) +
            (vals[MFOUR_IDX].length * vals[MTWO_IDX].length) +
            vals[TWO_IDX].length + vals[MTWO_IDX].length +
            vals[FOUR_IDX].length + vals[MFOUR_IDX].length;
        final int nmtwos =
            (vals[TWO_IDX].length * vals[MONE_IDX].length) +
            (vals[MTWO_IDX].length * vals[ONE_IDX].length) +
            (vals[FOUR_IDX].length * vals[MTWO_IDX].length) +
            (vals[MFOUR_IDX].length * vals[TWO_IDX].length) +
            vals[TWO_IDX].length + vals[MTWO_IDX].length +
            vals[FOUR_IDX].length + vals[MFOUR_IDX].length;
        final int nfours =
            (vals[FOUR_IDX].length * vals[ONE_IDX].length) +
            (vals[MFOUR_IDX].length * vals[MONE_IDX].length) +
            vals[FOUR_IDX].length + vals[MFOUR_IDX].length;
        final int nmfours =
            (vals[FOUR_IDX].length * vals[MONE_IDX].length) +
            (vals[MFOUR_IDX].length * vals[ONE_IDX].length) +
            vals[FOUR_IDX].length + vals[MFOUR_IDX].length;
        int idx;

        out[ZERO_IDX] = Arrays.copyOf(vals[ZERO_IDX], nzeros);

        idx = 0;
        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.div(vals[TWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.div(vals[MTWO_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.div(vals[FOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[ZERO_IDX][i].clone();

                val.div(vals[MFOUR_IDX][j]);

                out[ZERO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ZERO_IDX][0]);
            }
        }


        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.div((short)1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.div((short)-1);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.div((short)2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.div((short)-2);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.div((short)4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        for(int i = 0; i < vals[ZERO_IDX].length; i++) {
            final P val = vals[ZERO_IDX][i].clone();

            val.div((short)-4);

            out[ZERO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ZERO_IDX][0]);
        }

        out[ONE_IDX] = Arrays.copyOf(vals[ONE_IDX], nones);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.div(vals[TWO_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.div(vals[MTWO_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.div(vals[FOUR_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.div(vals[MFOUR_IDX][j]);

                out[ONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[ONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.div((short)1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.div((short)-1);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.div((short)2);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.div((short)-2);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.div((short)4);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.div((short)-4);

            out[ONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[ONE_IDX][0]);
        }

        out[MONE_IDX] = Arrays.copyOf(vals[MONE_IDX], nmones);

        idx = 0;
        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[ONE_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MONE_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.div(vals[MTWO_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.div(vals[TWO_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MFOUR_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.div(vals[MFOUR_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[FOUR_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.div(vals[FOUR_IDX][j]);

                out[MONE_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MONE_IDX][0]);
            }
        }

        for(int i = 0; i < vals[ONE_IDX].length; i++) {
            final P val = vals[ONE_IDX][i].clone();

            val.div((short)-1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MONE_IDX].length; i++) {
            final P val = vals[MONE_IDX][i].clone();

            val.div((short)1);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.div((short)-2);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.div((short)2);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.div((short)-4);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.div((short)4);

            out[MONE_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MONE_IDX][0]);
        }

        out[TWO_IDX] = Arrays.copyOf(vals[TWO_IDX], ntwos);

        idx = 0;
        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.div(vals[TWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.div(vals[MTWO_IDX][j]);

                out[TWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[TWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.div((short)1);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.div((short)-1);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.div((short)2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.div((short)-2);

            out[TWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[TWO_IDX][0]);
        }

        out[MTWO_IDX] = Arrays.copyOf(vals[MTWO_IDX], nmtwos);

        idx = 0;
        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[TWO_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MTWO_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MTWO_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.div(vals[MTWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[TWO_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.div(vals[TWO_IDX][j]);

                out[MTWO_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MTWO_IDX][0]);
            }
        }


        for(int i = 0; i < vals[TWO_IDX].length; i++) {
            final P val = vals[TWO_IDX][i].clone();

            val.div((short)-1);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MTWO_IDX].length; i++) {
            final P val = vals[MTWO_IDX][i].clone();

            val.div((short)1);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.div((short)-2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.div((short)2);

            out[MTWO_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MTWO_IDX][0]);
        }

        out[FOUR_IDX] = Arrays.copyOf(vals[FOUR_IDX], nfours);

        idx = 0;
        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[FOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[FOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.div((short)1);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.div((short)-1);

            out[FOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[FOUR_IDX][0]);
        }

        out[MFOUR_IDX] = Arrays.copyOf(vals[MFOUR_IDX], nmfours);

        idx = 0;
        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            for(int j = 0; j < vals[MONE_IDX].length; j++) {
                final P val = vals[FOUR_IDX][i].clone();

                val.div(vals[MONE_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            for(int j = 0; j < vals[ONE_IDX].length; j++) {
                final P val = vals[MFOUR_IDX][i].clone();

                val.div(vals[ONE_IDX][j]);

                out[MFOUR_IDX][idx++] = val;
                Assert.assertEquals(val, vals[MFOUR_IDX][0]);
            }
        }

        for(int i = 0; i < vals[FOUR_IDX].length; i++) {
            final P val = vals[FOUR_IDX][i].clone();

            val.div((short)-1);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        for(int i = 0; i < vals[MFOUR_IDX].length; i++) {
            final P val = vals[MFOUR_IDX][i].clone();

            val.div((short)1);

            out[MFOUR_IDX][idx++] = val;
            Assert.assertEquals(val, vals[MFOUR_IDX][0]);
        }

        return out;
    }
}
