package nexus.diff

import nexus._
import nexus.diff.execution._

/**
 * Syntactic sugar for partial derivatives / gradients.
 * @example {{{ ∂(loss) / ∂(param) }}}
 */
case class ∂[F[_], Y](value: F[Y])

trait PartialOpsMixin {

  implicit class LossPartialOps[F[_]: DifferentiableAlgebra, Y: IsReal](y: ∂[F, Y]) {

    /**
     * Computes the gradient between a scalar and a value of any differentiable type.
     */
    def /[X](x: ∂[F, X])(implicit F: Forward[F]): X = {  //TODO: subject to change
      val gradients = F.backward.compute(y.value)
      gradients(x.value)
    }
  }

}
