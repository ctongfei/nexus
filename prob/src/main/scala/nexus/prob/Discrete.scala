package nexus.prob

import nexus._
import nexus.syntax._

/**
 * A discrete probabilistic variable whose probability mass can be obtained at any point in its support.
 * @author Tongfei Chen
 */
trait Discrete[X, R] extends Probabilistic[X, R] {

  import Discrete._

  /** Gets the probability mass at a specific point. */
  def pmf: X => R

  /** Gets the log probability mass at a specific point. */
  def logPmf: X => R

  def product[Y](that: Discrete[Y, R])(implicit R: IsReal[R]) = new Product[X, Y, R](this, that)

  def prob = pmf
  def logProb = logPmf
}

object Discrete {
  class Product[X, Y, R: IsReal](self: Discrete[X, R], that: Discrete[Y, R]) extends Discrete[(X, Y), R] {
    val pmf = { case (x, y) => self.pmf(x) * that.pmf(y) }
    override val logPmf = { case (x, y) => self.logPmf(x) + that.logPmf(y) }
  }

}

trait DiscreteStochastic[X, R] extends Discrete[X, R] with Stochastic[X]
