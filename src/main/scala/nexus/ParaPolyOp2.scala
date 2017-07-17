package nexus

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
