package nexus.prob

import nexus._
import nexus.tensor._
import nexus.tensor.syntax._

/**
 * Represents the normal distribution N(μ, σ^2^).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Normal[R](val μ: R, val σ2: R)(implicit R: IsReal[R]) extends Stochastic[R] with HasPdf[R, R] {

  private[this] lazy val σ = R.sqrt(σ2)
  private[this] lazy val τ = R.inv(σ2)

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

  def real = R

}

object Normal {

  def standard[R](implicit R: IsReal[R]) = apply(R.zero, R.one)

  def apply[R: IsReal](μ: R, σ2: R) = new Normal(μ, σ2)
  def unapply[R](n: Normal[R]) = Some((n.μ, n.σ2))

}
