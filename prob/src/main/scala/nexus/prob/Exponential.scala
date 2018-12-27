package nexus.prob

import nexus._
import nexus.syntax._

/**
 * An exponential distribution.
 * @author Tongfei Chen
 */
class Exponential[R](val λ: R)(implicit R: IsReal[R]) extends Stochastic[R] {

  import R._

  def sample = {
    val u = randomSource.nextDouble()
    log(fromDouble(u)) / λ
  }

  def rate = λ

  override def toString = s"Exponential(λ = $rate)"

}

object Exponential {

  def standard[R](implicit R: IsReal[R]) = apply(R.one)

  def apply[R: IsReal](λ: R) = new Exponential[R](λ)
  def unapply[R](e: Exponential[R]) = Some(e.λ)

}
