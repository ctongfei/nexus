package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._

@implicitNotFound("Cannot apply Rename[${P}] to ${X}.")
trait RenameF[P, X, Y] extends (P => DOp1[X, Y])

object RenameF {

  implicit def tensor[T[_ <: $$], R, A <: $$, U, V, B <: $$]
  (implicit r: Replace.Aux[A, U, V, B], T: IsTypedRealTensor[T, R]) =
    new RenameF[(U, V), T[A], T[B]] {
      def apply(p: (U, V)) = new DOp1[T[A], T[B]] {
        val (u, v) = p
        import T._
        def tag = T.ground[B]
        def name = s"Rename[${objTypeName(u)} -> ${objTypeName(v)}]"
        def forward(x: T[A]) = typeWith(untype(x), r(typeOf(x), v))
        def backward(dy: T[B], y: T[B], x: T[A]) = typeWith(untype(dy), r.inverse(typeOf(dy), u))
      }
    }

  // TODO: non-differentiable case

}

@implicitNotFound("Cannot apply Concat[${CC}] to ${A} and ${B}.")
trait ConcatF[CC, A, B, C] extends (CC => DOp2[A, B, C])

object ConcatF {

  implicit def vector[T[_ <: $$], R, A, B, C](implicit T: IsTypedRealTensor[T, R]) =
    new ConcatF[C, T[A::$], T[B::$], T[C::$]] {
      def apply(c: C) = new DOp2[T[A::$], T[B::$], T[C::$]] {
        def tag = T.ground[C::$]
        def name = s"Concat[${objTypeName(c)}]"
        def backward1(dy: T[C::$], y: T[C::$], x1: T[A::$], x2: T[B::$]) = ???
        def backward2(dy: T[C::$], y: T[C::$], x1: T[A::$], x2: T[B::$]) = ???
        def forward(x1: T[A::$], x2: T[B::$]) = ???
      }
    }

}
