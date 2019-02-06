package nexus

/**
 * @author Tongfei Chen
 */
trait CastK[S[_], T[_]] { self =>
  def cast[I](s: S[I]): T[I]
  def ground[I]: Cast[S[I], T[I]] = new Cast[S[I], T[I]] {
    def cast(s: S[I]) = self.cast(s)
  }
}

trait BidiCastK[S[_], T[_]] extends CastK[S, T] { self =>
  def invert[I](t: T[I]): S[I]
  override def ground[I]: BidiCast[S[I], T[I]] = new BidiCast[S[I], T[I]] {
    def cast(s: S[I]) = self.cast(s)
    def invert(t: T[I]) = self.invert(t)
  }
}
