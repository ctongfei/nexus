package nexus.diff

import nexus._
import nexus.diff.execution._

/**
 * An [[Algebra]] that supports reverse mode differentiation (backpropagation).
 * Witnesses a box where the element's type tag can be retrieved at runtime.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DifferentiableAlgebra[F[_]] extends Algebra[F] {

  /** Gets the type tag of a boxed computation node at runtime. */
  def tag[X](x: F[X]): Tag[X]

  def gradients[R](x: F[R])(implicit R: IsReal[R], F: Forward[F]) = F.backward.compute(x)

}
