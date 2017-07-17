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
