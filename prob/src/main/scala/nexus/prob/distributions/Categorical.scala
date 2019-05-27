package nexus.prob.distributions

import nexus._
import nexus.prob._

/**
 * @author Tongfei Chen
 */
class Categorical[T[_], R, I <: Dim] private(weight: T[I])(implicit T: IsRealTensorK[T, R]) extends DiscreteStochastic[Int, R] {

  /** Gets the probability mass at a specific point. */
  def pmf = ???

  /** Gets the log probability mass at a specific point. */
  def logPmf = ???

  def sample: Int = ???

  override def flatMap[B](f: Int => Stochastic[B]) = f match {
    case f: Seq[Stochastic[B]] => new Mixture(this, f)
    case _ => super.flatMap(f)
  }

}
