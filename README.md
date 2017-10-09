# PrimeFields-Java- Prime field arithmetic over cryptographically useful primes

PrimeFields-Java aims to provide a cryptographically-viable implementation of
field arithmetic over various prime number fields that are useful in
cryptography.  The majority of these are for elliptic-curve cryptography;
however, prime field arithmetic is used in other areas as well, notably the
Poly1305 MAC.

## Prime Field Support

In order to support the list of curves, SafeCurves-Java must provide prime-field
arithmetic for the following prime-order fields:

* `2^130 - 5`
* `2^221 - 3`
* `2^222 - 117`
* `2^251 - 9`
* `2^255 - 19`
* `2^382 - 105`
* `2^383 - 187`
* `2^414 - 17`
* `2^511 - 187`
* `2^521 - 1`

### Prime Field Operations

SafeCurves-Java aims to support the following prime-field operations for all
fields:

* Basic field operations (add, subtract, multiply, divide)
* Add, subtract, multiply by "small" numbers
* Additive and multiplicative inverse
* Square root
* Inverse square root
* Legendre symbol

