package nexus.diff.ops

import nexus.diff._
import nexus._

object CastTo extends ParameterizedPolyOp1 {

  implicit def castToF[S, T, TT[_]](implicit c: BidiCast[S, T]) = (T: TT[T]) =>
    new F[S, T] {
      def name = s"CastTo[$T]"
      def tag = ???
      def forward(x: S) = c.cast(x)
      def backward(dy: T, y: T, x: S) = c.invert(dy)
    }
//
//  implicit def castToKF[S[_], T[_], A, TT[a] <: TypeK[a]](implicit c: BidiCastingK[S, T]) = (T: TT[T]) =>
//    new F[S[A], T[A]] {
//      type Tag[ta] = TT[ta]
//      def name = s"CastTo[$T]"
//      def tag = T.ground[A] // TODO: shape copying
//      def forward(x: S[A]) = c.cast(x)
//      def backward(dy: T[A], y: T[A], x: S[A]) = c.invert(dy)
//    }

}
