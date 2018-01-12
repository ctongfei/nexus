package nexus.algebra

import cats._

/**
 * Evidence that witnesses a type [[S]] can be cast to [[T]].
 * @author Tongfei Chen
 */
trait Casting[S, T] {
  def cast(s: S): T
}

trait BidiCasting[S, T] extends Casting[S, T] {
  def invert(t: T): S
}

trait CastingK[S[_], T[_]] { self =>
  def cast[A](s: S[A]): T[A]
  def ground[A]: Casting[S[A], T[A]] = new Casting[S[A], T[A]] {
    def cast(s: S[A]) = self.cast(s)
  }
}

trait BidiCastingK[S[_], T[_]] extends CastingK[S, T] { self =>
  def invert[A](t: T[A]): S[A]
  override def ground[A]: BidiCasting[S[A], T[A]] = new BidiCasting[S[A], T[A]] {
    def cast(s: S[A]) = self.cast(s)
    def invert(t: T[A]) = self.invert(t)
  }
}
