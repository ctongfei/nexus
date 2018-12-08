package nexus.diff.ops

import nexus.diff._
import nexus.tensor._

/**
 * @author Tongfei Chen
 */
object Sum extends PolyOp1 {
  implicit def sumF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], R] =
    new F[T[A], R] {
      def name = "Sum"
      def tag = Tag.real[R]
      def forward(x: T[A]) = T.sum(x)
      def backward(dy: R, y: R, x: T[A]) = ???
    }
}

object Prod extends PolyOp1

object Max extends PolyOp1

object Min extends PolyOp1

