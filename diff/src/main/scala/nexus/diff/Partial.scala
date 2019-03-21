package nexus.diff

import nexus._
import nexus.diff.execution._

/**
 * Syntactic sugar for partial derivatives / gradients.
 */
case class ∂[D[_], Y](value: D[Y])

trait PartialOpsMixin {

  implicit class LossPartialOps[D[_]: DifferentiableAlgebra, Y: IsReal](y: ∂[D, Y]) {
    def /[X](x: ∂[D, X])(implicit F: Forward[D]): X = F.backward.compute(y.value).apply(x.value)
  }

}
