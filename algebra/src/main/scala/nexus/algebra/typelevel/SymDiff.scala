package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that performs symmetric differences on two [[HList]]s.
 * This is used in tensor multiplication ([[nexus.op.Contract]]).
 * @author Tongfei Chen
 */
trait SymDiff[A, B] {
  type Out
  def matchedIndices: List[(Int, Int)]
  def recoverLeft: SymDiff.Aux[B, Out, A]
  def recoverRight: SymDiff.Aux[Out, A, B]
}

case class HListSymDiffData(
    la: Int,
    lb: Int,
    mi: List[(Int, Int)]
)

class HListSymDiff[A, B, C](m: HListSymDiffData) extends SymDiff[A, B] {
  type Out = C

  override def matchedIndices: List[(Int, Int)] = m.mi

  override def recoverLeft: SymDiff.Aux[B, C, A] = new HListSymDiffLeft(m)

  override def recoverRight: SymDiff.Aux[C, A, B] = new HListSymDiffRight(m)
}

class HListSymDiffLeft[A, B, C](m: HListSymDiffData) extends SymDiff[B, C] {
  type Out = A

  // compute indices in B and C that match
  // == those indices in B that are not in A
  override def matchedIndices: List[(Int, Int)] = {
    val offset = m.la - m.mi.size
    val matchedB = m.mi.map(_._2)
    (0 until m.lb)
        .filter(ib => !matchedB.contains(ib))
        .zipWithIndex
        .map { case (bi, ci) => (bi, ci + offset) }
        .toList
  }

  override def recoverLeft: SymDiff.Aux[C, A, B] = new HListSymDiffRight(m)

  override def recoverRight: SymDiff.Aux[A, B, C] = new HListSymDiff(m)
}

class HListSymDiffRight[A, B, C](m: HListSymDiffData) extends SymDiff[C, A] {
  type Out = B

  // compute indices in A and C that match
  // == those indices in A that are not in B
  override def matchedIndices: List[(Int, Int)] = {
    val matchedA = m.mi.map(_._1)
    (0 until m.la)
        .filter(ia => !matchedA.contains(ia))
        .zipWithIndex
        .map(_.swap)
        .toList
  }

  override def recoverLeft: SymDiff.Aux[A, B, C] = new HListSymDiff(m)

  override def recoverRight: SymDiff.Aux[B, C, A] = new HListSymDiffLeft(m)
}

object SymDiff {

  def apply[A, B](implicit o: SymDiff[A, B]): Aux[A, B, o.Out] = o
  type Aux[A, B, C] = SymDiff[A, B] { type Out = C }

  // A =:= HNil
  implicit def symDiffNil[B <: HList](implicit bl: Len[B]): Aux[HNil, B, B] =
    new HListSymDiff[HNil, B, B](HListSymDiffData(0, bl.apply(), Nil))

  // A.head ∉ B => A.head ∈ C
  implicit def symDiffNoMatch[H, T <: HList, B <: HList, C <: HList]
  (implicit n: NotContains[B, H], s: SymDiff.Aux[T, B, C], bl: Len[B], tl: Len[T]): Aux[H :: T, B, H :: C] = {
    val matched = s.matchedIndices map { case (i, j) => (i + 1, j) }
    new HListSymDiff[H :: T, B, H :: C](HListSymDiffData(tl.apply() + 1, bl.apply(), matched))
  }

  // A.head ∈ B => A.head ∉ C
  implicit def symDiffMatch[H, T <: HList, B <: HList, R <: HList, N <: Nat, C <: HList]
  (implicit idx: IndexOf.Aux[B, H, N], r: RemoveAt.Aux[B, N, R], s: SymDiff.Aux[T, R, C], bl: Len[B], tl: Len[T]): Aux[H :: T, B, C] = {
    val matched = (0, idx.toInt) :: (s.matchedIndices map { case (i, j) => (i + 1, if (j >= idx.toInt) j + 1 else j) })
    new HListSymDiff[H :: T, B, C](HListSymDiffData(tl.apply() + 1, bl.apply(), matched))
  }

  implicit def symDiffTuple[A, Ah <: HList, B, Bh <: HList, C, Ch <: HList]
  (implicit ah: ToHList.Aux[A, Ah], bh: ToHList.Aux[B, Bh], sd: SymDiff.Aux[Ah, Bh, Ch], ch: ToHList.Aux[C, Ch]): Aux[A, B, C] =
    new SymDiff[A, B] {
      type Out = C
      def matchedIndices = sd.matchedIndices
      def recoverLeft = ???
      def recoverRight = ???
    }

}
