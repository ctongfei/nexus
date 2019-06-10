package nexus.diff.modules

import nexus.diff._
import nexus.diff.ops._
import nexus._

/**
 * A residual unit.
 *
 * Reference:
 * - K He, X Zhang, S Ren, J Sun (2016): Deep residual learning for image recognition. CVPR.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class ResidualUnit[X](residual: Func1[X, X])(implicit X: Grad[X]) extends Module1[X, X] {

  def apply[F[_] : Algebra](x: F[X]) = {
    val r = residual(x)
    Add.grad.apply(x, r)
  }
}

object ResidualUnit {

  def apply[X: Grad](residual: Func1[X, X]) =
    new ResidualUnit[X](residual)

  def apply[X: Grad](residual: PolyFunc1)(implicit f: residual.P[X, X]) =
    new ResidualUnit[X](residual.ground)

}
