package nexus.diff.ops

import nexus.diff._
import nexus._
import nexus.syntax._


object Entropy extends PolyOp1 {

  implicit def entropyF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = new F[T[I], R] {
    import T._
    def name = "Entropy"
    def tag = Tag.real[R]
    def forward(x: T[I]) = -sum(x |*| log(x))
    def backward(dy: R, y: R, x: T[I]) = (log(x) +# 1) :* (-dy)
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

  implicit def crossEntropyF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[I], T[I], R] =
    new F[T[I], T[I], R] {
      import T._
      def name = "CrossEntropy"
      def tag = Tag.real[R]
      def forward(p: T[I], q: T[I]) = -sum(p |*| log(q))
      def backward1(dy: R, y: R, p: T[I], q: T[I]) = -log(q) :* dy
      def backward2(dy: R, y: R, p: T[I], q: T[I]) = -(p |/| q) :* dy
    }

}

object KullbackLeiblerDivergence extends PolyOp2 {

  implicit def kullbackLeiblerDivergenceF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[I], T[I], R] =
    new F[T[I], T[I], R] {
      def name = "KullbackLeiblerDivergence"
      def tag = Tag.real[R]
      def forward(x1: T[I], x2: T[I]) = ???
      def backward1(dy: R, y: R, x1: T[I], x2: T[I]) = ???
      def backward2(dy: R, y: R, x1: T[I], x2: T[I]) = ???
    }

}
