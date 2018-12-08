package nexus.prob

import nexus.tensor._

/**
 * @author Tongfei Chen
 */
class Categorical[T[_], R, U <: Dim](val p: T[U])(implicit T: IsRealTensorK[T, R]) extends Stochastic[Int] {

  // lazy val AliasSampler

  def sample: Int = ???

  override def flatMap[B](f: Int => Stochastic[B]) = f match {
    case f: Seq[Stochastic[B]] => new Mixture(this, f)
    case _ => super.flatMap(f)
  }

}

object Categorical {

}