package nexus.algebra

/**
 * Encapsulates mathematical operations on real numbers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait RealOps[@specialized(Float, Double) R] extends algebra.ring.Field[R] with GradOps[R] {

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

  def sin(x: R): R
  def cos(x: R): R
  def tan(x: R): R

  def sqr(x: R): R

  final def eMul(x1: R, x2: R) = mul(x1, x2)
  final def eDiv(x1: R, x2: R) = div(x1, x2)
  final def scale(x: R, k: Double) = mul(x, fromDouble(k))

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
    def eSqrt(x: Float) = Math.sqrt(x).toFloat
    def exp(x: Float) = Math.exp(x).toFloat
    def log(x: Float) = Math.log(x).toFloat
    def expm1(x: Float) = Math.expm1(x).toFloat
    def log1p(x: Float) = Math.log1p(x).toFloat
    def sin(x: Float) = Math.sin(x).toFloat
    def cos(x: Float) = Math.cos(x).toFloat
    def tan(x: Float) = Math.tan(x).toFloat

    override def fromDouble(a: Double) = a.toFloat
    override def fromInt(n: Int) = n.toFloat
    def addI(x1: Float, x2: Float) = x1 + x2
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
    def eSqrt(x: Double) = Math.sqrt(x)
    def exp(x: Double) = Math.exp(x)
    def log(x: Double) = Math.log(x)
    def expm1(x: Double) = Math.expm1(x)
    def log1p(x: Double) = Math.log1p(x)
    def sin(x: Double) = Math.sin(x)
    def cos(x: Double) = Math.cos(x)
    def tan(x: Double) = Math.tan(x)

    override def fromDouble(a: Double) = a
    override def fromInt(n: Int) = n
    def addI(x1: Double, x2: Double) = x1 + x2
  }

}
