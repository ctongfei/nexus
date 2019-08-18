package nexus.functions

import algebra.ring._
import nexus._


trait ScalarFunctions {
  // TODO: Make everything in here macros

  // Basic arithmetic

  /** Returns the value zero (the additive identity) of type `R`. */
  def zero[R](implicit R: AdditiveMonoid[R]): R = R.zero

  /** Returns the value one (the multiplicative identity) of type `R`. */
  def one[R](implicit R: MultiplicativeMonoid[R]): R = R.one

  /** Adds two values, returning their sum. */
  def add[R](x: R, y: R)(implicit R: AdditiveSemigroup[R]): R = R.plus(x, y)

  /** Negates a value. */
  def neg[R](x: R)(implicit R: AdditiveGroup[R]): R = R.negate(x)

  /** Subtracts a value from another, returning their difference. */
  def sub[R](x: R, y: R)(implicit R: AdditiveGroup[R]): R = R.minus(x, y)

  /** Multiplies two values, returning their product. */
  def mul[R](x: R, y: R)(implicit R: MultiplicativeSemigroup[R]): R = R.times(x, y)

  /** Divides a value by another. */
  def div[R](x: R, y: R)(implicit R: MultiplicativeGroup[R]): R = R.div(x, y)

  /** Reciprocal (the multiplicative inverse) of a value. */
  def recip[R](x: R)(implicit R: MultiplicativeGroup[R]): R = R.reciprocal(x)

  /** Finds the modulus (the remainder after division) of a number by another. */
  def mod[Z](x: Z, y: Z)(implicit Z: IsInt[Z]): Z = Z.mod(x, y)

  // Power
  /** Returns the square of a value. */
  def sqr[R](x: R)(implicit R: MultiplicativeSemigroup[R]): R = R.times(x, x)

  /** Returns the cube of a value. */
  def cube[R](x: R)(implicit R: MultiplicativeSemigroup[R]): R = R.times(R.times(x, x), x)

  /** Returns the square root of a value. */
  def sqrt[R](x: R)(implicit R: IsReal[R]): R = R.sqrt(x)

  /** Returns the cube root of a value. */
  def cbrt[R](x: R)(implicit R: IsReal[R]): R = ???

  def ipow[R, Z](x: R, y: Z)(implicit R: IsReal[R], Z: GetInt[Z]): R = R.pow(x, Z.toInt(y))



  // Trigonometrics
  def sin[R](x: R)(implicit R: IsReal[R]): R = R.sin(x)

  def cos[R](x: R)(implicit R: IsReal[R]): R = R.cos(x)

  def tan[R](x: R)(implicit R: IsReal[R]): R = R.tan(x)

  def arcsin[R](x: R)(implicit R: IsReal[R]): R = R.arcsin(x)

  def arccos[R](x: R)(implicit R: IsReal[R]): R = R.arccos(x)

  def arctan[R](x: R)(implicit R: IsReal[R]): R = R.arctan(x)

  def hypot[R](x: R, y: R)(implicit R: IsReal[R]): R = ???

  def arctan2[R](y: R, x: R)(implicit R: IsReal[R]): R = ???

  // Hyperbolic trigonometrics
  def sinh[R](x: R)(implicit R: IsReal[R]): R = ???
  def cosh[R](x: R)(implicit R: IsReal[R]): R = ???
  def tanh[R](x: R)(implicit R: IsReal[R]): R = ???
  def arsinh[R](x: R)(implicit R: IsReal[R]): R = ???
  def arcosh[R](x: R)(implicit R: IsReal[R]): R = ???
  def artanh[R](x: R)(implicit R: IsReal[R]): R = ???

  // Exponential and logarithms
  def exp[R](x: R)(implicit R: IsReal[R]): R = R.exp(x)

  def log[R](x: R)(implicit R: IsReal[R]): R = R.log(x)

  def expm1[R](x: R)(implicit R: IsReal[R]): R = R.expm1(x)

  def log1p[R](x: R)(implicit R: IsReal[R]): R = R.log1p(x)

  def logistic[R](x: R)(implicit R: IsReal[R]): R = ???

  def logit[R](x: R)(implicit R: IsReal[R]): R = ???

  def logAddExp[R](x: R)(implicit R: IsReal[R]): R = ???



}
