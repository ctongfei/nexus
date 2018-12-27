package nexus.prob

import nexus._

/**
 * @author Tongfei Chen
 */
class Mixture[T[_], R, U <: Dim, A]
(val classDistribution: Categorical[T, R, U], val components: Seq[Stochastic[A]]) extends Stochastic[A] {

  def sample =
    components(classDistribution.sample).sample

}
