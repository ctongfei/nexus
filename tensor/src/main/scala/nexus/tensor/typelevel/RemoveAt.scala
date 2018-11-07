package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that removes the [[I]]th type in [[A]].
 * @author Tongfei Chen
 */
trait RemoveAt[A, I <: Nat] extends DepFn1[A] {
  def index: Int
  type Out
  type Removed
  def inverse: InsertAt.Aux[Out, I, Removed, A]
}

object RemoveAt {

  def apply[A, I <: Nat](implicit o: RemoveAt[A, I]): Aux[A, I, o.Out] = o
  type Aux[A, I <: Nat, Out0] = RemoveAt[A, I] { type Out = Out0 }


  implicit def case0[At <: HList, Ah]: Aux[Ah :: At, _0, At] =
    new RemoveAt[Ah :: At, _0] {
      type Out = At
      type Removed = Ah
      def index = 0
      def apply(t: Ah :: At): At = t.tail
      def inverse: InsertAt.Aux[At, _0, Ah, Ah :: At] = InsertAt.case0[At, Ah]
    }

  implicit def caseN[At <: HList, H, Bt <: HList, P <: Nat]
  (implicit p: RemoveAt.Aux[At, P, Bt]): Aux[H :: At, Succ[P], H :: Bt] =
    new RemoveAt[H :: At, Succ[P]] {
      type Out = H :: Bt
      type Removed = p.Removed
      def index = p.index + 1
      def apply(t: H :: At): H :: Bt = t.head :: p(t.tail)
      def inverse: InsertAt.Aux[H :: Bt, Succ[P], Removed, H :: At] = InsertAt.caseN[Bt, H, P, Removed, At](p.inverse)
    }

  implicit def tuple[A, Al <: HList, I <: Nat, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], r: RemoveAt.Aux[Al, I, Bl], bl: FromHList.Aux[Bl, B]): Aux[A, I, B] =
    new RemoveAt[A, I] {
      type Out = B
      type Removed = r.Removed
      def index = r.index
      def apply(t: A): B = bl(r(al(t)))
      def inverse: InsertAt.Aux[B, I, r.Removed, A] = InsertAt.tuple(bl.inverse, r.inverse, al.inverse)
    }

}
