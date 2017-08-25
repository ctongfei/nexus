package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._

/**
 * Renaming an axis in any tensor.
 * @example {{{ Rename(B -> C) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Rename[U, V](uv: (U, V)) extends ParaPolyDOp1[(U, V), RenameF] {
  def parameter = uv
}

trait RenameF[P, X, Y] extends (P => DOp1[X, Y])

object RenameF {

  implicit def tensor[T[_ <: $$], D, A <: $$, U, V, B <: $$]
  (implicit r: Replace.Aux[A, U, V, B], ops: TypedRealTensorOps[T, D]) =
    new RenameF[(U, V), T[A], T[B]] {
      def apply(p: (U, V)) = new DOp1[T[A], T[B]] {
        val (u, v) = p
        import ops._
        def gradOps = ops.ground[B]
        def name = s"Rename[${u.getClass.getSimpleName}->${v.getClass.getSimpleName}]"
        def forward(x: T[A]) = typeWith(untype(x), r(typeOf(x), v))
        def backward(dy: T[B], y: T[B], x: T[A]) = typeWith(untype(dy), r.inverse(typeOf(dy), u))
      }
    }

}
