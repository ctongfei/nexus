package nexus.algebra

import scala.annotation._
import cats._


trait IsGenReal[E[_], @specialized(Float, Double) R] extends Grad[E[R]] {
  def mutable = false

  def B: IsGenBool[E, Boolean]

  def zero: E[R]
  def one: E[R]
  def fromDouble(d: Double): E[R]
  def fromFloat(f: Float): E[R] = fromDouble(f)

  def add(x: E[R], y: E[R]): E[R]

  def sub(x: E[R], y: E[R]): E[R]

  def neg(x: E[R]): E[R]

  def mul(x: E[R], y: E[R]): E[R]

  def div(x: E[R], y: E[R]): E[R]

  def inv(x: E[R]): E[R]

  def exp(x: E[R]): E[R]
  def log(x: E[R]): E[R]
  def expm1(x: E[R]): E[R]
  def log1p(x: E[R]): E[R]

  def abs(x: E[R]): E[R]
  def sgn(x: E[R]): E[R]

  def sin(x: E[R]): E[R]
  def cos(x: E[R]): E[R]
  def tan(x: E[R]): E[R]

  def sqr(x: E[R]): E[R]
  def sqrt(x: E[R]): E[R]

  def lt(x: E[R], y: E[R]): E[Boolean]
  def gt(x: E[R], y: E[R]) = lt(y, x)
  def eq(x: E[R], y: E[R]): E[Boolean]
  def ne(x: E[R], y: E[R]) = B.not(eq(x, y))
  def le(x: E[R], y: E[R]): E[Boolean]
  def ge(x: E[R], y: E[R]) = le(y, x)

  def eMul(x1: E[R], x2: E[R]) = mul(x1, x2)
  def eDiv(x1: E[R], x2: E[R]) = div(x1, x2)
  def eSqrt(x: E[R]) = sqrt(x)
  def scale(x: E[R], k: Double) = mul(x, fromDouble(k))

}

/**
 * Encapsulates mathematical operations on real numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${R} is a real number.")
trait IsReal[R] extends IsGenReal[Id, R] {
  def zeroBy(x: R) = zero
  def toFloat(x: R): Float
}

object IsReal {

  def apply[R](implicit R: IsReal[R]) = R

  implicit def extract[T[_], R](implicit T: IsRealTensorK[T, R]): IsReal[R] = T.R

}
