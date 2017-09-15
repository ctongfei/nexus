package nexus

/**
 * Any generic unary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type parameter [[F]].
 *
 * Essentially, this function can be applied to a symbolic expression of type [[Expr]]`[X]` '''if and only if''' an
 * implicit [[F]]`[X, Y]` is found, and the application returns a symbolic expression of type [[Expr]]`[Y]`.
 *
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @since 0.1.0
 */
trait PolyOp1[F[X, Y] <: Op1[X, Y]] {

  /** Applies this operation to a concrete value (forward computation). */
  def apply[X, Y](x: X)(implicit f: F[X, Y]): Y = f.forward(x)

  /** Applies this operation to a symbolic expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] = Apply1(f, x)
}


/**
 * Any generic binary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type parameter [[F]].
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @see [[PolyOp1]]
 * @since 0.1.0
 */
trait PolyOp2[F[X1, X2, Y] <: Op2[X1, X2, Y]] {
  def apply[X1, X2, Y](x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): Y = f.forward(x1, x2)

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] = Apply2(f, x1, x2)
}



/**
 * Any generic ternary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type parameter [[F]].
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @see [[PolyOp1]]
 * @since 0.1.0
 */
trait PolyOp3[F[X1, X2, X3, Y] <: Op3[X1, X2, X3, Y]] {
  def apply[X1, X2, X3, Y](x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): Y = f.forward(x1, x2, x3)

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = Apply3(f, x1, x2, x3)

}
