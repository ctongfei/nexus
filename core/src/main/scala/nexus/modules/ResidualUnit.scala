package nexus.modules

import nexus._
import nexus.algebra._
import nexus.ops._

/**
 * A residual unit.
 *
 * Reference:
 * - K He, X Zhang, S Ren, J Sun (2016): Deep residual learning for image recognition. CVPR.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ResidualUnit[X](val residual: Func1[X, X])(implicit X: Grad[X]) extends Module1[X, X] {

  def parameters = residual match {
    case residual: Module1[X, X] => residual.parameters
    case _ => Set[Param[_]]()
  }

  def apply(x: Expr[X]): Expr[X] = {
    val r = residual(x)
    Add(x, r)
  }

}

object ResidualUnit {

  def apply[X: Grad](residual: Func1[X, X]) =
    new ResidualUnit[X](residual)

  def apply[X: Grad](residual: PolyFunc1)(implicit f: residual.F[X, X]) =
    new ResidualUnit[X](residual.ground)

}
