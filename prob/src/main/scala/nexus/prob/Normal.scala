package nexus.prob

import nexus._
import nexus.tensor._
import nexus.tensor.syntax._

/**
 * Represents the univariate normal distribution N(μ, σ^2^).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Normal[R] private(val μ: R, val τ: R)(implicit R: IsReal[R]) extends Stochastic[R] with HasPdf[R, R] {

  lazy val σ2 = R.inv(τ)
  lazy val σ = R.sqrt(σ2)

  def pdf(x: R) = ???

  def logPdf(x: R) = ???

  def mean = μ

  def variance = σ2

  def precision = τ

  def stdDev = σ

  def sample = {
    val u = random.nextGaussian()
    μ + R.fromDouble(u) * σ
  }

}

object Normal {

  def standard[R](implicit R: IsReal[R]) = apply(R.zero, R.one)

  def apply[R](μ: R, σ2: R)(implicit R: IsReal[R]) = new Normal(μ, R.inv(σ2))

  def ofMeanAndStdDev[R](μ: R, σ: R)(implicit R: IsReal[R]) = new Normal(μ, R.inv(R.sqr(σ)))

  def ofMeanAndPrecision[R](μ: R, τ: R)(implicit R: IsReal[R]) = new Normal(μ, τ)

  def unapply[R](n: Normal[R]) = Some((n.μ, n.σ2))

}
