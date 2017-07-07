package nexus

import nexus.util._

sealed trait GenExpr

/**
 * Represents a symbolic expression in a computational graph.
 * @tparam X Type of data that it conceptually holds
 * @since 0.1.0
 * @author Tongfei Chen
 */
sealed trait Expr[+X] extends GenExpr {
  def computeGradient: Boolean

  def |>[X1 >: X, Y](f: Module[X1, Y]): Expr[Y] = f(this)

  def |>[F[x, y] <: Op1[x, y], X1 >: X, Y](op: GenOp1[F])(implicit f: F[X1, Y]): Expr[Y] = f(this)
}

/**
 * A placeholder for models' inputs.
 */
case class Input[X](name: String = ExprName.nextInput) extends Expr[X] {
  def computeGradient = false
  override def toString = name

  def =>>[Y](y: Expr[Y]): Module[X, Y] = ???
}

/**
 * A constant value in a computational graph.
 * @param value Value of this constant
 */
case class Const[X](value: X, name: String = ExprName.nextConst) extends Expr[X] {
  def computeGradient = false
  override def toString = name
}

/**
 * A parameter of a model.
 * @param value Initial value of this parameter
 */
case class Param[X](value: X, name: String) extends Expr[X] {
  def computeGradient = true
  override def toString = name
}

/**
 * The result of the application of a unary function to an expression.
 */
case class Apply1[X, Y](op: Op1[X, Y], x: Expr[X]) extends Expr[Y] {
  def computeGradient = true
  override def toString = s"${op.name}($x)"
}

/**
 * The result of the application of a binary function to two expressions.
 */
case class Apply2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Expr[X1], x2: Expr[X2]) extends Expr[Y] {
  def computeGradient = true
  override def toString = s"${op.name}($x1, $x2)"
}

/**
 * The result of the application of a ternary function to three expressions.
 */
case class Apply3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) extends Expr[Y] {
  def computeGradient = true
  override def toString = s"${op.name}($x1, $x2, $x3)"
}
