package nexus

/**
 * @author Tongfei Chen
 */
trait CastK[S[_], T[_]] { self =>
  def cast[A](s: S[A]): T[A]
  def ground[A]: Cast[S[A], T[A]] = new Cast[S[A], T[A]] {
    def cast(s: S[A]) = self.cast(s)
  }
}

trait BidiCastK[S[_], T[_]] extends CastK[S, T] { self =>
  def invert[A](t: T[A]): S[A]
  override def ground[A]: BidiCast[S[A], T[A]] = new BidiCast[S[A], T[A]] {
    def cast(s: S[A]) = self.cast(s)
    def invert(t: T[A]) = self.invert(t)
  }
}
