package nexus.diff.ops

import nexus.diff._
import nexus._
import nexus.syntax._

/**
 * Linear interpolation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object LinearInterpolation extends PolyOp3 {

  implicit def lerpScalarF[R](implicit R: IsReal[R]): F[R, R, R, R] =
    new F[R, R, R, R] {
      def name = "LinearInterpolation"
      def tag = Tag.real[R]
      def forward(x1: R, x2: R, t: R) = (R.one - t) * x1 + t * x2
      def backward1(dy: R, y: R, x1: R, x2: R, t: R) = dy * (R.one - t)
      def backward2(dy: R, y: R, x1: R, x2: R, t: R) = dy * t
      def backward3(dy: R, y: R, x1: R, x2: R, t: R) = dy * (x2 - x1)
    }

  // TODO: generalize to all vector spaces?
  implicit def lerpTensorF[T[_], R, I](implicit T: IsRealTensorK[T, R]): F[T[I], T[I], R, T[I]] =
    new F[T[I], T[I], R, T[I]] {
      import T._
      def name = "LinearInterpolation"
      def tag = Tag.realTensor[T, R, I]
      def forward(x1: T[I], x2: T[I], t: R) = (x1 :* (R.one - t)) + (x2 :* t)
      def backward1(dy: T[I], y: T[I], x1: T[I], x2: T[I], t: R) = dy :* (R.one - t)
      def backward2(dy: T[I], y: T[I], x1: T[I], x2: T[I], t: R) = dy :* t
      def backward3(dy: T[I], y: T[I], x1: T[I], x2: T[I], t: R) = dy ⋅ (x2 - x1)
    }

}
