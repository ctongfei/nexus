package nexus.algebra.instances

import nexus.algebra._

object Float32 extends IsReal[Float] {
  val one = 1f
  val zero = 0f
  def add(x: Float, y: Float) = x + y
  def sub(x: Float, y: Float) = x - y
  def neg(x: Float) = -x
  def mul(x: Float, y: Float) = x * y
  def div(x: Float, y: Float) = x / y
  def inv(x: Float) = 1f / x

  def addS(x1: Float, x2: Double) = x1 + x2.toFloat

  def abs(x: Float) = Math.abs(x)
  def sgn(x: Float) = Math.signum(x)
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
  def addI(x1: Float, x2: Float) = x1 + x2
}

object Float64 extends IsReal[Double] {
  def one = 1d
  def zero = 0d
  def add(x: Double, y: Double) = x + y
  def sub(x: Double, y: Double) = x - y
  def neg(x: Double) = -x
  def mul(x: Double, y: Double) = x * y
  def div(x: Double, y: Double) = x / y
  def inv(x: Double) = 1d / x

  def addS(x1: Double, x2: Double) = x1 + x2

  def abs(x: Double) = Math.abs(x)
  def sgn(x: Double) = Math.signum(x)
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
  def addI(x1: Double, x2: Double) = x1 + x2
}
