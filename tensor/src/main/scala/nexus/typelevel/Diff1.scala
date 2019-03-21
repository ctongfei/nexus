package nexus.typelevel

import nexus._
import shapeless._

/**
 * Typelevel function that computes the only different element between two lists.
 * If the number of differences is not 1, no such evidence instance would be derived.
 */
trait Diff1[U, V] {
  type Left
  type Right
  def index: Int
  def left(u: U): Left
  def right(v: V): Right
}

object Diff1 {

  def apply[U, V, I, J](implicit d: Diff1.Aux[U, V, I, J]) = d

  /** Proves that between U & V, there is only one difference -- in U it is I, whereas in V, it is J. */
  type Aux[U, V, I, J] = Diff1[U, V] { type Left = I; type Right = J }

  implicit def case0[T <: HList, L, R](implicit neq: L =:!= R): Aux[L :: T, R :: T, L, R] =
    new Diff1[L :: T, R :: T] {
      type Left = L
      type Right = R
      def index = 0
      def left(u: L :: T) = u.head
      def right(v: R :: T) = v.head
    }

  implicit def caseN[TL <: HList, TR <: HList, H, L, R](implicit d: Diff1.Aux[TL, TR, L, R]): Aux[H :: TL, H :: TR, L, R] =
    new Diff1[H :: TL, H :: TR] {
      type Left = L
      type Right = R
      def index = d.index + 1
      def left(u: H :: TL) = d.left(u.tail)
      def right(v: H :: TR) = d.right(v.tail)
    }

  implicit def tuple[U, Uh <: HList, V, Vh <: HList, L, R]
  (implicit uh: ToHList.Aux[U, Uh], vh: ToHList.Aux[V, Vh], d: Diff1.Aux[Uh, Vh, L, R]): Aux[U, V, L, R] =
    new Diff1[U, V] {
      type Left = L
      type Right = R
      def index = d.index
      def left(u: U) = d.left(uh(u))
      def right(v: V) = d.right(vh(v))
    }
}
