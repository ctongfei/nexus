package nexus.diff

import nexus._

/**
 * Represents a lazy symbolic expression in a computational graph,
 * whose actual value is deferred until forward computation.
 * @tparam X Type of data that it conceptually holds
 * @since 0.1.0
 * @author Tongfei Chen
 */
trait Symbolic[X] {
  import Symbolic._

  type Data = X

  /** Type tag of this expression. */
  def tag: Tag[X]

  /** Will the gradient of this expression be computed when performing backward computation? */
  def requireGrad: Boolean

  /** Creates an assignment to this symbolic expression. */
  def :=(value: X): Assignment[Symbolic] = Assignment(this, value)

  /** Substitutes an input to an expression in this expression. */
  def substitute[E](a: Input[E], b: Symbolic[E]): Symbolic[X] = this match {
    case x: Input[X] => if (x eq a) b.asInstanceOf[Symbolic[X]] else x // this cast is safe: (x eq a) proves that E =:= X
    case x: Const[X] => x
    case x: Param[X] => x
    case App1(op, x) => App1(op, x.substitute(a, b))
    case App2(op, x1, x2) => App2(op, x1.substitute(a, b), x2.substitute(a, b))
    case App3(op, x1, x2, x3) => App3(op, x1.substitute(a, b), x2.substitute(a, b), x3.substitute(a, b))
  }
}

object Symbolic {

  implicit object Algebra extends DifferentiableAlgebra[Symbolic] {
    type In[X] = Input[X]
    def forTraining = true
    def tag[X](x: Symbolic[X]) = x.tag
    def input[X](input: Input[X], name: String = "") = new Input[X](name)
    def const[X](value: X, name: String = "") = new Const(value, name)

    def app0[Y](op: Op0[Y]) = App0(op)
    def app1[X, Y](op: Op1[X, Y], x: Symbolic[X]) = App1(op, x)
    def app2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Symbolic[X1], x2: Symbolic[X2]) = App2(op, x1, x2)
    def app3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Symbolic[X1], x2: Symbolic[X2], x3: Symbolic[X3]) = App3(op, x1, x2, x3)

    def fromParam[X](p: Param[X]) = p
    def getParam[X](p: Symbolic[X]) = p match {
      case p: Param[X] => Some(p)
      case _ => None
    }
  }

  case class App0[Y](op: Op0[Y]) extends Symbolic[Y] {
    type Output = Y
    def tag = op.tag
    final def requireGrad = false  // nothing to backpropagate!
    override def toString = s"${op.name}()"
  }

  /**
   * The result of the application of a unary function to an expression.
   */
  case class App1[X, Y](op: Op1[X, Y], x: Symbolic[X]) extends Symbolic[Y] {
    type Input = X
    type Output = Y
    val requireGrad = op.differentiable && x.requireGrad
    def tag = op.tag
    override def toString = s"${op.name}($x)"
  }

  /**
   * The result of the application of a binary function to two expressions.
   */
  case class App2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Symbolic[X1], x2: Symbolic[X2]) extends Symbolic[Y] {
    type Input1 = X1
    type Input2 = X2
    type Output = Y

    val requireGrad = op.differentiable && (x1.requireGrad || x2.requireGrad)
    def tag = op.tag
    override def toString = s"${op.name}($x1, $x2)"
  }

  /**
   * The result of the application of a ternary function to three expressions.
   */
  case class App3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Symbolic[X1], x2: Symbolic[X2], x3: Symbolic[X3]) extends Symbolic[Y] {
    type Input1 = X1
    type Input2 = X2
    type Input3 = X3
    type Output = Y

    val requireGrad = op.differentiable && (x1.requireGrad || x2.requireGrad || x3.requireGrad)
    def tag = op.tag
    override def toString = s"${op.name}($x1, $x2, $x3)"
  }

}
