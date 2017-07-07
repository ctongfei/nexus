package nexus.typeop

import shapeless._

/**
 * Typelevel function that performs symmetric differences on two [[HList]]s. This is used in tensor multiplication ([[nexus.op.TMul]]).
 */
trait SymDiff[A <: HList, B <: HList] extends DepFn2[A, B] {
  type Out <: HList
  def matchedIndices: List[(Int, Int)]
}

object SymDiff {

  def apply[A <: HList, B <: HList](implicit o: SymDiff[A, B]): Aux[A, B, o.Out] = o
  type Aux[A <: HList, B <: HList, Out0 <: HList] = SymDiff[A, B] { type Out = Out0 }

  implicit def case0[B <: HList]: Aux[HNil, B, B] =
    new SymDiff[HNil, B] {
      type Out = B
      def apply(a: HNil, b: B): Out = b
      def matchedIndices = Nil
    }

  // A.head ∉ B
  implicit def case1[H, T <: HList, B <: HList, C <: HList]
  (implicit n: NotContains[B, H], s: SymDiff.Aux[T, B, C]): Aux[H :: T, B, H :: C] =
    new SymDiff[H :: T, B] {
      type Out = H :: C
      def apply(a: H :: T, b: B): Out = a.head :: s(a.tail, b)
      def matchedIndices = s.matchedIndices map { case (i, j) => (i + 1, j + 1) }
    }

  // A.head ∈ B
  implicit def case2[H, T <: HList, B <: HList, R <: HList, N <: Nat, C <: HList]
  (implicit i: IndexOf.Aux[B, H, N], r: RemoveAt.Aux[B, N, R], s: SymDiff.Aux[T, R, C]): Aux[H :: T, B, C] =
    new SymDiff[H :: T, B] {
      type Out = C
      def apply(a: H :: T, b: B): Out = s(a.tail, r(b))
      def matchedIndices = (0, i.toInt) :: (s.matchedIndices map { case (i, j) => (i + 1, j + 1) })
    }

}
