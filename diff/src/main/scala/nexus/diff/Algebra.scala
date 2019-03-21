package nexus.diff

import cats._
import nexus.{Id => _, _}
import nexus.diff.Algebra.IdAlgebra
import nexus.diff.ops._

/**
 * A typed tagless final encoding of computation nodes in a differentiable program.
 *
 * Reference:
 *   - O Kiselyov (2012): Typed tagless final interpreters. Generic and Indexed Programming, pp. 130-174.
 *
 * @tparam D Type of box for interpreting a differentiable program.
 *           This could be either [[Symbolic]] (for lazy computation)
 *           or [[Traced]] (for eager computation). If `D =:= `[[Id]],
 *           the program being interpreted loses the ability to perform
 *           differentiation (back-propagation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Algebra[D[_]] {

  import Algebra._

  type In[_]

  def forTraining: Boolean

  /** Converts an input to a node in this differential program. */
  def input[X](input: In[X], name: String): D[X]

  /** Lifts a value into box `D`. */
  def const[X](value: X, name: String): D[X]

  /** Put the result of a nullary operation into box `D`. */
  def app0[Y](op: Op0[Y]): D[Y]

  /** Applies a unary operation to a boxed node. */
  def app1[X, Y](op: Op1[X, Y], x: D[X]): D[Y]

  /** Applies a binary operation to two boxed nodes. */
  def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: D[X1], x2: D[X2]): D[Y]

  /** Applies a ternary operation to three boxed nodes. */
  def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: D[X1], x2: D[X2], x3: D[X3]): D[Y]

  /** Coerces a parameter (`Param[X]`) into box `D` (as `D[X]`). */
  def fromParam[X](p: Param[X]): D[X]

  /** Unpacks a parameter from a box. */
  def getParam[X](p: D[X]): Option[Param[X]]

  object Param {
    def apply[X](p: Param[X]): D[X] = fromParam(p)
    def unapply[X](p: D[X]): Option[Param[X]] = getParam(p)
  }

  def liftReal[R: IsReal]: IsReal[D[R]] = new LiftedIsReal()(this, IsReal[R])
}


object Algebra {

  implicit object IdAlgebra extends Algebra[Id] {
    type In[X] = X
    def forTraining = false
    def input[X](input: X, name: String) = input
    def const[X](value: X, name: String) = value
    def fromParam[X](p: Param[X]) = p.value
    def getParam[X](p: Id[X]) = None
    def app0[Y](op: Op0[Y]) = op.forward()
    def app1[X, Y](op: Op1[X, Y], x: Id[X]) = op.forward(x)
    def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Id[X1], x2: Id[X2]) = op.forward(x1, x2)
    def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Id[X1], x2: Id[X2], x3: Id[X3]) = op.forward(x1, x2, x3)
    def unroll[S[_], X](xs: Id[S[X]])(implicit unroll: Unroll[S, Id]) = unroll.unroll(xs)
  }

  class LiftedIsReal[D[_], R](implicit val D: Algebra[D], val R: IsReal[R]) extends IsReal[D[R]] {
    def zero = D.const(R.zero, "0")
    def one = D.const(R.one, "1")

    def uniformSample = ???
    def normalSample = ???

    def add(x: D[R], y: D[R]) = Add(x, y)
    def sub(x: D[R], y: D[R]) = Sub(x, y)
    def neg(x: D[R]) = Neg(x)
    def mul(x: D[R], y: D[R]) = Mul(x, y)
    def div(x: D[R], y: D[R]) = Div(x, y)
    def inv(x: D[R]) = Inv(x)
    def pi = D.const(R.pi, "π")
    def e = D.const(R.e, "e")
    def twoPi = D.const(R.twoPi, "2π")
    def exp(x: D[R]) = Exp(x)
    def log(x: D[R]) = Log(x)
    def expm1(x: D[R]) = Expm1(x)
    def log1p(x: D[R]) = Log1p(x)
    def abs(x: D[R]) = Abs(x)
    def sgn(x: D[R]) = ???
    def sin(x: D[R]) = Sin(x)
    def cos(x: D[R]) = Cos(x)
    def tan(x: D[R]) = Tan(x)
    def arcsin(x: D[R]) = ArcSin(x)
    def arccos(x: D[R]) = ArcCos(x)
    def arctan(x: D[R]) = ArcTan(x)
    def sqr(x: D[R]) = Sqr(x)
    def sqrt(x: D[R]) = Sqrt(x)
  }

}
