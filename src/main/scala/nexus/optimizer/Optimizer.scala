package nexus.optimizer

import nexus._
import nexus.autodiff._

/**
 * Base trait for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Optimizer {

  def parameters: ValueStore

  def update(gradients: ValueStore): Unit

}
