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
package net.metricspace.crypto.math.field.perf;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import net.metricspace.crypto.math.field.PrimeField;

@State(Scope.Benchmark)
public abstract class PrimeFieldPerf<P extends PrimeField<P>> {
    public static enum DataKind {
        ZERO, ONE, MONE, ASCENDING, DESCENDING, HALF_HI, HALF_LO,
        HALF_SMALL, RANDOM_1, RANDOM_2, RANDOM_3, RANDOM_4
    }

    protected final EnumMap<DataKind, P> params;
    protected final EnumMap<DataKind, BigInteger> bigintParams;
    protected final BigInteger modulus;

    protected PrimeFieldPerf(final EnumMap<DataKind, P> params,
                             final EnumMap<DataKind, BigInteger> bigintParams,
                             final BigInteger modulus) {
        this.params = params;
        this.bigintParams = bigintParams;
        this.modulus = modulus;
    }

    @State(Scope.Benchmark)
    public static class TwoArgs {
        @Param({ "ZERO", "ONE", "MONE", "ASCENDING", "DESCENDING", "HALF_HI",
                 "HALF_LO", "HALF_SMALL", "RANDOM_1", "RANDOM_2",
                 "RANDOM_3", "RANDOM_4" })
        public DataKind a;

        @Param({ "ZERO", "ONE", "MONE", "ASCENDING", "DESCENDING", "HALF_HI",
                 "HALF_LO", "HALF_SMALL", "RANDOM_1", "RANDOM_2",
                 "RANDOM_3", "RANDOM_4" })
        public DataKind b;
    }

    @State(Scope.Benchmark)
    public static class IntArg {
        @Param({ "ZERO", "ONE", "MONE", "ASCENDING", "DESCENDING", "HALF_HI",
                 "HALF_LO", "HALF_SMALL", "RANDOM_1", "RANDOM_2",
                 "RANDOM_3", "RANDOM_4" })
        public DataKind a;

        @Param({ "0", "1", "2", "-1", "-2", "-252645136", "-858993460",
                 "-16711936", "1431655765", "-65536", "65535" })
        public int b;
    }

    @State(Scope.Benchmark)
    public static class ShortArg {
        @Param({ "ZERO", "ONE", "MONE", "ASCENDING", "DESCENDING", "HALF_HI",
                 "HALF_LO", "HALF_SMALL", "RANDOM_1", "RANDOM_2",
                 "RANDOM_3", "RANDOM_4" })
        public DataKind a;

        @Param({ "0", "1", "2", "-1", "-2", "0xf0f0", "0xcccc",
                 "0x5555", "0xff00", "0x00ff" })
        public short b;
    }

    @State(Scope.Benchmark)
    public static class OneArg {
        @Param({ "ZERO", "ONE", "MONE", "ASCENDING", "DESCENDING", "HALF_HI",
                 "HALF_LO", "HALF_SMALL", "RANDOM_1", "RANDOM_2",
                 "RANDOM_3", "RANDOM_4" })
        public DataKind a;
    }

    @Benchmark
    public void addTest(final TwoArgs args) {
        params.get(args.a).add(params.get(args.b));
    }

    @Benchmark
    public void addNormalizeTest(final TwoArgs args) {
        final P val = params.get(args.a);

        val.add(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void smallAddTest(final IntArg args) {
        params.get(args.a).add(params.get(args.b));
    }

    @Benchmark
    public void smallAddNormalizeTest(final IntArg args) {
        final P val = params.get(args.a);

        val.add(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void subTest(final TwoArgs args) {
        params.get(args.a).sub(params.get(args.b));
    }

    @Benchmark
    public void subNormalizeTest(final TwoArgs args) {
        final P val = params.get(args.a);

        val.sub(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void smallSubTest(final IntArg args) {
        params.get(args.a).sub(params.get(args.b));
    }

    @Benchmark
    public void smallSubNormalizeTest(final IntArg args) {
        final P val = params.get(args.a);

        val.sub(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void mulTest(final TwoArgs args) {
        params.get(args.a).mul(params.get(args.b));
    }

    @Benchmark
    public void mulNormalizeTest(final TwoArgs args) {
        final P val = params.get(args.a);

        val.mul(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void smallMulTest(final IntArg args) {
        params.get(args.a).mul(params.get(args.b));
    }

    @Benchmark
    public void smallMulNormalizeTest(final IntArg args) {
        final P val = params.get(args.a);

        val.mul(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void divTest(final TwoArgs args) {
        params.get(args.a).div(params.get(args.b));
    }

    @Benchmark
    public void divNormalizeTest(final TwoArgs args) {
        final P val = params.get(args.a);

        val.div(params.get(args.b));
        val.normalize();
    }

    @Benchmark
    public void addBigIntTest(final Blackhole bh,
                              final TwoArgs args) {
        bh.consume(bigintParams.get(args.a)
                   .add(bigintParams.get(args.b))
                   .mod(modulus));
    }

    @Benchmark
    public void subBigIntTest(final Blackhole bh,
                              final TwoArgs args) {
        bh.consume(bigintParams.get(args.a)
                   .subtract(bigintParams.get(args.b))
                   .mod(modulus));
    }

    @Benchmark
    public void mulBigIntTest(final Blackhole bh,
                              final TwoArgs args) {
        bh.consume(bigintParams.get(args.a)
                   .multiply(bigintParams.get(args.b))
                   .mod(modulus));
    }

    @Benchmark
    public void divBigIntTest(final Blackhole bh,
                              final TwoArgs args) {
        bh.consume(bigintParams.get(args.a)
                   .divide(bigintParams.get(args.b))
                   .mod(modulus));
    }

    public static byte[] swapByteOrder(final byte[] data) {
        final byte[] out = new byte[data.length];

        for(int i = 0; i < data.length; i++) {
            out[data.length - i - 1] = data[i];
        }

        return out;
    }
}
