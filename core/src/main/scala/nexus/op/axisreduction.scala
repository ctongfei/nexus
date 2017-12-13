package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import shapeless.Nat

class SumAlong[P](val parameter: P) extends ParaPolyDOp1[P] {
  type F[P, X, Y] = SumAlong.F[P, X, Y]
}

object SumAlong {

  def apply[X](axis: X) = new SumAlong(axis)

  trait F[P, X, Y] extends (P => DOp1[X, Y])

  implicit def tensor[T[_ <: $$], R, A <: $$, X, N <: Nat, B <: $$]
    (implicit T: IsRealTensor[T, R], ix: IndexOf.Aux[A, X, N], ra: RemoveAt.Aux[A, N, B]):
  F[X, T[A], T[B]] = (x: X) => new DOp1[T[A], T[B]] {
    def name = s"SumAlong[${objTypeName(x)}]"
    def tag = T.ground[B]
    def forward(x: T[A]) = ??? // T.sumAlong(x, ix.toInt)
    def backward(dy: T[B], y: T[B], x: T[A]) = ??? // = T.expandDim(dy, ix.toInt, T.size(x, ix.toInt))
  }

}
