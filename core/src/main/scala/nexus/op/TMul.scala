package nexus.op

import nexus._
import nexus.impl._
import nexus.typelevel._

/**
 * General tensor multiplication (contraction) that marginalizes out all axes between two tensors that match.
 * Einstein summation
 * @author Tongfei Chen
 * @since 0.1.0
 */
object TMul extends PolyOp2[TMulF]

trait TMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "TMul"
}

object TMulF {

  implicit def tensor[T[_ <: $$], D, A <: $$, B <: $$, C <: $$](implicit ops: TypedMathOps[T, D], sd: SymDiff.Aux[A, B, C]) =
    new TMulF[T[A], T[B], T[C]] {
      import ops._
      def _ops = ops.ground[C]
      def forward(x1: T[A], x2: T[B]) = x1 ⋈ x2
      def backward1(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = ??? // dy ⋈ x2
      def backward2(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = ??? // dy ⋈ x1
  }

}
