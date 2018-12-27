package nexus.prob

import nexus._

/**
 * @author Tongfei Chen
 */
class Bernoulli[Z, R](p: R)(implicit Z: IsInt[Z], R: IsReal[R]) extends Stochastic[Z] with Distribution[Z, R] {
  def sample = ???
  def prob(x: Z) = ???
  def logProb(x: Z) = ???
}
