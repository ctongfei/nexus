package nexus.prob

import java.util._

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * An exponential distribution.
 * @author Tongfei Chen
 */
class Exponential[R](val λ: R)(implicit R: IsReal[R]) extends Stochastic[R] {
  private[this] val standardUniform = new Random(GlobalSettings.seed)

  def sample = {
    val u = standardUniform.nextDouble()
    -R.log(R.fromDouble(u)) / λ
  }

}

object Exponential {

  def standard[R](implicit R: IsReal[R]) = apply(R.one)

  def apply[R: IsReal](λ: R) = new Exponential[R](λ)

}
