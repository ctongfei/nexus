package nexus

import cats._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.util._

/**
 * Represents a symbolic expression in a computational graph.
 * @tparam X Type of data that it conceptually holds
 * @since 0.1.0
 * @author Tongfei Chen
 */
sealed trait Expr[X] {

  def value[F[_]](implicit f: Expr ~> F) = f(this)

  def tag: Type[X]

  def requireGrad = tag.isInstanceOf[Grad[X]]

  /** Passes this expression through any function. */
  def |>[Y](f: Func1[X, Y]): Expr[Y] = f(this)

  /** Passes this expression through any polymorphic neural function. */
  def |>[Y](f: PolyFunc1)(implicit ff: f.F[X, Y]): Expr[Y] = f(this)

  /** Creates an assignment to this expression. */
  def <<-(value: X): Assignment = Assignment(this, value)

  /** Substitutes an input to an expression in this expression. */
  def substitute[E](ex: Input[E], e: Expr[E]): Expr[X] = this match {
    case x: Input[X] => if (x eq ex) e.asInstanceOf[Expr[X]] else x
    case x: Const[X] => x
    case x: Param[X] => x
    case Apply1(op, x) => Apply1(op, x.substitute(ex, e))
    case Apply2(op, x1, x2) => Apply2(op, x1.substitute(ex, e), x2.substitute(ex, e))
    case Apply3(op, x1, x2, x3) => Apply3(op, x1.substitute(ex, e), x2.substitute(ex, e), x3.substitute(ex, e))
  }
}

/**
 * A placeholder for models' inputs.
 */
case class Input[X](name: String = ExprName.nextInput) extends Expr[X] { self =>

  def tag = Type.nonDifferentiable[X]
  override def toString = name

  /** Constructs a neural function (lambda expression). */
  def =>>[Y](y: Expr[Y]): Func1[X, Y] = new Func1[X, Y] {
    def apply(x: Expr[X]) = y.substitute(self, x)
  }

}

/**
 * A parameter of a model.
 * @param value Initial value of this parameter
 */
case class Param[X](var value: X, name: String)(implicit val tag: Grad[X]) extends Expr[X] {
  override def toString = name

  def +=(g: X) = if (tag.mutable)
    tag.addI(value, g)
  else value = tag.add(value, g)

  def -=(g: X) = +=(-g)

}


/**
 * A constant value in a computational graph.
 * @param value Value of this constant
 */
case class Const[X](value: X, name: String = ExprName.nextConst)(implicit val tag: Type[X]) extends Expr[X] {
  override def toString = name
}

/**
 * The result of the application of a unary function to an expression.
 */
case class Apply1[X, Y](op: Op1[X, Y], x: Expr[X]) extends Expr[Y] {
  type Input = X
  val tag = op.tag(x.tag)
  override def toString = s"${op.name}($x)"
}

/**
 * The result of the application of a binary function to two expressions.
 */
case class Apply2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Expr[X1], x2: Expr[X2]) extends Expr[Y] {
  type Input1 = X1
  type Input2 = X2
  val tag = op.tag(x1.tag, x2.tag)
  override def toString = s"${op.name}($x1, $x2)"
}

/**
 * The result of the application of a ternary function to three expressions.
 */
case class Apply3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) extends Expr[Y] {
  type Input1 = X1
  type Input2 = X2
  type Input3 = X3
  val tag = op.tag(x1.tag, x2.tag, x3.tag)
  override def toString = s"${op.name}($x1, $x2, $x3)"
}
