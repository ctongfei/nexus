package nexus.tensor.typelevel

import shapeless._

/**
 * Typelevel function that replaces type [[U]] in [[A]] to [[V]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Replace[A, U, V] extends DepFn2[A, V] {
  type Out

  /** Constructs the inverse replacement evidence. */
  def inverse: Replace.Aux[Out, V, U, A]
}

object Replace {

  def apply[A, U, V](implicit r: Replace[A, U, V]): Aux[A, U, V, r.Out] = r
  type Aux[A, U, V, B] = Replace[A, U, V] { type Out = B }

  implicit def case0[A <: HList, U, V]: Aux[U :: A, U, V, V :: A] =
    new Replace[U :: A, U, V] { self =>
      type Out = V :: A
      def apply(l: U :: A, v: V): V :: A = v :: l.tail
      def inverse = new Replace[V :: A, V, U] {
        type Out = U :: A
        def apply(l: V :: A, u: U): U :: A = u :: l.tail
        def inverse = self
      }
    }

  implicit def caseN[At <: HList, Ah, U, V, Bt <: HList]
  (implicit n: Ah =:!= U, r: Replace.Aux[At, U, V, Bt]): Replace.Aux[Ah :: At, U, V, Ah :: Bt] =
    new Replace[Ah :: At, U, V] { self =>
      type Out = Ah :: Bt
      def apply(l: Ah :: At, v: V): Ah :: Bt = l.head :: r(l.tail, v)
      def inverse = new Replace[Ah :: Bt, V, U] {
        type Out = Ah :: At
        def apply(l: Ah :: Bt, u: U): Ah :: At = l.head :: r.inverse(l.tail, u)
        def inverse = self
      }
    }

  implicit def tuple[A, Al <: HList, U, V, Bl <: HList, B]
  (implicit al: ToHList.Aux[A, Al], r: Replace.Aux[Al, U, V, Bl], bl: FromHList.Aux[Bl, B]): Replace.Aux[A, U, V, B] =
    new Replace[A, U, V] { self =>
      type Out = B
      def apply(t: A, v: V): B = bl(r(al(t), v))
      def inverse = new Replace[B, V, U] {
        type Out = A
        def inverse = self
        def apply(t: B, u: U) = al.invert(r.inverse(bl.invert(t), u))
      }
    }

}
