package nexus.op

import nexus._
import nexus.algebra._
import scala.annotation._

case class CastTo[T](parameter: T) extends ParaPolyDOp1[T] {

  type F[TT, S, T] = CastTo.F[TT, S, T]

  implicit def scalar[S, T](implicit cast: BidiCasting[S, T]): F[Grad[T], S, T] =
    new F[Grad[T], S, T] {
      def apply(Y: Grad[T]) = new DOp1[S, T] {
        def name = s"CastTo[$Y]"
        def tag = Y
        def forward(x: S) = cast(x)
        def backward(dy: T, y: T, x: S) = cast.invert(dy)
      }
    }

  implicit def tensor[S[_ <: $$], T[_ <: $$], A <: $$](implicit cast: BidiCastingH[S, T]): F[GradH[T], S[A], T[A]] =
    new F[GradH[T], S[A], T[A]] {
      def apply(T: GradH[T]) = new DOp1[S[A], T[A]] {
        def name = s"CastTo[$T]"
        def tag = T.ground[A]
        def forward(x: S[A]) = cast(x)
        def backward(dy: T[A], y: T[A], x: S[A]) = cast.invert(dy)
      }
    }


}

object CastTo {

  @implicitNotFound("Cannot cast ${S} to ${T}.")
  trait F[TT, S, T] extends (TT => DOp1[S, T])
}
