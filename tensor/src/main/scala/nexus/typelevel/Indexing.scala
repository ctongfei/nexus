package nexus.typelevel

import nexus._
import shapeless._

/**
 * Type-level function for advanced indexing.
 */
trait Indexing[U, I] {

  type Out
  def indexers(i: I): List[Any]

}

object Indexing {

  def apply[U, I](implicit ix: Indexing[U, I]): Aux[U, I, ix.Out] = ix
  type Aux[U, I, V] = Indexing[U, I] { type Out = V }

  implicit def case0: Aux[HNil, HNil, HNil] =
    new Indexing[HNil, HNil] {
      type Out = HNil
      def indexers(i: HNil) = Nil
    }

  implicit def caseInt[Ut <: HList, It <: HList, Uh <: Dim, Vt]
  (implicit p: Indexing.Aux[Ut, It, Vt]): Aux[Uh :: Ut, Int :: It, Vt] =
    new Indexing[Uh :: Ut, Int :: It] {
      type Out = Vt
      def indexers(i: Int :: It) = i.head :: p.indexers(i.tail)
    }

  implicit def caseSlice[Ut <: HList, It <: HList, S <: Slice, Uh <: Dim, Vt <: HList]
  (implicit p: Indexing.Aux[Ut, It, Vt]): Aux[Uh :: Ut, S :: It, Uh :: Vt] =
    new Indexing[Uh :: Ut, S :: It] {
      type Out = Uh :: Vt
      def indexers(i: S :: It) = i.head :: p.indexers(i.tail)
    }

  implicit def tuple[U, Ul <: HList, I, Il <: HList, Vl <: HList, V]
  (implicit ul: ToHList.Aux[U, Ul], il: ToHList.Aux[I, Il], ix: Indexing.Aux[Ul, Il, Vl], vl: FromHList.Aux[Vl, V]): Aux[U, I, V] =
    new Indexing[U, I] {
      type Out = V
      def indexers(i: I) = ix.indexers(il(i))
    }

}
