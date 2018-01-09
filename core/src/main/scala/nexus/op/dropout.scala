package nexus.op

import nexus._
import nexus.algebra._

/**
 * Dropout.
 */
object Dropout extends ParamPolyOp1 {

  implicit def tensor[T[_], R, A](implicit T: IsRealTensorH[T, R]) = new F[Double, T[A], T[A]] {
    def apply(rate: Double) = new Op1[T[A], T[A]] {
      def name = s"Dropout[rate = $rate]"
      def tag(tx: Type[T[A]]) = tx
      def differentiable = true
      def forward(x: T[A]) = x // TODO: not implemented
      def backward(dy: T[A], y: T[A], x: T[A]) = dy // as if it does not exist
    }
  }

}
