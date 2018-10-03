package nexus.ops

import nexus._

/**
 * Linear interpolation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object LinearInterpolation extends PolyOp3 {

  implicit def lerpScalarF[R](implicit R: IsReal[R]): F[R, R, R, R] =
    new F[R, R, R, R] {
      type Tag[r] = IsReal[r]
      def name = "LinearInterpolation"
      def tag = R
      def forward(x1: R, x2: R, t: R) = (R.one - t) * x1 + t * x2
      def backward1(dy: R, y: R, x1: R, x2: R, t: R) = dy * (R.one - t)
      def backward2(dy: R, y: R, x1: R, x2: R, t: R) = dy * t
      def backward3(dy: R, y: R, x1: R, x2: R, t: R) = dy * (x2 - x1)
    }

  // TODO: generalize to all vector spaces?
  implicit def lerpTensorF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R, T[A]] =
    new F[T[A], T[A], R, T[A]] {
      import T._
      type Tag[t] = IsRealTensor[t, R]
      def name = "LinearInterpolation"
      def tag = T.ground[A]
      def forward(x1: T[A], x2: T[A], t: R) = (x1 :* (R.one - t)) + (x2 :* t)
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A], t: R) = dy :* (R.one - t)
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A], t: R) = dy :* t
      def backward3(dy: T[A], y: T[A], x1: T[A], x2: T[A], t: R) = dy ⋅ (x2 - x1)
    }

}
