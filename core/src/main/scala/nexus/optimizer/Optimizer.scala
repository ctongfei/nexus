package nexus.optimizer

import nexus._
import nexus.exec._

/**
 * Base trait for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Optimizer {

  def update(gradients: Values): Unit

}
