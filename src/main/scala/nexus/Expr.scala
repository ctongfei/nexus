package nexus

import nexus.util._

/**
 * Represents a symbolic expression in a computational graph.
 *
 * @tparam T Type of data that it conceptually holds
 * @since 0.1.0
 * @author Tongfei Chen
 */
sealed trait Expr[+T] {
  def computeGradient: Boolean
}

/**
 * A placeholder for models' inputs.
 * @param value Input to a model
 */
case class Input[+X](value: X, name: String = ExprName.next) extends Expr[X] {
  def computeGradient = false
  override def toString = name
}

/**
 * A constant value in a computational graph.
 * @param value Value of this constant
 */
case class Const[+X](value: X, name: String = ExprName.next) extends Expr[X] {
  def computeGradient = false
  override def toString = name
}

/**
 * A parameter of a model.
 * @param value Initial value of this parameter
 */
case class Param[+X](value: X, name: String) extends Expr[X] {
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
