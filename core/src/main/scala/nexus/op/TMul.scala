package nexus.op

import nexus._
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

  implicit def TMulImpl[T[D, _ <: $$], D, A <: $$, B <: $$, C <: $$](implicit env: Env[T, D], sd: SymDiff.Aux[A, B, C]) =
    new TMulF[T[D, A], T[D, B], T[D, C]] {
      import env._
      def forward(x1: T[D, A], x2: T[D, B]) = x1 ⋈ x2
      def backward1(dy: T[D, C], y: T[D, C], x1: T[D, A], x2: T[D, B]) = ??? // dy ⋈ x2
      def backward2(dy: T[D, C], y: T[D, C], x1: T[D, A], x2: T[D, B]) = ??? // dy ⋈ x1
  }

}
