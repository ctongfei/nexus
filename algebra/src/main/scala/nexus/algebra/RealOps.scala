package nexus.algebra

import nexus.exception._

/**
 * Encapsulates mathematical operations on real numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait RealOps[@specialized(Float, Double) D] extends algebra.ring.Field[D] with GradOps[D] {

  def mutable = false

  def add(x: D, y: D): D
  override def plus(x: D, y: D) = add(x, y)

  def sub(x: D, y: D): D
  override def minus(x: D, y: D) = sub(x, y)

  def neg(x: D): D
  override def negate(x: D) = neg(x)

  def mul(x: D, y: D): D
  override def times(x: D, y: D) = mul(x, y)

  def div(x: D, y: D): D

  def inv(x: D): D
  override def reciprocal(x: D) = inv(x)

  def zeroBy(x: D) = zero

  def exp(x: D): D
  def log(x: D): D
  def expm1(x: D): D
  def log1p(x: D): D

  def sin(x: D): D
  def cos(x: D): D
  def tan(x: D): D

  def sqr(x: D): D

  final def eMul(x1: D, x2: D) = mul(x1, x2)
  final def eDiv(x1: D, x2: D) = div(x1, x2)
  final def scale(x: D, k: Double) = mul(x, fromDouble(k))

  def fromFloat(x: Float) = fromDouble(x)
}

object RealOps {

  implicit object Float32 extends RealOps[Float] {
    def one = 1f
    def zero = 0f
    def add(x: Float, y: Float) = x + y
    def sub(x: Float, y: Float) = x - y
    def neg(x: Float) = -x
    def mul(x: Float, y: Float) = x * y
    def div(x: Float, y: Float) = x / y
    def inv(x: Float) = 1f / x
    
    def addS(x1: Float, x2: Double) = x1 + x2.toFloat


    def sqr(x: Float) = x * x
    def sqrt(x: Float) = Math.sqrt(x).toFloat
    def exp(x: Float) = Math.exp(x).toFloat
    def log(x: Float) = Math.log(x).toFloat
    def expm1(x: Float) = Math.expm1(x).toFloat
    def log1p(x: Float) = Math.log1p(x).toFloat
    def sin(x: Float) = Math.sin(x).toFloat
    def cos(x: Float) = Math.cos(x).toFloat
    def tan(x: Float) = Math.tan(x).toFloat

    override def fromDouble(a: Double) = a.toFloat
    override def fromInt(n: Int) = n.toFloat
    def addI(x1: Float, x2: Float) = throw new IncrementingImmutableGradientException
  }
  
  implicit object Float64 extends RealOps[Double] {
    def one = 1d
    def zero = 0d
    def add(x: Double, y: Double) = x + y
    def sub(x: Double, y: Double) = x - y
    def neg(x: Double) = -x
    def mul(x: Double, y: Double) = x * y
    def div(x: Double, y: Double) = x / y
    def inv(x: Double) = 1d / x

    def addS(x1: Double, x2: Double) = x1 + x2


    def sqr(x: Double) = x * x
    def sqrt(x: Double) = Math.sqrt(x)
    def exp(x: Double) = Math.exp(x)
    def log(x: Double) = Math.log(x)
    def expm1(x: Double) = Math.expm1(x)
    def log1p(x: Double) = Math.log1p(x)
    def sin(x: Double) = Math.sin(x)
    def cos(x: Double) = Math.cos(x)
    def tan(x: Double) = Math.tan(x)

    override def fromDouble(a: Double) = a
    override def fromInt(n: Int) = n
    def addI(x1: Double, x2: Double) = throw new IncrementingImmutableGradientException
  }

}
