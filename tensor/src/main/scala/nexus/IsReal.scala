package nexus

import algebra.ring._
import cats._

import scala.annotation._

/**
 * Encapsulates mathematical operations on real numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${R} is a real number.")
trait IsReal[@specialized(Float, Double) R] extends Grad[R] with Field[R] with Order[R] {
  def zeroBy(x: R) = zero

  def mutable = false

  def zero: R
  def one: R
  def pi: R
  def e: R
  def twoPi: R // also known as tau, as in the Tau Manifesto
  def logOf2: R

  def fromDouble(d: Double): R
  def fromFloat(f: Float): R = fromDouble(f)

  def sampleFromStandardUniform: R
  def sampleFromStandardNormal: R

  def add(x: R, y: R): R
  def sub(x: R, y: R): R
  def neg(x: R): R
  def mul(x: R, y: R): R
  def div(x: R, y: R): R
  def recip(x: R): R

  def sqr(x: R): R
  def cube(x: R): R
  def sqrt(x: R): R
  def cbrt(x: R): R
  def pow(x: R, y: R): R

  def abs(x: R): R
  def sgn(x: R): R

  def exp(x: R): R
  def exp2(x: R): R
  def expm1(x: R): R
  def log(x: R): R
  def log2(x: R): R
  def log1p(x: R): R
  def logistic(x: R): R
  def logLogistic(x: R): R
  def logit(x: R): R
  def logAddExp(x: R, y: R): R

  def sin(x: R): R
  def cos(x: R): R
  def tan(x: R): R
  def arcsin(x: R): R
  def arccos(x: R): R
  def arctan(x: R): R
  def arctan2(y: R, x: R): R

  def sinh(x: R): R
  def cosh(x: R): R
  def tanh(x: R): R
  def arsinh(x: R): R
  def arcosh(x: R): R
  def artanh(x: R): R

  def compare(x: R, y: R): Int
  def min(x: R, y: R): R
  def max(x: R, y: R): R

  def round(x: R): R
  def ceil(x: R): R
  def floor(x: R): R
  def clip(x: R, l: R, r: R): R

  def addScalar(x1: R, x2: Double) = add(x1, fromDouble(x2))
  def addInplace(x1: R, x2: R): R = add(x1, x2)
  def scale(x: R, k: Double) = mul(x, fromDouble(k))

  // Conforms to algebra.ring.Field
  final def negate(x: R) = neg(x)
  final def plus(x: R, y: R) = add(x, y)
  final def times(x: R, y: R) = mul(x, y)

}


object IsReal {
  def apply[R](implicit R: IsReal[R]) = R
  implicit def c2[T[_], R](implicit T: IsRealTensorK[T, R]): IsReal[R] = T.R
}


@implicitNotFound("Cannot get real value out of boxed type ${R}.")
trait GetReal[@specialized(Float, Double) R] {

  def toFloat(x: R): Float
  def toDouble(x: R): Double

}
