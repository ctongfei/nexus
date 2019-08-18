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
 * @tparam F Type of box for interpreting a differentiable program.
 *           This could be either [[Symbolic]] (for lazy computation)
 *           or [[Traced]] (for eager computation). If `F =:= `[[Id]],
 *           the program being interpreted loses the ability to perform
 *           differentiation (back-propagation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Algebra[F[_]] {

  import Algebra._

  /** Type of input to the neural program that this algebra accepts. */
  type In[_]

  def forTraining: Boolean

  /** Converts an input to a node in this differential program. */
  def input[X](input: In[X], name: String): F[X]

  /** Lifts a value into box. */
  def const[X](value: X, name: String): F[X]

  /** Put the result of a nullary operation into box. */
  def app0[Y](op: Op0[Y]): F[Y]

  /** Applies a unary operation to a boxed node. */
  def app1[X, Y](op: Op1[X, Y], x: F[X]): F[Y]

  /** Applies a binary operation to two boxed nodes. */
  def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: F[X1], x2: F[X2]): F[Y]

  /** Applies a ternary operation to three boxed nodes. */
  def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: F[X1], x2: F[X2], x3: F[X3]): F[Y]

  /** Coerces a parameter (`Param[X]`) into box `F` (as `F[X]`). */
  def fromParam[X](p: Param[X]): F[X]

  /** Unpacks a parameter from a box. */
  def getParam[X](p: F[X]): Option[Param[X]]

  object Param {
    def apply[X](p: Param[X]): F[X] = fromParam(p)
    def unapply[X](p: F[X]): Option[Param[X]] = getParam(p)
  }

  def liftReal[R: IsReal]: IsReal[F[R]] = ???
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
//
//  class LiftedIsReal[F[_], R](implicit val F: Algebra[F], val R: IsReal[R]) extends IsReal[F[R]] {
//    def zero = F.const(R.zero, "0")
//    def one = F.const(R.one, "1")
//    def pi = F.const(R.pi, "π")
//    def e = F.const(R.e, "e")
//    def twoPi = F.const(R.twoPi, "2π")
//
//    def sampleFromStandardUniform = ???
//    def sampleFromStandardNormal = ???
//
//    def add(x: F[R], y: F[R]) = Add(x, y)
//    def sub(x: F[R], y: F[R]) = Sub(x, y)
//    def neg(x: F[R]) = Neg(x)
//    def mul(x: F[R], y: F[R]) = Mul(x, y)
//    def div(x: F[R], y: F[R]) = Div(x, y)
//    def recip(x: F[R]) = Inv(x)
//    def exp(x: F[R]) = Exp(x)
//    def log(x: F[R]) = Log(x)
//    def expm1(x: F[R]) = Expm1(x)
//    def log1p(x: F[R]) = Log1p(x)
//    def abs(x: F[R]) = Abs(x)
//    def sgn(x: F[R]) = ???
//    def sin(x: F[R]) = Sin(x)
//    def cos(x: F[R]) = Cos(x)
//    def tan(x: F[R]) = Tan(x)
//    def arcsin(x: F[R]) = ArcSin(x)
//    def arccos(x: F[R]) = ArcCos(x)
//    def arctan(x: F[R]) = ArcTan(x)
//    def sqr(x: F[R]) = Sqr(x)
//    def sqrt(x: F[R]) = Sqrt(x)
//  }

}
