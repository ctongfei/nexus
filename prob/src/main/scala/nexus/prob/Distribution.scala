package nexus.prob

/**
 * @author Tongfei Chen
 */
trait Distribution[A, R] extends Stochastic[A] {

  def prob(x: A): R

  def logProb(x: A): R

}
