package nexus.diff

import cats._
import nexus.diff.Algebra.IdAlgebra

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
 */
trait Algebra[D[_]] {

  type In[_]

  def input[X](input: In[X], name: String): D[X]

  /** Lifts a value into box `D`. */
  def const[X](value: X, name: String): D[X]

  /** Coerces a parameter (`Param[X]`) into box `D` (as `D[X]`). */
  def param[X](p: Param[X]): D[X]

  /** Put the result of a nullary operation into box `D`. */
  def app0[Y](op: Op0[Y]): D[Y]

  /** Applies a unary operation to a boxed node. */
  def app1[X, Y](op: Op1[X, Y], x: D[X]): D[Y]

  /** Applies a binary operation to two boxed nodes. */
  def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: D[X1], x2: D[X2]): D[Y]

  /** Applies a ternary operation to three boxed nodes. */
  def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: D[X1], x2: D[X2], x3: D[X3]): D[Y]

  def unroll[S[_], X](xs: D[S[X]])(implicit unroll: Unroll[S, D]): S[D[X]]

}


object Algebra {

  implicit object IdAlgebra extends Algebra[Id] {
    type In[X] = X
    def input[X](input: X, name: String) = input
    def const[X](value: X, name: String) = value
    def param[X](p: Param[X]) = p.value
    def app0[Y](op: Op0[Y]) = op.forward()
    def app1[X, Y](op: Op1[X, Y], x: Id[X]) = op.forward(x)
    def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Id[X1], x2: Id[X2]) = op.forward(x1, x2)
    def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Id[X1], x2: Id[X2], x3: Id[X3]) = op.forward(x1, x2, x3)
    def unroll[S[_], X](xs: Id[S[X]])(implicit unroll: Unroll[S, Id]) = unroll.unroll(xs)
  }

}
