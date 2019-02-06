package nexus.prob

/**
 * @author Tongfei Chen
 */
trait ContinuousDistribution[X, R] extends Stochastic[X] {

  /** Gets the probability density at a specific point. */
  def pdf(x: X): R

  /** Gets the log probability density at a specific point. */
  def logPdf(x: X): R

}

trait DiscreteDistribution[X, R] extends Stochastic[X] {

  /** Gets the probability mass at a specific point. */
  def pmf(x: X): R

  /** Gets the log probability mass at a specific point. */
  def logPmf(x: X): R

}
