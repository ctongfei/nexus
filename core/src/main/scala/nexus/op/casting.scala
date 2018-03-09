package nexus.op

import nexus._
import nexus.algebra._
import scala.annotation._

object CastTo extends ParameterizedPolyOp1 {

  implicit def castToF[S, T](implicit c: BidiCasting[S, T]) = (T: Type[T]) =>
    new F[S, T] {
        def name = s"CastTo[$T]"
        def tag(tx: Type[S]) = T
        def forward(x: S) = c.cast(x)
        def backward(dy: T, y: T, x: S) = c.invert(dy)
      }

  implicit def castToKF[S[_], T[_], A](implicit c: BidiCastingK[S, T]) = (T: TypeK[T]) =>
    new F[S[A], T[A]] {
      def name = s"CastTo[$T]"
      def tag(tx: Type[S[A]]) = T.ground[A] // TODO: shape copying
      def forward(x: S[A]) = c.cast(x)
      def backward(dy: T[A], y: T[A], x: S[A]) = c.invert(dy)
    }

}
