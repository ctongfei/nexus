package nexus.ops

import nexus._
import nexus.tensor._

/**
 * Dropout.
 */
object Dropout extends ParameterizedPolyOp1 {

  implicit def dropoutF[T[_], R, a](implicit T: IsRealTensorK[T, R]) = (rate: Double) =>
    new F[T[a], T[a]] {
      def name = s"Dropout[rate = $rate]"
      def tag = Tag.realTensor[T, R, a]
      def forward(x: T[a]) = x // TODO: not implemented
      def backward(dy: T[a], y: T[a], x: T[a]) = dy // as if it does not exist
    }

}
