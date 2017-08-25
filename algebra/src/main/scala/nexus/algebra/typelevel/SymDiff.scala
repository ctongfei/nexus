package nexus.algebra.typelevel

import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * Typelevel function that performs symmetric differences on two [[HList]]s. This is used in tensor multiplication ([[nexus.op.TMul]]).
 * @author Tongfei Chen
 */
trait SymDiff[A <: HList, B <: HList] extends DepFn2[A, B] {
  type Out <: HList
  def matchedIndices: List[(Int, Int)]
  def lhsRetainedIndices: List[(Int, Int)]
  def rhsRetainedIndices: List[(Int, Int)]
}

object SymDiff {

  def apply[A <: HList, B <: HList](implicit o: SymDiff[A, B]): Aux[A, B, o.Out] = o
  type Aux[A <: HList, B <: HList, C <: HList] = SymDiff[A, B] { type Out = C }

  // A =:= HNil
  implicit def case0[B <: HList, N <: Nat](implicit bl: Length.Aux[B, N], bln: ToInt[N]): Aux[HNil, B, B] =
    new SymDiff[HNil, B] {
      type Out = B
      def apply(a: HNil, b: B): Out = b
      def matchedIndices = Nil
      def lhsRetainedIndices = Nil
      def rhsRetainedIndices = (0 until bln()).map(i => (i, i)).toList
    }

  // A.head ∉ B => A.head ∈ C
  implicit def case1[H, T <: HList, B <: HList, C <: HList]
  (implicit n: NotContains[B, H], s: SymDiff.Aux[T, B, C]): Aux[H :: T, B, H :: C] =
    new SymDiff[H :: T, B] {
      type Out = H :: C
      def apply(a: H :: T, b: B): Out = a.head :: s(a.tail, b)
      def matchedIndices = s.matchedIndices map { case (i, j) => (i + 1, j) }
      def lhsRetainedIndices = (0, 0) :: (s.lhsRetainedIndices map { case (i, j) => (i + 1, j + 1) })
      def rhsRetainedIndices = s.rhsRetainedIndices
    }

  // A.head ∈ B => A.head ∉ C
  implicit def case2[H, T <: HList, B <: HList, R <: HList, N <: Nat, C <: HList]
  (implicit i: IndexOf.Aux[B, H, N], r: RemoveAt.Aux[B, N, R], s: SymDiff.Aux[T, R, C]): Aux[H :: T, B, C] =
    new SymDiff[H :: T, B] {
      type Out = C
      def apply(a: H :: T, b: B): Out = s(a.tail, r(b))
      def matchedIndices = (0, i.toInt) :: (s.matchedIndices map { case (i, j) => (i + 1, j + 1) })
      def lhsRetainedIndices = s.lhsRetainedIndices
      def rhsRetainedIndices = s.rhsRetainedIndices map { case (i, j) => (i + 1, if (j >= i.toInt) j + 1 else j) }
    }

}
