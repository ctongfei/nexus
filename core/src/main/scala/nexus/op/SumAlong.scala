package nexus.op

import nexus._
import shapeless.ops.hlist.Remove

/**
 * Computes the sum along a specific axis of a given tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class SumAlong[U](u: U) extends ParaPolyOp1[U, SumAlongF] {
  def parameter = u
}

trait SumAlongF[U, X, Y] extends (U => Op1[X, Y])

object SumAlongF {

  implicit def tensor[T[_, _ <: $$], D, A <: $$, U, B <: $$]
  (implicit r: Remove.Aux[A, U, (U, B)], env: Env[T, D]) =
    new SumAlongF[U, T[D, A], T[D, B]] {
      import env._
      def apply(u: U) = new Op1[T[D, A], T[D, B]] {
        def name = s"SumAlong[${u.getClass.getSimpleName}]"
        def forward(x: T[D, A]) =  ??? //sumAlong(x, r.index)
        def backward(dy: T[D, B], y: T[D, B], x: T[D, A]) = ??? // dy.broadcast(r.removed, r.index)
      }
    }

}
