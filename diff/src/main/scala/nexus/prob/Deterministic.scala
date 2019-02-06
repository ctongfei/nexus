package nexus.prob

import nexus._

/**
 * A deterministic stochastic variable.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Deterministic[X](value: X) extends Stochastic[X] {

  final def sample = value

}
