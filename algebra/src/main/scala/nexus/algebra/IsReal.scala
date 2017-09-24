package nexus.algebra

import scala.annotation._

/**
 * Encapsulates mathematical operations on real numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${R} is a real number.")
trait IsReal[@specialized(Float, Double) R] extends algebra.ring.Field[R] with Grad[R] {

  def mutable = false

  def add(x: R, y: R): R
  override def plus(x: R, y: R) = add(x, y)

  def sub(x: R, y: R): R
  override def minus(x: R, y: R) = sub(x, y)

  def neg(x: R): R
  override def negate(x: R) = neg(x)

  def mul(x: R, y: R): R
  override def times(x: R, y: R) = mul(x, y)

  def div(x: R, y: R): R

  def inv(x: R): R
  override def reciprocal(x: R) = inv(x)

  def zeroBy(x: R) = zero

  def exp(x: R): R
  def log(x: R): R
  def expm1(x: R): R
  def log1p(x: R): R

  def abs(x: R): R
  def sgn(x: R): R

  def sin(x: R): R
  def cos(x: R): R
  def tan(x: R): R

  def sqr(x: R): R
  def sqrt(x: R): R

  final def eMul(x1: R, x2: R) = mul(x1, x2)
  final def eDiv(x1: R, x2: R) = div(x1, x2)
  final def eSqrt(x: R) = sqrt(x)
  final def scale(x: R, k: Double) = mul(x, fromDouble(k))

  def fromFloat(x: Float) = fromDouble(x)
}

object IsReal {

  implicit def extract[T[_ <: $$], R](implicit T: IsTypedRealTensor[T, R]): IsReal[R] = T.R

}