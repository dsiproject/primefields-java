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

import net.metricspace.crypto.math.field.ModE221M3;

public class ModE221M3Perf extends PrimeField1Mod4Perf<ModE221M3> {
    private static Random random = new Random();

    private static final byte[] ASCENDING_DATA =
        new byte[] { (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
                     (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07,
                     (byte)0x08, (byte)0x09, (byte)0x0a, (byte)0x0b,
                     (byte)0x0c, (byte)0x0d, (byte)0x0e, (byte)0x0f,
                     (byte)0x10, (byte)0x11, (byte)0x12, (byte)0x13,
                     (byte)0x14, (byte)0x15, (byte)0x16, (byte)0x17,
                     (byte)0x18, (byte)0x19, (byte)0x1a, (byte)0x1b };
    private static final byte[] DESCENDING_DATA =
        new byte[] { (byte)0x1b, (byte)0x1a, (byte)0x19, (byte)0x18,
                     (byte)0x17, (byte)0x16, (byte)0x15, (byte)0x14,
                     (byte)0x13, (byte)0x12, (byte)0x11, (byte)0x10,
                     (byte)0x0f, (byte)0x0e, (byte)0x0d, (byte)0x0c,
                     (byte)0x0b, (byte)0x0a, (byte)0x09, (byte)0x08,
                     (byte)0x07, (byte)0x06, (byte)0x05, (byte)0x04,
                     (byte)0x03, (byte)0x02, (byte)0x01, (byte)0x00 };
    private static final byte[] HALF_HI =
        new byte[] { (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                     (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                     (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                     (byte)0x00, (byte)0x08, (byte)0xff, (byte)0xff,
                     (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                     (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                     (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x1f };
    private static final byte[] HALF_LO =
        new byte[] { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                     (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                     (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                     (byte)0xff, (byte)0x7f, (byte)0x00, (byte)0x00,
                     (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                     (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                     (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 };
    private static final byte[] HALF_SMALL =
        new byte[] { (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00,
                     (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00 };
    private static final byte[] RANDOM_1 = new byte[ModE221M3.PACKED_BYTES];
    private static final byte[] RANDOM_2 = new byte[ModE221M3.PACKED_BYTES];
    private static final byte[] RANDOM_3 = new byte[ModE221M3.PACKED_BYTES];
    private static final byte[] RANDOM_4 = new byte[ModE221M3.PACKED_BYTES];

    private static final BigInteger MODULUS =
        new BigInteger(new byte[] {
                (byte)0x1f, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0x1f, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xfd
            });

    private static final EnumMap<DataKind, ModE221M3> PARAMS =
        new EnumMap<>(DataKind.class);

    static {
        random.nextBytes(RANDOM_1);
        random.nextBytes(RANDOM_2);
        random.nextBytes(RANDOM_3);
        random.nextBytes(RANDOM_4);
        PARAMS.put(DataKind.ZERO, ModE221M3.zero());
        PARAMS.put(DataKind.ONE, ModE221M3.one());
        PARAMS.put(DataKind.MONE, ModE221M3.mone());
        PARAMS.put(DataKind.ASCENDING, new ModE221M3(ASCENDING_DATA));
        PARAMS.put(DataKind.DESCENDING, new ModE221M3(DESCENDING_DATA));
        PARAMS.put(DataKind.HALF_HI, new ModE221M3(HALF_HI));
        PARAMS.put(DataKind.HALF_LO, new ModE221M3(HALF_LO));
        PARAMS.put(DataKind.HALF_SMALL, new ModE221M3(HALF_SMALL));
        PARAMS.put(DataKind.RANDOM_1, new ModE221M3(RANDOM_1));
        PARAMS.put(DataKind.RANDOM_2, new ModE221M3(RANDOM_2));
        PARAMS.put(DataKind.RANDOM_3, new ModE221M3(RANDOM_3));
        PARAMS.put(DataKind.RANDOM_4, new ModE221M3(RANDOM_4));
    };

    private static final EnumMap<DataKind, BigInteger> BIGINT_PARAMS =
        new EnumMap<>(DataKind.class);

    static {
        BIGINT_PARAMS.put(DataKind.ZERO, BigInteger.ZERO);
        BIGINT_PARAMS.put(DataKind.ONE, BigInteger.ONE);
        BIGINT_PARAMS.put(DataKind.MONE, BigInteger.ONE.negate().mod(MODULUS));
        BIGINT_PARAMS.put(DataKind.ASCENDING,
                   new BigInteger(swapByteOrder(ASCENDING_DATA)));
        BIGINT_PARAMS.put(DataKind.DESCENDING,
                   new BigInteger(swapByteOrder(DESCENDING_DATA)));
        BIGINT_PARAMS.put(DataKind.HALF_HI,
                   new BigInteger(swapByteOrder(HALF_HI)));
        BIGINT_PARAMS.put(DataKind.HALF_LO,
                   new BigInteger(swapByteOrder(HALF_LO)));
        BIGINT_PARAMS.put(DataKind.HALF_SMALL,
                   new BigInteger(swapByteOrder(HALF_SMALL)));
        BIGINT_PARAMS.put(DataKind.RANDOM_1,
                   new BigInteger(swapByteOrder(RANDOM_1)));
        BIGINT_PARAMS.put(DataKind.RANDOM_2,
                   new BigInteger(swapByteOrder(RANDOM_2)));
        BIGINT_PARAMS.put(DataKind.RANDOM_3,
                   new BigInteger(swapByteOrder(RANDOM_3)));
        BIGINT_PARAMS.put(DataKind.RANDOM_4,
                   new BigInteger(swapByteOrder(RANDOM_4)));
    };

    public ModE221M3Perf() {
        super(PARAMS, BIGINT_PARAMS, MODULUS);

    }
}
