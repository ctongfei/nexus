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

  import GlobalSettings._

  def sample = {
    val u = random.nextDouble()
    -R.log(R.fromDouble(u)) / λ
  }

  def rate = λ

}

object Exponential {

  def standard[R](implicit R: IsReal[R]) = apply(R.one)

  def apply[R: IsReal](λ: R) = new Exponential[R](λ)
  def unapply[R](e: Exponential[R]) = Some(e.λ)

}
