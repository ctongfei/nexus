package nexus.op

import nexus._
import nexus.algebra._
import shapeless.ops.hlist.Remove

/**
 * Computes the sum along a specific axis of a given tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class SumAlong[U](u: U) extends ParaPolyDOp1[U, SumAlongF] {
  def parameter = u
}

trait SumAlongF[U, X, Y] extends (U => DOp1[X, Y])

object SumAlongF {

  implicit def tensor[T[_ <: $$], D, A <: $$, U, B <: $$]
  (implicit r: Remove.Aux[A, U, (U, B)], ops: TypedRealTensorOps[T, D]) =
    new SumAlongF[U, T[A], T[B]] {
      import ops._
      def apply(u: U) = new DOp1[T[A], T[B]] {
        def gradOps = ops.ground[B]
        def name = s"SumAlong[${u.getClass.getSimpleName}]"
        def forward(x: T[A]) =  ??? //sumAlong(x, r.index)
        def backward(dy: T[B], y: T[B], x: T[A]) = ??? // dy.broadcast(r.removed, r.index)
      }
    }

}
