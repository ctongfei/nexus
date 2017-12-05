package nexus.func

import nexus._
import nexus.algebra._

@implicitNotFound("Cannot cast ${S} to ${T}.")
trait CastToF[TT, S, T] extends (TT => DOp1[S, T])

object CastToF {

  implicit def scalar[S, T](implicit cast: BidiCasting[S, T]): CastToF[Grad[T], S, T] =
    new CastToF[Grad[T], S, T] {
      def apply(Y: Grad[T]) = new DOp1[S, T] {
        def name = s"Cast[$Y]"
        def tag = Y
        def forward(x: S) = cast(x)
        def backward(dy: T, y: T, x: S) = cast.invert(dy)
      }
    }

  implicit def tensor[S[_ <: $$], T[_ <: $$], A <: $$](implicit cast: BidiCastingH[S, T]): CastToF[GradH[T], S[A], T[A]] =
    new CastToF[GradH[T], S[A], T[A]] {
      def apply(T: GradH[T]) = new DOp1[S[A], T[A]] {
        def name = s"Cast[$T]"
        def tag = T.ground[A]
        def forward(x: S[A]) = cast(x)
        def backward(dy: T[A], y: T[A], x: S[A]) = cast.invert(dy)
      }
    }

}
