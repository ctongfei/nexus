package nexus

import nexus.algebra._
import nexus.algebra.typelevel.util._
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
case class Param[X](var value: X, name: String)(implicit val gradOps: GradOps[X]) extends DExpr[X] {
  override def toString = name

  def +=(g: X) = if (gradOps.mutable)
    gradOps.addI(value, g)
  else value = gradOps.add(value, g)

  def -=(g: X) = +=(gradOps.neg(g))

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
trait Apply1[Y] extends Expr[Y] {
  type X
  val op: Op1[X, Y]
  val x: Expr[X]
  override def toString = s"${op.name}($x)"
}

object Apply1 {
  def unapply[Y](e: Apply1[Y]): Option[(Op1[e.X, Y], Expr[e.X])] = Some(e.op, e.x)
  def apply[_X, Y](_op: Op1[_X, Y], _x: Expr[_X]): Apply1[Y] = new Apply1[Y] {
    type X = _X
    val op = _op
    val x = _x
  }

}

/**
 * The result of the application of a binary function to two expressions.
 */
trait Apply2[Y] extends Expr[Y] {
  type X1
  type X2
  val op: Op2[X1, X2, Y]
  val x1: Expr[X1]
  val x2: Expr[X2]
  override def toString = s"${op.name}($x1, $x2)"
}

object Apply2 {
  def unapply[Y](e: Apply2[Y]): Option[(Op2[e.X1, e.X2, Y], Expr[e.X1], Expr[e.X2])] = Some(e.op, e.x1, e.x2)
  def apply[_X1, _X2, Y](_op: Op2[_X1, _X2, Y], _x1: Expr[_X1], _x2: Expr[_X2]): Apply2[Y] = new Apply2[Y] {
    type X1 = _X1
    type X2 = _X2
    val op = _op
    val x1 = _x1
    val x2 = _x2
  }
}

/**
 * The result of the application of a ternary function to three expressions.
 */
trait Apply3[Y] extends Expr[Y] {
  type X1
  type X2
  type X3
  val op: Op3[X1, X2, X3, Y]
  val x1: Expr[X1]
  val x2: Expr[X2]
  val x3: Expr[X3]
  override def toString = s"${op.name}($x1, $x2, $x3)"
}

object Apply3 {
  def unapply[Y](e: Apply3[Y]): Option[(Op3[e.X1, e.X2, e.X3, Y], Expr[e.X1], Expr[e.X2], Expr[e.X3])] = Some(e.op, e.x1, e.x2, e.x3)
  def apply[_X1, _X2, _X3, Y](_op: Op3[_X1, _X2, _X3, Y], _x1: Expr[_X1], _x2: Expr[_X2], _x3: Expr[_X3]): Apply3[Y] = new Apply3[Y] {
    type X1 = _X1
    type X2 = _X2
    type X3 = _X3
    val op = _op
    val x1 = _x1
    val x2 = _x2
    val x3 = _x3
  }
}

/**
 * The result of the application of a unary differentiable function to an expression.
 * Gradient of this expression would be computed in backward computation.
 */
trait DApply1[Y] extends DExpr[Y] {
  type X
  val op: DOp1[X, Y]
  val x: Expr[X]
  def gradOps = op.gradOps
  override def toString = s"${op.name}($x)"
}

object DApply1 {
  def unapply[Y](e: DApply1[Y]): Option[(DOp1[e.X, Y], Expr[e.X])] = Some(e.op, e.x)
  def apply[_X, Y](_op: DOp1[_X, Y], _x: Expr[_X]): DApply1[Y] = new DApply1[Y] {
    type X = _X
    val op = _op
    val x = _x
  }

}

/**
 * The result of the application of a binary differentiable function to two expressions.
 * Gradient of this expression would be computed in backward computation.
 */
trait DApply2[Y] extends DExpr[Y] {
  type X1
  type X2
  val op: DOp2[X1, X2, Y]
  val x1: Expr[X1]
  val x2: Expr[X2]
  def gradOps = op.gradOps
  override def toString = s"${op.name}($x1, $x2)"
}

object DApply2 {
  def unapply[Y](e: DApply2[Y]): Option[(DOp2[e.X1, e.X2, Y], Expr[e.X1], Expr[e.X2])] = Some(e.op, e.x1, e.x2)
  def apply[_X1, _X2, Y](_op: DOp2[_X1, _X2, Y], _x1: Expr[_X1], _x2: Expr[_X2]): DApply2[Y] = new DApply2[Y] {
    type X1 = _X1
    type X2 = _X2
    val op = _op
    val x1 = _x1
    val x2 = _x2
  }
}


/**
 * The result of the application of a ternary differentiable function to three expressions.
 * Gradient of this expression would be computed in backward computation.
 */
trait DApply3[Y] extends DExpr[Y] {
  type X1
  type X2
  type X3
  val op: DOp3[X1, X2, X3, Y]
  val x1: Expr[X1]
  val x2: Expr[X2]
  val x3: Expr[X3]
  def gradOps = op.gradOps
  override def toString = s"${op.name}($x1, $x2, $x3)"
}

object DApply3 {
  def unapply[Y](e: DApply3[Y]): Option[(DOp3[e.X1, e.X2, e.X3, Y], Expr[e.X1], Expr[e.X2], Expr[e.X3])] = Some(e.op, e.x1, e.x2, e.x3)
  def apply[_X1, _X2, _X3, Y](_op: DOp3[_X1, _X2, _X3, Y], _x1: Expr[_X1], _x2: Expr[_X2], _x3: Expr[_X3]): DApply3[Y] = new DApply3[Y] {
    type X1 = _X1
    type X2 = _X2
    type X3 = _X3
    val op = _op
    val x1 = _x1
    val x2 = _x2
    val x3 = _x3
  }
}
