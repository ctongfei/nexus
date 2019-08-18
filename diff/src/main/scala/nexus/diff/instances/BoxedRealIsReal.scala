package nexus.diff.instances

import nexus._
import nexus.diff._
import nexus.diff.ops._

class BoxedRealIsReal[F[_], R](implicit F: Algebra[F], val R: IsReal[R]) extends IsReal[F[R]] {

  lazy val zero = F.const(R.zero, "0")
  lazy val one = F.const(R.one, "1")
  lazy val pi = F.const(R.pi, "π")
  lazy val e = F.const(R.e, "e")
  lazy val twoPi = F.const(R.twoPi, "2π")
  lazy val logOf2 = F.const(R.logOf2, "log2")

  def sampleFromStandardUniform = F.const(R.sampleFromStandardUniform, "U(0, 1)")
  def sampleFromStandardNormal = F.const(R.sampleFromStandardNormal, "N(0, 1)")

  def add(x: F[R], y: F[R]) = Add(x, y)
  def sub(x: F[R], y: F[R]) = Sub(x, y)
  def neg(x: F[R]) = Neg(x)
  def mul(x: F[R], y: F[R]) = Mul(x, y)
  def div(x: F[R], y: F[R]) = Div(x, y)
  def recip(x: F[R]) = Recip(x)

  def sqr(x: F[R]) = Sqr(x)
  def cube(x: F[R]) = ???
  def sqrt(x: F[R]) = Sqrt(x)
  def cbrt(x: F[R]) = ???
  def pow(x: F[R], y: F[R]) = ???

  def abs(x: F[R]) = Abs(x)
  def sgn(x: F[R]) = ???

  def exp(x: F[R]) = Exp(x)
  def exp2(x: F[R]) = ???
  def expm1(x: F[R]) = Expm1(x)
  def log(x: F[R]) = Log(x)
  def log2(x: F[R]) = ???
  def log1p(x: F[R]) = Log1p(x)
  def logistic(x: F[R]) = ???
  def logLogistic(x: F[R]) = ???
  def logit(x: F[R]) = ???
  def logAddExp(x: F[R], y: F[R]) = ???

  def sin(x: F[R]) = Sin(x)
  def cos(x: F[R]) = Cos(x)
  def tan(x: F[R]) = Tan(x)
  def arcsin(x: F[R]) = ArcSin(x)
  def arccos(x: F[R]) = ArcCos(x)
  def arctan(x: F[R]) = ArcTan(x)
  def arctan2(y: F[R], x: F[R]) = ???

  def sinh(x: F[R]) = ???
  def cosh(x: F[R]) = ???
  def tanh(x: F[R]) = ???

  def arsinh(x: F[R]) = ???
  def arcosh(x: F[R]) = ???
  def artanh(x: F[R]) = ???

  def compare(x: F[R], y: F[R]) = ???

  def round(x: F[R]) = ???
  def ceil(x: F[R]) = ???
  def floor(x: F[R]) = ???
  def clip(x: F[R], l: F[R], r: F[R]) = ???
}
