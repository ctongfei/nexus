package nexus

/**
 * Any generic unary function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type member `Op`.
 *
 * Essentially, this function can be applied to a symbolic expression of type [[Expr]]`[X]` '''if and only if''' an
 * implicit `Op[X, Y]` is found, and the application returns a symbolic expression of type [[Expr]]`[Y]`.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait PolyOp1 {

  /**
   * Type of the actual grounded operator.
   * This acts as a type constraint expressing what type of variables this polymorphic operation can apply to.
   */
  type Op[X, Y] <: Op1[X, Y]

  /** Applies this operation to a concrete value (forward computation). */
  def apply[X, Y](x: X)(implicit f: Op[X, Y]): Y = f.forward(x)

  /** Applies this operation to a symbolic expression. */
  def apply[X, Y](x: Expr[X])(implicit f: Op[X, Y]): Expr[Y] = Apply1(f, x)

}

/**
 * Any generic binary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type member `Op`.
 * @see [[PolyOp1]]
 * @since 0.1.0
 */
trait PolyOp2 {

  type Op[X1, X2, Y] <: Op2[X1, X2, Y]

  /** Applies this operation to concrete values (forward computation). */
  def apply[X1, X2, Y](x1: X1, x2: X2)(implicit f: Op[X1, X2, Y]): Y = f.forward(x1, x2)

  /** Applies this operation to symbolic expressions. */
  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: Op[X1, X2, Y]): Expr[Y] = Apply2(f, x1, x2)

}

/**
 * Any generic ternary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type member `Op`.
 * @see [[PolyOp1]]
 * @since 0.1.0
 */
trait PolyOp3 {

  type Op[X1, X2, X3, Y] <: Op3[X1, X2, X3, Y]

  def apply[X1, X2, X3, Y](x1: X1, x2: X2, x3: X3)(implicit f: Op[X1, X2, X3, Y]): Y = f.forward(x1, x2, x3)

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: Op[X1, X2, X3, Y]): Expr[Y] = Apply3(f, x1, x2, x3)

}
