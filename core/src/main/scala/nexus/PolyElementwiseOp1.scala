package nexus

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
abstract class PolyElementwiseOp1[EvE[e], EvT[t[_], e] <: IsTensorK[t, e]] extends PolyOp1 { poly =>

  def name: String
  def forward[E](x: E)(implicit E: EvE[E]): E
  def backward[E](dy: E, y: E, x: E)(implicit E: EvE[E]): E

  def forwardElementwise[T[_], E, A](x: T[A])(implicit T: EvT[T, E]): T[A]
  def backwardElementwise[T[_], E, A](dy: T[A], y: T[A], x: T[A])(implicit T: EvT[T, E]): T[A]

  implicit def f[E](implicit E: EvE[E]): F[E, E] =
    new F[E, E] {
      type Tag[e] = EvE[e]
      def name = poly.name
      def tag = E
      def forward(x: E) = poly.forward(x)
      def backward(dy: E, y: E, x: E) = poly.backward(dy, y, x)
    }

  object Elementwise extends PolyOp1 {

    implicit def f[T[_], E, A](implicit T: EvT[T, E]): F[T[A], T[A]] =
      new F[T[A], T[A]] {
        type Tag[ta] = IsTensor[T[A], E]
        def name = s"${poly.name}.Elementwise"
        def tag = T.ground[A]
        def forward(x: T[A]) = poly.forwardElementwise(x)
        def backward(dy: T[A], y: T[A], x: T[A]) = poly.backwardElementwise(dy, y, x)
      }

  }

}
