package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.algebra.typelevel._

/**
 * The cross entropy function.
 *  - Input 1: the predicted probability of labels (\(\mathbf{\hat y}\)), which should be a rank-1 tensor;
 *  - Input 2: the gold labels (\(\mathbf{y}\)), which should be a rank-1 tensor.
 *
 * The output is the loss function value, which is a scalar value, computed as
 *
 * \( \mathscr{L}(\mathbf{\hat y}, \mathbf{y}) = -\sum_{i}{y_i \log \hat y_i} \).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object CrossEntropy extends PolyOp2 {

  implicit def crossEntropyF[T[_], R, A: Label](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R] =
    new F[T[A], T[A], R] {
      import T._
      def name = "CrossEntropy"
      def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = T.R
      def forward(p: T[A], q: T[A]) = -(sum(p |*| eLog(q)))
      def backward1(dy: R, y: R, p: T[A], q: T[A]) = -eLog(q) :* dy
      def backward2(dy: R, y: R, p: T[A], q: T[A]) = -(p |/| q) :* dy
    }

}

object KullbackLeiblerDivergence extends PolyOp2 {

  implicit def kullbackLeiblerDivergenceF[T[_], R, A: Label](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R] =
    new F[T[A], T[A], R] {
      def name = "KullbackLeiblerDivergence"
      def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = T.R
      def forward(x1: T[A], x2: T[A]) = ???
      def backward1(dy: R, y: R, x1: T[A], x2: T[A]) = ???
      def backward2(dy: R, y: R, x1: T[A], x2: T[A]) = ???
    }

}
