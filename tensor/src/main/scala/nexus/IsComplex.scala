package nexus

import algebra.ring._

/**
 * Typeclass representing operations on complex numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsComplex[C, R] extends Field[C] {

  def R: IsReal[R]

  def complex(x: R, y: R): C
  def re(z: C): R
  def im(z: C): R

  override def fromDouble(x: Double) = complex(R.fromDouble(x), R.zero)
  def fromFloat(x: Float) = complex(R.fromFloat(x), R.zero)

  def zero = complex(R.zero, R.zero)
  def one = complex(R.one, R.zero)
  def i = complex(R.zero, R.one)

  def add(x: C, y: C) = complex(R.add(re(x), re(y)), R.add(im(x), im(y)))
  def sub(x: C, y: C) = complex(R.sub(re(x), re(y)), R.sub(im(x), im(y)))

  def arg(z: C): R

  def times(x: C, y: C) = ???
  def plus(x: C, y: C) = add(x, y)
  def div(x: C, y: C) = ???
  def negate(x: C) = ???

  def toFloat(x: C) = ???
  def neg(x: C) = ???
  def mul(x: C, y: C) = ???
  def inv(x: C) = ???
  def exp(x: C) = ???
  def log(x: C) = ???
  def expm1(x: C) = ???
  def log1p(x: C) = ???
  def abs(x: C) = ???
  def sgn(x: C) = ???
  def sin(x: C) = ???
  def cos(x: C) = ???
  def tan(x: C) = ???
  def arcsin(x: C) = ???
  def arccos(x: C) = ???
  def arctan(x: C) = ???
  def sqr(x: C) = ???
  def sqrt(x: C) = ???
}
