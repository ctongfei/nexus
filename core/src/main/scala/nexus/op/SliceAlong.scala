package nexus.op

import nexus._
import nexus.impl._
import nexus.typelevel._
import shapeless._

/**
 * Slices a tensor along a specific axis.
 * @example {{{ SliceAlong(Width -> 3) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class SliceAlong[U](parameter: (U, Int)) extends ParaPolyOp1[(U, Int), SliceAlongF]

trait SliceAlongF[P, X, Y] extends (P => Op1[X, Y])

object SliceAlongF {

  implicit def tensor[T[_ <: $$], D, A <: $$, U, N <: Nat, B <: $$]
  (implicit ops: TypedMathOps[T, D], ui: IndexOf.Aux[A, U, N], ur: RemoveAt.Aux[A, N, B]) = new SliceAlongF[(U, Int), T[A], T[B]] {
    def apply(p: (U, Int)) = new Op1[T[A], T[B]] {
      import ops._
      def _ops = ops.ground[B]
      val (axis, i) = p
      def name = s"SliceAlong[${axis.getClass.getSimpleName}->$i]"
      def forward(x: T[A]) = ???
      def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }
  }
}
