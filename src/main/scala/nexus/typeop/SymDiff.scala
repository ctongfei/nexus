package nexus.typeop

import shapeless._

/**
 * Typelevel function that performs symmetric differences on two [[HList]]s. This is used in tensor multiplication ([[nexus.op.TMul]]).
 */
trait SymDiff[A <: HList, B <: HList] extends DepFn2[A, B] { type Out <: HList }

object SymDiff extends SymDiffLowerPriorities {

  def apply[A <: HList, B <: HList](implicit o: SymDiff[A, B]): Aux[A, B, o.Out] = o
  type Aux[A <: HList, B <: HList, Out0 <: HList] = SymDiff[A, B] { type Out = Out0 }

  implicit def headEq[A <: HList, B <: HList, C <: HList, H](implicit ev: SymDiff.Aux[A, B, C]): Aux[H :: A, H :: B, C] =
    new SymDiff[H :: A, H :: B] {
      type Out = C
      def apply(t: H :: A, u: H :: B): C = ev(t.tail, u.tail)
    }

  implicit def leftNil[B <: HList]: Aux[HNil, B, B] =
    new SymDiff[HNil, B] {
      type Out = B
      def apply(t: HNil, u: B): B = u
    }

  implicit def rightNil[A <: HList]: Aux[A, HNil, A] =
    new SymDiff[A, HNil] {
      type Out = A
      def apply(t: A, u: HNil): A = t
    }

}

trait SymDiffLowerPriorities {

  import SymDiff._

  implicit def headNEq[H, T <: HList, B <: HList, C <: HList](implicit ev: Aux[T, B, C]): Aux[H :: T, B, H :: C] =
    new SymDiff[H :: T, B] {
      type Out = H :: C
      def apply(t: H :: T, u: B): H :: C = t.head :: ev(t.tail, u)
    }
}

