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
trait PolyOp1[F[X, Y] <: Op1[X, Y]] { self =>

  /** Applies this operation to a concrete value (forward computation). */
  def forward[X, Y](x: X)(implicit f: F[X, Y]): Y = f.forward(x)

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward[X, Y](dy: Y, y: Y, x: X)(implicit f: F[X, Y]): X = f.backward(dy, y, x)

  /** Given type parameters, resolves the type parameters of this operation. */
  def ground[X, Y](implicit f: F[X, Y]): Op1[X, Y] = f

  /** Applies this operation to a symbolic expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] = Apply1(ground[X, Y], x)
}


/**
 * Any generic binary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type parameter [[F]].
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @since 0.1.0
 */
trait PolyOp2[F[X1, X2, Y] <: Op2[X1, X2, Y]] { self =>
  def forward[X1, X2, Y](x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): Y = f.forward(x1, x2)
  def backward1[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): X1 = f.backward1(dy, y, x1, x2)
  def backward2[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): X2 = f.backward2(dy, y, x1, x2)
  def ground[X1, X2, Y](implicit f: F[X1, X2, Y]): Op2[X1, X2, Y] = f
  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] = Apply2(ground[X1, X2, Y], x1, x2)
}


trait PolyOp3[F[X1, X2, X3, Y] <: Op3[X1, X2, X3, Y]] { self =>
  def forward[X1, X2, X3, Y](x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): Y = f.forward(x1, x2, x3)
  def backward1[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): X1 = f.backward1(dy, y, x1, x2, x3)
  def backward2[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): X2 = f.backward2(dy, y, x1, x2, x3)
  def backward3[X1, X2, X3, Y](dy: Y, y: Y, x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): X3 = f.backward3(dy, y, x1, x2, x3)
  def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]): Op3[X1, X2, X3, Y] = f
  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = Apply3(ground[X1, X2, X3, Y], x1, x2, x3)

}
