package nexus.diff

import nexus._
import nexus.diff.execution._

/**
 * Witnesses a box where the element's type tag can be retrieved at runtime.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DifferentiableAlgebra[D[_]] extends Algebra[D] {

  /** Gets the type tag of a boxed computation node at runtime. */
  def tag[X](x: D[X]): Tag[X]

  def gradients[R](x: D[R])(implicit R: IsReal[R], F: Forward[D]) = F.backward.compute(x)

}
