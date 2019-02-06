package nexus.diff.ops

import nexus.diff._
import nexus._

object CastTo extends ParameterizedPolyOp1 {

  implicit def castToF[S, T, TT[_]](implicit c: BidiCast[S, T]) = (T: TT[T]) =>
    new F[S, T] {
      def name = s"CastTo[$T]"
      def tag = Tag of T
      def forward(x: S) = c.cast(x)
      def backward(dy: T, y: T, x: S) = c.invert(dy)
    }

  implicit def castToKF[S[_], T[_], I, TT[_[_]]](implicit c: BidiCastK[S, T]) = (T: TT[T]) =>
    new F[S[I], T[I]] {
      def name = s"CastTo[$T]"
      def tag = ??? // Tag.tensor(T)
      def forward(x: S[I]) = c.cast(x)
      def backward(dy: T[I], y: T[I], x: S[I]) = c.invert(dy)
    }

}
