package nexus.prob

import nexus._
import nexus.syntax._

/**
 * A continuous probabilistic variable whose probability density can be obtained at any point in its support.
 * @author Tongfei Chen
 */
trait Continuous[X, R] extends Probabilistic[X, R] {

  /** Gets the probability density at a specific point. */
  def pdf: X => R

  /** Gets the log probability density at a specific point. */
  def logPdf: X => R

  def prob = pdf
  def logProb = logPdf
}

object Continuous {
  class Product[X, Y, R: IsReal](self: Continuous[X, R], that: Continuous[Y, R]) extends Continuous[(X, Y), R] {
    val pdf = { case (x, y) => self.pdf(x) * that.pdf(y) }
    val logPdf = { case (x, y) => self.logPdf(x) + that.logPdf(y) }
  }
}

trait ContinuousStochastic[X, R] extends Continuous[X, R] with Stochastic[X]
