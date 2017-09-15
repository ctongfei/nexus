package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._

@implicitNotFound("Cannot apply Rename[${P}] to ${X}.")
trait RenameF[P, X, Y] extends (P => DOp1[X, Y])

object RenameF {

  implicit def tensor[T[_ <: $$], R, A <: $$, U, V, B <: $$]
  (implicit r: Replace.Aux[A, U, V, B], ops: IsTypedRealTensor[T, R]) =
    new RenameF[(U, V), T[A], T[B]] {
      def apply(p: (U, V)) = new DOp1[T[A], T[B]] {
        val (u, v) = p
        import ops._
        def tag = ops.ground[B]
        def name = s"Rename[${u.getClass.getSimpleName} -> ${v.getClass.getSimpleName}]"
        def forward(x: T[A]) = typeWith(untype(x), r(typeOf(x), v))
        def backward(dy: T[B], y: T[B], x: T[A]) = typeWith(untype(dy), r.inverse(typeOf(dy), u))
      }
    }

  // TODO: non-differentiable case

}
