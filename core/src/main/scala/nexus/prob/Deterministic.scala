package nexus.prob

/**
 * Represents a deterministic stochastic variable.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Deterministic[A](value: A) extends Stochastic[A] {
  final def sample = value
}
