package nexus.algebra.instances

import nexus.algebra._

object Float16 extends IsReal[Half] {
  def add(x: Half, y: Half) = x + y
  def sub(x: Half, y: Half) = x - y
  def neg(x: Half) = -x
  def mul(x: Half, y: Half) = x * y
  def div(x: Half, y: Half) = x / y
  def inv(x: Half) = Half.One / x
  def exp(x: Half) = ???
  def log(x: Half) = ???
  def expm1(x: Half) = ???
  def log1p(x: Half) = ???
  def abs(x: Half) = ???
  def sgn(x: Half) = ???
  def sin(x: Half) = ???
  def cos(x: Half) = ???
  def tan(x: Half) = ???
  def sqr(x: Half) = ???
  def sqrt(x: Half) = ???
  def toFloat(x: Half) = ???

  def compare(x: Half, y: Half) = ???

  def one = ???
  def addS(x1: Half, x2: Double) = ???
  def addI(x1: Half, x2: Half) = ???
  def zero = ???
}

object Float32 extends IsReal[Float] {
  type R = Float
  
  val one = 1f
  val zero = 0f
  def add(x: R, y: R) = x + y
  def sub(x: R, y: R) = x - y
  def neg(x: R) = -x
  def mul(x: R, y: R) = x * y
  def div(x: R, y: R) = x / y
  def inv(x: R) = 1f / x

  def addS(x1: R, x2: Double) = x1 + x2.toFloat

  def abs(x: R) = Math.abs(x)
  def sgn(x: R) = Math.signum(x)
  def sqr(x: R) = x * x
  def sqrt(x: R) = Math.sqrt(x).toFloat
  def exp(x: R) = Math.exp(x).toFloat
  def log(x: R) = Math.log(x).toFloat
  def expm1(x: R) = Math.expm1(x).toFloat
  def log1p(x: R) = Math.log1p(x).toFloat
  def sin(x: R) = Math.sin(x).toFloat
  def cos(x: R) = Math.cos(x).toFloat
  def tan(x: R) = Math.tan(x).toFloat

  def compare(x: R, y: R) = (x - y).toInt
  override def lt(x: R, y: R) = x < y
  override def lteqv(x: R, y: R) = x <= y
  override def eqv(x: R, y: R) = x == y
  override def neqv(x: R, y: R) = x != y
  override def gt(x: R, y: R) = x > y
  override def gteqv(x: R, y: R) = x >= y

  override def fromDouble(a: Double) = a.toFloat
  override def fromInt(n: Int) = n.toFloat

  def toFloat(x: R) = x

  def addI(x1: R, x2: R) = x1 + x2
}

object Float64 extends IsReal[Double] {
  type R = Double

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

  def compare(x: R, y: R) = (x - y).toInt
  override def lt(x: R, y: R) = x < y
  override def lteqv(x: R, y: R) = x <= y
  override def eqv(x: R, y: R) = x == y
  override def neqv(x: R, y: R) = x != y
  override def gt(x: R, y: R) = x > y
  override def gteqv(x: R, y: R) = x >= y

  override def fromDouble(a: Double) = a
  override def fromInt(n: Int) = n

  def toFloat(x: Double) = x.toFloat

  def addI(x1: Double, x2: Double) = x1 + x2
}
