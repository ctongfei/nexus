package nexus.diff.ops

import nexus.diff._
import nexus._

/**
 * @author Tongfei Chen
 */
object Cross extends PolyOp2 {

  implicit def crossF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], T[A]] =
    new F[T[A], T[A], T[A]] {
      def name = "Cross"
      def tag = Tag.realTensor[T, R, A]
      def forward(x1: T[A], x2: T[A]) = ???
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
    }

}
