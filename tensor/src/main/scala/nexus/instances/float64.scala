package nexus.instances

import nexus._


object DoubleIsReal extends IsReal[Double] with GetReal[Double] {
  type R = Double

  final val one = 1d
  final val zero = 0d
  final val pi = Math.PI
  final val e = Math.E
  final val twoPi = Math.PI * 2.0
  final val logOf2 = Math.log(2.0)

  def sampleFromStandardUniform: R = randomSource.nextDouble()
  def sampleFromStandardNormal: R = randomSource.nextGaussian()

  def add(x: R, y: R) = x + y
  def sub(x: R, y: R) = x - y
  def neg(x: R) = -x
  def mul(x: R, y: R) = x * y
  def div(x: R, y: R) = x / y
  def recip(x: R) = 1d / x

  override def addScalar(x1: R, x2: R) = x1 + x2

  def abs(x: R) = Math.abs(x)
  def sgn(x: R) = Math.signum(x)
  def sqr(x: R) = x * x
  def sqrt(x: R) = Math.sqrt(x)
  def cube(x: R) = x * x * x
  def cbrt(x: R) = Math.cbrt(x)
  def pow(x: R, y: R) = Math.pow(x, y)

  def exp(x: R) = Math.exp(x)
  def exp2(x: R) = Math.pow(2.0, x)
  def expm1(x: R) = Math.expm1(x)
  def log(x: R) = Math.log(x)
  def log2(x: R) = Math.log(x) / logOf2
  def log1p(x: R) = Math.log1p(x)
  def logistic(x: R) =
    if (x >= 0.0) 1.0 / (1.0 + Math.exp(-x))
    else {
      val ex = Math.exp(x)
      ex / (ex + 1.0)
    }
  def logLogistic(x: R) =
    if (x >= 0.0) -Math.log1p(Math.exp(-x))
    else x - Math.log1p(Math.exp(x))
  def logit(x: R) = Math.log(x) - Math.log1p(-x)
  def logAddExp(x: R, y: R) = {
    val t = x - y
    if (t == 0.0f)
      x + logOf2
    else if (t > 0)
      x + Math.log1p(Math.exp(-t))
    else
      y + Math.log1p(Math.exp(t))
  }

  def sin(x: R) = Math.sin(x)
  def cos(x: R) = Math.cos(x)
  def tan(x: R) = Math.tan(x)
  def arcsin(x: R) = Math.asin(x)
  def arccos(x: R) = Math.acos(x)
  def arctan(x: R) = Math.atan(x)
  def arctan2(y: R, x: R) = Math.atan2(y, x)
  def hypot(x: R, y: R) = Math.hypot(x, y)


  def sinh(x: R) = Math.sinh(x)
  def cosh(x: R) = Math.cosh(x)
  def tanh(x: R) = Math.tanh(x)

  def arsinh(x: R) = ???
  def arcosh(x: R) = ???
  def artanh(x: R) = ???

  def compare(x: R, y: R) = ???

  def round(x: R) = Math.round(x)
  def ceil(x: R) = Math.ceil(x)
  def floor(x: R) = Math.floor(x)
  def clip(x: R, l: R, r: R) = if (x <= l) l else if (x >= r) r else x

  override def fromDouble(a: R) = a
  override def fromInt(n: Int) = n

  def toDouble(x: R) = x
  def toFloat(x: R) = x.toFloat

  override def addInplace(x1: R, x2: R) = x1 + x2

  override def toString = "Double"
}
