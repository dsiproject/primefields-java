package net.metricspace.crypto.math.field;

/**
 * Common superclass for elements of a finite integer field, modulo a
 * prime number {@code P} such that {@code P mod 4 = 1}.
 * <p>
 * This class exposes the quartic legendre symbol.
 */
public abstract class PrimeField1Mod4<Val extends PrimeField1Mod4<Val>>
    extends PrimeField<Val> {
    /**
     * Initialize with a digits array.
     *
     * This constructor initializes the {@code digits} field.  The
     * created object takes possession of the array.  To initialize
     * from an array without taking possession, use {@link #set}.
     *
     * @param digits The {@code digits} field.
     * @see #set
     */
    protected PrimeField1Mod4(final long[] digits) {
        super(digits);
    }

    /**
     * Compute the quartic Legendre symbol on this number.
     * <p>
     * A number {@code n} is a <i>quartic residue</i> {@code mod p}
     * if there exists some {@code m} such that {@code m * m * m * m = n mod
     * p} (that is, {@code n} has a quartic root {@code mod p}).
     * <p>
     * The quartic Legendre symbol on {@code n mod p} evaluates to
     * {@code 1} if the value is a quartic residue {@code mod p},
     * and {@code -1} if not.
     * <p>
     * This function is only guaranteed to produce a meaningful result
     * if the input is a quadratic residue (meaning {@link #legendre}
     * yields {@code 1}.
     *
     * @return {@code 1} if the value is a quartic residue, {@code -1}
     *         if not.
     * @see #legendre
     */
    public abstract byte legendreQuartic();
}
