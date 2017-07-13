package nexus

/**
 * @author Tongfei Chen
 */
trait ArgOp1[Arg, X, Y] extends (Arg => Op1[X, Y])

/**
 * Polymorphic unary neural (differential) function with argument.
 * @tparam F Type constraint expressing what type of variables this operation can apply to
 * @since 0.1.0
 */
trait ArgPolyOp1[Arg, F[Arg, X, Y] <: ArgOp1[Arg, X, Y]] { self =>

  def arg: Arg

  /** Applies this operation to a concrete value (forward computation). */
  def forward[X, Y](x: X)(implicit f: F[Arg, X, Y]): Y = f(arg).forward(x)

  /**
   * Performs gradient backpropagation.
   * @param dy Gradient of ''y'': ∇y
   * @param y Value of ''y''
   * @param x Value of ''x''
   * @return Gradient of ''x'': ∇x
   */
  def backward[X, Y](dy: Y, y: Y, x: X)(implicit f: F[Arg, X, Y]): X = f(arg).backward(dy, y, x)

  /** Given type parameters, resolves the type parameters of this operation. */
  def ground[X, Y](implicit f: F[Arg, X, Y]): Op1[X, Y] = f(arg)

  /** Applies this operation to a symbolic expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[Arg, X, Y]) = Apply1(ground[X, Y], x)
}
