package nexus.typelevel

import shapeless._

/**
 * Typelevel function that replaces type [[U]] in [[L]] to [[V]].
 * @author Tongfei Chen
 */
trait Replace[L <: HList, U, V] extends DepFn2[L, V] {
  type Out <: HList

  /** Constructs the inverse replacement evidence. */
  def inverse: Replace.Aux[Out, V, U, L]
}

object Replace {

  def apply[L <: HList, U, V](implicit r: Replace[L, U, V]): Aux[L, U, V, r.Out] = r
  type Aux[L <: HList, U, V, Out0 <: HList] = Replace[L, U, V] { type Out = Out0 }

  implicit def replaceAt0[T <: HList, U, V]: Aux[U :: T, U, V, V :: T] =
    new Replace[U :: T, U, V] { self =>
      type Out = V :: T
      def apply(l: U :: T, v: V): V :: T = v :: l.tail
      def inverse = new Replace[V :: T, V, U] {
        type Out = U :: T
        def apply(l: V :: T, u: U): U :: T = u :: l.tail
        def inverse = self
      }
    }

  implicit def replaceAtN[T <: HList, H, U, V, R <: HList]
  (implicit n: H =:!= U, r: Replace.Aux[T, U, V, R]): Replace.Aux[H :: T, U, V, H :: R] =
    new Replace[H :: T, U, V] { self =>
      type Out = H :: R
      def apply(l: H :: T, v: V): H :: R = l.head :: r(l.tail, v)
      def inverse = new Replace[H :: R, V, U] {
        type Out = H :: T
        def apply(l: H :: R, u: U): H :: T = l.head :: r.inverse(l.tail, u)
        def inverse = self
      }
    }

}
