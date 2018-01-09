package nexus.op

import nexus._
import nexus.algebra._
import scala.annotation._

object CastTo extends ParamPolyOp1 {

  implicit def scalar[S, T](implicit cast: BidiCasting[S, T]): F[Grad[T], S, T] =
    new F[Grad[T], S, T] {
      def apply(T: Grad[T]) = new Op1[S, T] {
        def name = s"CastTo[$T]"
        def tag(tx: Type[S]) = T
        def differentiable = true
        def forward(x: S) = cast.cast(x)
        def backward(dy: T, y: T, x: S) = cast.invert(dy)
      }
    }

  implicit def tensor[S[_], T[_], A](implicit cast: BidiCastingH[S, T]): F[GradH[T], S[A], T[A]] =
    new F[GradH[T], S[A], T[A]] {
      def apply(T: GradH[T]) = new Op1[S[A], T[A]] {
        def name = s"CastTo[$T]"
        def tag(tx: Type[S[A]]) = T.ground[A] // TODO: shape copying
        def differentiable = true
        def forward(x: S[A]) = cast.cast(x)
        def backward(dy: T[A], y: T[A], x: S[A]) = cast.invert(dy)
      }
    }

}
