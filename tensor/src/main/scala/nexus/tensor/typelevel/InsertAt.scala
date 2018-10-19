package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that inserts type [[U]] at the [[I]]-th position in [[A]].
 * @author Tongfei Chen
 */
trait InsertAt[A, I <: Nat, U] extends DepFn2[A, U] {
  type Out
  def index: Int

  def inverse: RemoveAt.Aux[Out, I, A]
}

object InsertAt {

  def apply[A, I <: Nat, U](implicit o: InsertAt[A, I, U]): Aux[A, I, U, o.Out] = o
  type Aux[A, I <: Nat, U, Out0] = InsertAt[A, I, U] { type Out = Out0 }

  implicit def case0[At <: HList, Ah]: Aux[At, _0, Ah, Ah :: At] =
    new InsertAt[At, _0, Ah] { ix =>
      type Out = Ah :: At
      def apply(t: At, h: Ah): Ah :: At = h :: t
      def index = 0
      def inverse: RemoveAt.Aux[Ah :: At, _0, At] = new RemoveAt[Ah :: At, _0] {
        def index = ix.index
        type Out = At
        def apply(t: Ah :: At) = t.tail
      }
    }

  implicit def caseN[At <: HList, Ah, P <: Nat, U, Bt <: HList]
  (implicit pix: InsertAt.Aux[At, P, U, Bt]): Aux[Ah :: At, Succ[P], U, Ah :: Bt] =
    new InsertAt[Ah :: At, Succ[P], U] { ix =>
      type Out = Ah :: Bt
      def apply(t: Ah :: At, x: U): Ah :: Bt = t.head :: pix(t.tail, x)
      def index = pix.index + 1
      def inverse: RemoveAt.Aux[Ah :: Bt, Succ[P], Ah :: At] = new RemoveAt[Ah :: Bt, Succ[P]] {
        def index = pix.index
        type Out = Ah :: At
        def apply(t: Ah :: Bt) = t.head :: pix.inverse(t.tail)
      }
    }

  implicit def tuple[A, Al <: HList, I <: Nat, U, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], ix: InsertAt.Aux[Al, I, U, Bl], bl: FromHList.Aux[Bl, B]): Aux[A, I, U, B] =
    new InsertAt[A, I, U] {
      type Out = B
      def apply(t: A, x: U): B = bl(ix(al(t), x))
      def index = ix.index
      def inverse: RemoveAt.Aux[B, I, A] = new RemoveAt[B, I] {
        def index = ix.index
        type Out = A
        def apply(t: B): A = al.invert(ix.inverse(bl.invert(t)))
      }
    }
}
