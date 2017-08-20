package nexus

import nexus.impl._
import nexus.util._

/**
 * Represents a symbolic expression in a computational graph.
 * @tparam X Type of data that it conceptually holds
 * @since 0.1.0
 * @author Tongfei Chen
 */
sealed trait Expr[X] {

  /** Passes this expression through any function. */
  def |>[Y]
  (f: Module[X, Y]): Expr[Y] =
    f(this)

  def |>[Y]
  (f: DModule[X, Y]): DExpr[Y] =
    f(this)

  def |>[Y]
  (f: Op1[X, Y]): Expr[Y] = f(this)

  /** Passes this expression through any polymorphic neural function. */
  def |>[F[X, Y] <: Op1[X, Y], Y]
  (op: PolyOp1[F])
  (implicit f: F[X, Y]): Expr[Y] =
    f(this)

  /** Passes this expression through any parametrized polymorphic neural function. */
  def |>[F[P, X, Y] <: (P => Op1[X, Y]), P, Y]
  (op: ParaPolyOp1[P, F])
  (implicit f: F[P, X, Y]): Expr[Y] =
    f(op.parameter)(this)

  /**
   * Creates an assignment to this expression.
   */
  def <<-(value: X) = Assignment(this, value)

  def substitute[A](ax: Input[A], a: Expr[A]): Expr[X] = this match {
    case x: Input[X] => if (x == ax) a.asInstanceOf[Expr[X]] else x
    case x: Const[X] => x
    case x: Param[X] => x
    case Apply1(op, x) => Apply1(op, x.substitute(ax, a))
    case Apply2(op, x1, x2) => Apply2(op, x1.substitute(ax, a), x2.substitute(ax, a))
    case Apply3(op, x1, x2, x3) => Apply3(op, x1.substitute(ax, a), x2.substitute(ax, a), x3.substitute(ax, a))
    case DApply1(op, x) => DApply1(op, x.substitute(ax, a))
    case DApply2(op, x1, x2) => DApply2(op, x1.substitute(ax, a), x2.substitute(ax, a))
    case DApply3(op, x1, x2, x3) => DApply3(op, x1.substitute(ax, a), x2.substitute(ax, a), x3.substitute(ax, a))
  }
}

/**
 * Represents an expression in a computational graph
 * whose gradient would be computed in backward computation.
 */
sealed trait DExpr[X] extends Expr[X] {

  def gradOps: GradOps[X]

  def |>[Y]
  (f: DOp1[X, Y]): DExpr[Y] = f(this)

  def |>[F[X, Y] <: DOp1[X, Y], Y]
  (op: PolyDOp1[F])
  (implicit f: F[X, Y]): DExpr[Y] =
    f(this)

  def |>[F[P, X, Y] <: (P => DOp1[X, Y]), P, Y]
  (op: ParaPolyDOp1[P, F])
  (implicit f: F[P, X, Y]): DExpr[Y] =
    f(op.parameter)(this)

}

/**
 * A placeholder for models' inputs.
 */
case class Input[X](name: String = ExprName.nextInput) extends Expr[X] { self =>

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
case class Param[X](var value: X, name: String)(val gradOps: GradOps[X]) extends DExpr[X] {
  override def toString = name

  def +=(g: X) = if (gradOps.mutable)
    gradOps.addI(value, g)
  else value = gradOps.add(value, g)

}

object Param {

  def apply[T[_ <: $$], D, A <: $$](value: T[A], name: String)(implicit ops: TypedRealTensorOps[T, D]) = new Param(value, name)(ops.ground[A])

}

/**
 * A constant value in a computational graph.
 * @param value Value of this constant
 */
case class Const[X](value: X, name: String = ExprName.nextConst) extends Expr[X] {
  override def toString = name
}

/**
 * The result of the application of a unary function to an expression.
 */
case class Apply1[X, Y](op: Op1[X, Y], x: Expr[X]) extends Expr[Y] {
  override def toString = s"${op.name}($x)"
}

/**
 * The result of the application of a binary function to two expressions.
 */
case class Apply2[X1, X2, Y](op: Op2[X1, X2, Y], x1: Expr[X1], x2: Expr[X2]) extends Expr[Y] {
  override def toString = s"${op.name}($x1, $x2)"
}

/**
 * The result of the application of a ternary function to three expressions.
 */
case class Apply3[X1, X2, X3, Y](op: Op3[X1, X2, X3, Y], x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) extends Expr[Y] {
  override def toString = s"${op.name}($x1, $x2, $x3)"
}

/**
 * The result of the application of a unary differentiable function to an expression.
 * Gradient of this expression would be computed in backward computation.
 */
case class DApply1[X, Y](op: DOp1[X, Y], x: Expr[X]) extends DExpr[Y] {
  def gradOps = op.gradOps
  override def toString = s"${op.name}($x)"
}

/**
 * The result of the application of a binary differentiable function to two expressions.
 * Gradient of this expression would be computed in backward computation.
 */
case class DApply2[X1, X2, Y](op: DOp2[X1, X2, Y], x1: Expr[X1], x2: Expr[X2]) extends DExpr[Y] {
  def gradOps = op.gradOps
  override def toString = s"${op.name}($x1, $x2)"
}

/**
 * The result of the application of a ternary differentiable function to three expressions.
 * Gradient of this expression would be computed in backward computation.
 */
case class DApply3[X1, X2, X3, Y](op: DOp3[X1, X2, X3, Y], x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) extends DExpr[Y] {
  def gradOps = op.gradOps
  override def toString = s"${op.name}($x1, $x2, $x3)"
}
