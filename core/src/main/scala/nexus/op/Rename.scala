package nexus.op

import nexus._
import nexus.typelevel._

/**
 * Renaming an axis in any tensor.
 * @example {{{ Rename(B -> C) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Rename[U, V](uv: (U, V)) extends ParaPolyOp1[(U, V), RenameF] {
  def parameter = uv
}

trait RenameF[P, X, Y] extends (P => Op1[X, Y])

object RenameF {

  implicit def RenameImpl[T[_, _ <: $$], D, A <: $$, U, V, B <: $$]
  (implicit r: Replace.Aux[A, U, V, B], env: Env[T, D]) =
    new RenameF[(U, V), T[D, A], T[D, B]] {
      def apply(p: (U, V)) = new Op1[T[D, A], T[D, B]] {
        val (u, v) = p
        import env._
        def name = s"Rename[${u.getClass.getSimpleName}->${v.getClass.getSimpleName}]"
        def forward(x: T[D, A]) = typeWith(untype(x), r(typeOf(x), v))
        def backward(dy: T[D, B], y: T[D, B], x: T[D, A]) = typeWith(untype(dy), r.inverse(typeOf(dy), u))
      }
    }

}
