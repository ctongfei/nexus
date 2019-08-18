package nexus.instances

import nexus._

object FloatIsReal extends IsReal[Float] with GetReal[Float] {
  type R = Float
  def B = BoolIsBool

  final val zero = 0f
  final val one = 1f
  final val pi = Math.PI.toFloat
  final val e = Math.E.toFloat
  final val twoPi = Math.PI.toFloat * 2.0f
  final val logOf2 = Math.log(2.0).toFloat

  def sampleFromStandardUniform: R = randomSource.nextFloat()
  def sampleFromStandardNormal: R = randomSource.nextGaussian().toFloat

  def add(x: R, y: R) = x + y
  def sub(x: R, y: R) = x - y
  def neg(x: R) = -x
  def mul(x: R, y: R) = x * y
  def div(x: R, y: R) = x / y
  def recip(x: R) = 1f / x

  override def addScalar(x1: R, x2: Double) = x1 + x2.toFloat

  def sqr(x: R) = x * x
  def cube(x: R) = x * x * x
  def sqrt(x: R) = Math.sqrt(x).toFloat
  def cbrt(x: R) = Math.cbrt(x).toFloat
  def pow(x: R, y: R) = Math.pow(x, y).toFloat

  def abs(x: R) = Math.abs(x)
  def sgn(x: R) = Math.signum(x)


  def exp(x: R) = Math.exp(x).toFloat
  def exp2(x: R) = Math.pow(2.0, x).toFloat
  def expm1(x: R) = Math.expm1(x).toFloat
  def log(x: R) = Math.log(x).toFloat
  def log2(x: R) = (Math.log(x) / logOf2).toFloat
  def log1p(x: R) = Math.log1p(x).toFloat
  def logistic(x: R) =  // numeric stability
    if (x >= 0f) 1f / (1f + Math.exp(-x).toFloat)
    else {
      val ex = Math.exp(x).toFloat
      ex / (ex + 1f)
    }
  def logLogistic(x: R) =  // numeric stability
    if (x >= 0f) -Math.log1p(Math.exp(-x)).toFloat
    else x - Math.log1p(Math.exp(x)).toFloat
  def logit(x: R) = (Math.log(x) - Math.log1p(-x)).toFloat
  def logAddExp(x: R, y: R) = {  // numeric stability
    val t = x - y
    if (t == 0.0f)
      x + logOf2
    else if (t > 0)
      x + Math.log1p(Math.exp(-t)).toFloat
    else
      y + Math.log1p(Math.exp(t)).toFloat
  }

  def sin(x: R) = Math.sin(x).toFloat
  def cos(x: R) = Math.cos(x).toFloat
  def tan(x: R) = Math.tan(x).toFloat
  def arcsin(x: R) = Math.asin(x).toFloat
  def arccos(x: R) = Math.acos(x).toFloat
  def arctan(x: R) = Math.atan(x).toFloat
  def arctan2(y: R, x: R) = Math.atan2(y, x).toFloat
  def hypot(x: R, y: R) = Math.hypot(x, y).toFloat

  def sinh(x: R) = Math.sinh(x).toFloat
  def cosh(x: R) = Math.cosh(x).toFloat
  def tanh(x: R) = Math.tanh(x).toFloat
  def arsinh(x: R) = ???
  def arcosh(x: R) = ???
  def artanh(x: R) = ???

  def compare(x: R, y: R) = ???

  def round(x: R) = Math.round(x).toFloat
  def ceil(x: R) = Math.ceil(x).toFloat
  def floor(x: R) = Math.floor(x).toFloat

  def clip(x: R, l: R, r: R) = if (x <= l) l else if (x >= r) r else x

  override def fromDouble(a: Double) = a.toFloat
  override def fromInt(n: Int) = n.toFloat

  override def toDouble(x: R) = x.toDouble
  def toFloat(x: R) = x

  override def addInplace(x1: R, x2: R) = x1 + x2

  override def toString = "Float"
}
