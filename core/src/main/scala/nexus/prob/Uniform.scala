package nexus.prob

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import java.util._

import cats.Id

/**
 * Represents the uniform distribution Uniform(l, r).
 * @author Tongfei Chen
 */
trait Uniform[R] extends Stochastic[R] with HasPdf[R, R] {
  def left: R
  def right: R
}

object Uniform {
/*
  class Impl[E[_], R, B](l: E[R], r: E[R])(implicit R: IsReal[E[R]], Rc: Comparison[E[R], E[B]], Bc: Cond[E[B], E]) extends Uniform[E[R]] {

    import R._
    private[this] val standardUniform = new Random(GlobalSettings.seed)
    private[this] val d = r - l

    def left = l
    def right = r
    def pdf(x: E[R]) = ???
    def logPdf(x: E[R]) = ???
    def sample = {
      val u = standardUniform.nextDouble()
      add(l, mul(fromDouble(u), d))
    }

  }

  def standard[R](implicit R: IsReal[R]) = apply(R.zero, R.one)

  def apply[E[_], R](l: E[R], r: E[R])(implicit R: GenReal[E, R]): Uniform[E[R]] = new Impl(l, r)
*/
}
