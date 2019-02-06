package nexus.diff

import cats._

/**
 * PyTorch-style eagerly-executed traced computation node.
 * @author Tongfei Chen
 */
trait Traced[X] {

  /** Type tag of this expression. */
  def tag: Tag[X]

  /** Will the gradient of this expression be computed when performing backward computation? */
  def requireGrad: Boolean

  def value: X

}

object Traced {

  implicit object Algebra extends Algebra[Traced] {
    type In[X] = X
    def input[X](input: X, name: String): Traced[X] = new Const(input, name)
    def const[X](value: X, name: String): Traced[X] = new Const(value, name)
    def param[X](p: Param[X]): Traced[X] = p
    def app0[Y](op: Op0[Y]): Traced[Y] = App0(op)
    def app1[X, Y](op: Op1[X, Y], x: Traced[X]): Traced[Y] = App1(op, x)
    def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Traced[X1], x2: Traced[X2]): Traced[Y] = App2(op, x1, x2)
    def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Traced[X1], x2: Traced[X2], x3: Traced[X3]): Traced[Y] = App3(op, x1, x2, x3)
    def unroll[S[_], X](xs: Traced[S[X]])(implicit unroll: Unroll[S, Traced]) = ???
  }


  implicit object Interpreter extends (Traced ~> Id) {
    def apply[A](ta: Traced[A]) = ta.value
  }

  case class App0[Y](op: Op0[Y]) extends Traced[Y] {
    val value = op.forward()
    def tag = op.tag
    final def requireGrad = false  // nothing to backpropagate!
  }

  case class App1[X, Y](op: Op1[X, Y], x: Traced[X]) extends Traced[Y] {
    val value = op.forward(x.value)
    def tag = op.tag
    def requireGrad = op.differentiable && x.requireGrad
  }

  case class App2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Traced[X1], x2: Traced[X2]) extends Traced[Y] {
    val value = op.forward(x1.value, x2.value)
    def tag = op.tag
    def requireGrad = op.differentiable && (x1.requireGrad || x2.requireGrad)
  }

  case class App3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Traced[X1], x2: Traced[X2], x3: Traced[X3]) extends Traced[Y] {
    val value = op.forward(x1.value, x2.value, x3.value)
    val requireGrad = op.differentiable && (x1.requireGrad || x2.requireGrad || x3.requireGrad)
    def tag = op.tag
  }

}
