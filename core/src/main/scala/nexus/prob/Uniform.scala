package nexus.prob

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import java.util._

/**
 * Represents the uniform distribution Uniform(l, r).
 * @author Tongfei Chen
 */
class Uniform[R](val l: R, r: R)(implicit R: IsReal[R]) extends Stochastic[R] {

  private[this] val standardUniform = new Random(GlobalSettings.seed)
  private[this] val d = r - l

  def sample = {
    val u = standardUniform.nextDouble()
    l + R.fromDouble(u) * d
  }

}

object Uniform {

  def standard[R](implicit R: IsReal[R]) = apply(R.zero, R.one)

  def apply[R: IsReal](l: R, r: R) = new Uniform[R](l, r)

}
