package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that replaces type [[U]] in [[L]] to [[V]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Replace[L, U, V] extends DepFn2[L, V] {
  type Out

  /** Constructs the inverse replacement evidence. */
  def inverse: Replace.Aux[Out, V, U, L]
}

object Replace {

  def apply[L, U, V](implicit r: Replace[L, U, V]): Aux[L, U, V, r.Out] = r
  type Aux[L, U, V, Out0] = Replace[L, U, V] { type Out = Out0 }

  implicit def replaceHListCase0[T <: HList, U, V]: Aux[U :: T, U, V, V :: T] =
    new Replace[U :: T, U, V] { self =>
      type Out = V :: T
      def apply(l: U :: T, v: V): V :: T = v :: l.tail
      def inverse = new Replace[V :: T, V, U] {
        type Out = U :: T
        def apply(l: V :: T, u: U): U :: T = u :: l.tail
        def inverse = self
      }
    }

  implicit def replaceHListCaseN[T <: HList, H, U, V, R <: HList]
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

  implicit def replaceTuple[L, Lh <: HList, U, V, Rh <: HList, R]
  (implicit lh: ToHList.Aux[L, Lh], r: Replace.Aux[Lh, U, V, Rh], rh: ToHList.Aux[R, Rh]): Replace.Aux[L, U, V, R] =
    new Replace[L, U, V] { self =>
      type Out = R
      def apply(t: L, v: V): R = rh.inverse(r(lh(t), v))
      def inverse = new Replace[R, V, U] {
        type Out = L
        def inverse = self
        def apply(t: R, u: U) = lh.inverse(r.inverse(rh(t), u))
      }
    }

}
