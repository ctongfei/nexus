package nexus.diff.ops

import nexus.diff._
import nexus.tensor._

/**
 * @author Tongfei Chen
 */
object Cross extends PolyOp2 {

  implicit def crossF[T[_], R, a](implicit T: IsRealTensorK[T, R]): F[T[a], T[a], T[a]] =
    new F[T[a], T[a], T[a]] {
      def name = "Cross"
      def tag = Tag.realTensor[T, R, a]
      def forward(x1: T[a], x2: T[a]) = ???
      def backward1(dy: T[a], y: T[a], x1: T[a], x2: T[a]) = ???
      def backward2(dy: T[a], y: T[a], x1: T[a], x2: T[a]) = ???
    }

}
