package nexus

/**
 * @author Tongfei Chen
 */
trait ArgOp1[Arg, X, Y] extends (Arg => Op1[X, Y])

/**
 * Any generic unary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type parameter [[F]].
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @since 0.1.0
 */
trait ArgGenOp1[F[Arg, X, Y] <: ArgOp1[Arg, X, Y]] { self =>

  /** Applies this operation to a concrete value (forward computation). */
  def forward[Arg, X, Y](arg: Arg)(x: X)(implicit f: F[Arg, X, Y]): Y = f(arg).forward(x)

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward[Arg, X, Y](arg: Arg)(dy: Y, y: Y, x: X)(implicit f: F[Arg, X, Y]): X = f(arg).backward(dy, y, x)

  /** Given type parameters, resolves the type parameters of this operation. */
  def ground[Arg, X, Y](arg: Arg)(implicit f: F[Arg, X, Y]): Op1[X, Y] = f(arg)

  /** Applies this operation to a symbolic expression. */
  def apply[Arg, X, Y](arg: Arg)(x: Expr[X])(implicit f: F[Arg, X, Y]) = Apply1(ground[Arg, X, Y](arg), x)
}
