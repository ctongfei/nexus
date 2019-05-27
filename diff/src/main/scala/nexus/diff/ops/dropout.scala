package nexus.diff.ops

import nexus.diff._
import nexus._

/**
 * Dropout.
 */
object Dropout extends ParameterizedPolyOp1 {

  implicit def dropoutF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = (rate: Double) =>
    new P[T[I], T[I]] {
      def name = s"Dropout[rate = $rate]"
      def tag = Tag.realTensor[T, R, I]
      def forward(x: T[I]) = x // TODO: not implemented
      def backward(dy: T[I], y: T[I], x: T[I]) = dy // as if it does not exist
    }

}
