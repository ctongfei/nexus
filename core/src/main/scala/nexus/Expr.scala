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
  def |>[Y](f: Expr[X] => Expr[Y]): Expr[Y] =
    f(this)

  def |>[Y](f: Op1[X, Y]): Expr[Y] = f(this)

  /** Passes this expression through any polymorphic neural function. */
  def |>[Y](op: PolyOp1)(implicit f: op.F[X, Y]): Expr[Y] =
    f(this)

  def |>[Y](op: PolyDOp1)(implicit f: op.F[X, Y]): Expr[Y] =
    f(this)

  /** Passes this expression through any parametrized polymorphic neural function. */
  def |>[P, Y](op: ParaPolyOp1[P])(implicit f: op.F[P, X, Y]): Expr[Y] =
    f(op.parameter)(this)

  /**
   * Creates an assignment to this expression.
   */
  def <<-(value: X): Assignment = Assignment(this, value)

  def substitute[A](ax: Input[A], a: Expr[A]): Expr[X] = this match {
    case x: Input[X] => if (x eq ax) a.asInstanceOf[Expr[X]] else x
    case x: Const[X] => x
    case x: Param[X] => x
    case Apply1(op, x) => Apply1(op, x.substitute(ax, a))
    case Apply2(op, x1, x2) => Apply2(op, x1.substitute(ax, a), x2.substitute(ax, a))
    case Apply3(op, x1, x2, x3) => Apply3(op, x1.substitute(ax, a), x2.substitute(ax, a), x3.substitute(ax, a))
  }
}

/**
 * A placeholder for models' inputs.
 */
case class Input[X](name: String = ExprName.nextInput) extends Expr[X] { self =>

  def tag = Type.empty[X]
  override def toString = name

  /** Constructs a neural function (lambda expression). */
  def =>>[Y](y: Expr[Y]): Module[X, Y] = new Module[X, Y] {
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
  def tag = op.tag
  override def toString = s"${op.name}($x)"
}

/**
 * The result of the application of a binary function to two expressions.
 */
case class Apply2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Expr[X1], x2: Expr[X2]) extends Expr[Y] {
  type Input1 = X1
  type Input2 = X2
  def tag = op.tag
  override def toString = s"${op.name}($x1, $x2)"
}

/**
 * The result of the application of a ternary function to three expressions.
 */
case class Apply3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) extends Expr[Y] {
  type Input1 = X1
  type Input2 = X2
  type Input3 = X3
  def tag = op.tag
  override def toString = s"${op.name}($x1, $x2, $x3)"
}
