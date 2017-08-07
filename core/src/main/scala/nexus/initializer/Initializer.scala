package nexus.initializer

import nexus._

/**
 * Initializer of neural network parameters.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Initializer[T[_, _ <: $$], D] {

  def env: Env[T, D]

  def initialize[A <: $$](x: T[D, A]): Unit

}
