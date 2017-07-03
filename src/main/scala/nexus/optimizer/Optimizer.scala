package nexus.optimizer

import nexus._
import nexus.exec._

/**
 * Base trait for optimizers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Optimizer[T[_, _ <: $$], D] {

  def env: Env[T, D]

  def update(gradients: Values[T, D]): Unit

}
