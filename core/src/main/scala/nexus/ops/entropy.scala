package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.algebra.typelevel._

object Entropy extends PolyOp1 {

  implicit def entropyF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = new F[T[A], R] {
    import T._
    def name = "Entropy"
    def tag = T.R
    def forward(x: T[A]) = -sum(x |*| eLog(x))
    def backward(dy: R, y: R, x: T[A]) = (eLog(x) +# 1) :* (-dy)
  }

}

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

  implicit def crossEntropyF[T[_], R, A <: Dim](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R] =
    new F[T[A], T[A], R] {
      import T._
      def name = "CrossEntropy"
      def tag = T.R
      def forward(p: T[A], q: T[A]) = -sum(p |*| eLog(q))
      def backward1(dy: R, y: R, p: T[A], q: T[A]) = -eLog(q) :* dy
      def backward2(dy: R, y: R, p: T[A], q: T[A]) = -(p |/| q) :* dy
    }

}

object KullbackLeiblerDivergence extends PolyOp2 {

  implicit def kullbackLeiblerDivergenceF[T[_], R, A <: Dim](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R] =
    new F[T[A], T[A], R] {
      def name = "KullbackLeiblerDivergence"
      def tag = T.R
      def forward(x1: T[A], x2: T[A]) = ???
      def backward1(dy: R, y: R, x1: T[A], x2: T[A]) = ???
      def backward2(dy: R, y: R, x1: T[A], x2: T[A]) = ???
    }

}
