package nexus

/**
 * Parametrized polymorphic unary differentiable function.
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @since 0.1.0
 * @author Tongfei Chen
 */
trait ParaPolyOp1[P, F[P, X, Y] <: (P => Op1[X, Y])] { self =>

  def parameter: P

  /** Applies this operation to a concrete value (forward computation). */
  def forward[X, Y](x: X)(implicit f: F[P, X, Y]): Y =
    f(parameter).forward(x)

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward[X, Y](dy: Y, y: Y, x: X)(implicit f: F[P, X, Y]): X =
    f(parameter).backward(dy, y, x)

  /** Given type parameters, resolves the type parameters of this operation. */
  def ground[X, Y](implicit f: F[P, X, Y]): Op1[X, Y] =
    f(parameter)

  /** Applies this operation to a symbolic expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[P, X, Y]) =
    Apply1(ground[X, Y], x)

}

/**
 * Parametrized polymorphic binary differentiable function.
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @since 0.1.0
 */
trait ParaPolyOp2[P, F[P, X1, X2, Y] <: (P => Op2[X1, X2, Y])] { self =>

  def parameter: P

  def forward[X1, X2, Y](x1: X1, x2: X2)(implicit f: F[P, X1, X2, Y]): Y =
    f(parameter).forward(x1, x2)

  def backward1[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[P, X1, X2, Y]): X1 =
    f(parameter).backward1(dy, y, x1, x2)

  def backward2[X1, X2, Y](dy: Y, y: Y, x1: X1, x2: X2)(implicit f: F[P, X1, X2, Y]): X2 =
    f(parameter).backward2(dy, y, x1, x2)

  def ground[X1, X2, Y](implicit f: F[P, X1, X2, Y]): Op2[X1, X2, Y] =
    f(parameter)

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[P, X1, X2, Y]) =
    Apply2(ground[X1, X2, Y], x1, x2)

}
