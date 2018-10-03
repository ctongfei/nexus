package nexus.ops

import nexus._

/**
 * Dropout.
 */
object Dropout extends ParameterizedPolyOp1 {

  implicit def dropoutF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = (rate: Double) =>
    new F[T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = s"Dropout[rate = $rate]"
      def tag = T.ground[A]
      def forward(x: T[A]) = x // TODO: not implemented
      def backward(dy: T[A], y: T[A], x: T[A]) = dy // as if it does not exist
    }

}
