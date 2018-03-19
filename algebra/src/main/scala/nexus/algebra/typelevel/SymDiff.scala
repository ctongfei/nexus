package nexus.algebra.typelevel

import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * Typelevel function that performs symmetric differences on two [[HList]]s.
 * This is used in tensor multiplication ([[nexus.op.Contract]]).
 * @author Tongfei Chen
 */
trait SymDiff[A, B] extends DepFn2[A, B] {
  type Out
  def matchedIndices: List[(Int, Int)]
  def lhsRetainedIndices: List[(Int, Int)]
  def rhsRetainedIndices: List[(Int, Int)]
  def recoverLeft: SymDiff.Aux[Out, B, A]
  def recoverRight: SymDiff.Aux[Out, A, B]
}

object SymDiff {

  def apply[A, B](implicit o: SymDiff[A, B]): Aux[A, B, o.Out] = o
  type Aux[A, B, C] = SymDiff[A, B] { type Out = C }

  // A =:= HNil
  implicit def symDiffCase0[B <: HList](implicit bl: Len[B]): Aux[HNil, B, B] =
    new SymDiff[HNil, B] {
      type Out = B
      def apply(a: HNil, b: B): Out = b
      def matchedIndices = Nil
      def lhsRetainedIndices = Nil
      def rhsRetainedIndices = (0 until bl()).map(i => (i, i)).toList
      def recoverLeft = ???
      def recoverRight = ???
    }

  // A.head ∉ B => A.head ∈ C
  implicit def symDiffCase1[H, T <: HList, B <: HList, C <: HList]
  (implicit n: NotContains[B, H], s: SymDiff.Aux[T, B, C]): Aux[H :: T, B, H :: C] =
    new SymDiff[H :: T, B] {
      type Out = H :: C
      def apply(a: H :: T, b: B): Out = a.head :: s(a.tail, b)
      def matchedIndices = s.matchedIndices map { case (i, j) => (i + 1, j) }
      def lhsRetainedIndices = (0, 0) :: (s.lhsRetainedIndices map { case (i, j) => (i + 1, j + 1) })
      def rhsRetainedIndices = s.rhsRetainedIndices.map { case (i, j) => (i + 1, j) }
      def recoverLeft = ???
      def recoverRight = ???
    }

  // A.head ∈ B => A.head ∉ C
  implicit def symDiffCase2[H, T <: HList, B <: HList, R <: HList, N <: Nat, C <: HList]
  (implicit idx: IndexOf.Aux[B, H, N], r: RemoveAt.Aux[B, N, R], s: SymDiff.Aux[T, R, C]): Aux[H :: T, B, C] =
    new SymDiff[H :: T, B] {
      type Out = C
      def apply(a: H :: T, b: B): Out = s(a.tail, r(b))
      def matchedIndices = (0, idx.toInt) :: (s.matchedIndices map { case (i, j) => (i + 1, j) })
      def lhsRetainedIndices = s.lhsRetainedIndices
      def rhsRetainedIndices = s.rhsRetainedIndices map { case (i, j) => (i, if (j >= idx.toInt) j + 1 else j) }
      def recoverLeft = ???
      def recoverRight = ???
    }

  implicit def symDiffTuple[A, Ah <: HList, B, Bh <: HList, C, Ch <: HList]
  (implicit ah: ToHList.Aux[A, Ah], bh: ToHList.Aux[B, Bh], sd: SymDiff.Aux[Ah, Bh, Ch], ch: ToHList.Aux[C, Ch]): Aux[A, B, C] =
    new SymDiff[A, B] {
      type Out = C
      def matchedIndices = sd.matchedIndices
      def lhsRetainedIndices = sd.lhsRetainedIndices
      def rhsRetainedIndices = sd.rhsRetainedIndices
      def recoverLeft = ???
      def recoverRight = ???
      def apply(t: A, u: B): C = ch.invert(sd(ah(t), bh(u)))
    }

}
