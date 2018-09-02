package nexus.instances

import cats.Order
import nexus._
import nexus.algebra._
import nexus.ops._

/**
 * @author Tongfei Chen
 */
class RealExprIsReal[R](implicit R: IsReal[R]) extends IsReal[Expr[R]] {
  def add(x: Expr[R], y: Expr[R]) = Add(x, y)
  def sub(x: Expr[R], y: Expr[R]) = Sub(x, y)
  def neg(x: Expr[R]) = Neg(x)
  def mul(x: Expr[R], y: Expr[R]) = Mul(x, y)
  def div(x: Expr[R], y: Expr[R]) = Div(x, y)
  def inv(x: Expr[R]) = Inv(x)
  def exp(x: Expr[R]) = Exp(x)
  def log(x: Expr[R]) = Log(x)
  def expm1(x: Expr[R]) = Expm1(x)
  def log1p(x: Expr[R]) = Log1p(x)
  def abs(x: Expr[R]) = Abs(x)
  def sgn(x: Expr[R]) = ??? // Sgn(x)
  def sin(x: Expr[R]) = Sin(x)
  def cos(x: Expr[R]) = Cos(x)
  def tan(x: Expr[R]) = ??? //Tan(x)
  def sqr(x: Expr[R]) = Sqr(x)
  def sqrt(x: Expr[R]) = Sqrt(x)
  def toFloat(x: Expr[R]) = ???
  def one = Const(R.one)
  def zero = Const(R.zero)
  def addS(x1: Expr[R], x2: Double) = Add(x1, Const(R.fromDouble(x2)))
  def addI(x1: Expr[R], x2: Expr[R]): Unit = ???
}

class RealExprGenOrder[R](implicit R: IsReal[R], RO: Order[R]) extends GenOrder[Expr[R], Expr[Boolean]] {
  def B = BoolExprIsBool
  def eqv(x: Expr[R], y: Expr[R]) = ???
  def lt(x: Expr[R], y: Expr[R]) = ???
}

object FloatExprIsReal extends RealExprIsReal()(Float32)
object DoubleExprIsReal extends RealExprIsReal()(Float64)

object FloatExprGenOrder extends RealExprGenOrder[Float]()(Float32, Float32)
object DoubleExprGenOrder extends RealExprGenOrder[Double]()(Float64, Float64)
