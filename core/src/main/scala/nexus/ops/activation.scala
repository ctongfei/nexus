package nexus.ops

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.algebra.typelevel._

/**
 * Rectified linear unit.
 *
 * References:
 *  - R Hahnloser, R Sarpeshkar, M A Mahowald, R J Douglas, H S Seung (2000):
 *    Digital selection and analogue amplification coexist in a cortex-inspired silicon cicuit. Nature. 405.
 *  - R Hahnloser, H S Seung (2001):
 *    Permitted and forbidden sets in symmetric threshold-linear networks. NIPS.
 *  - X Glorot, A Bordes, Y Bengio (2011):
 *    Deep sparse rectifier neural networks. AISTATS.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ReLU extends PolyOp1 {
  implicit def reLUF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = "ReLU"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.relu(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| T.pos(x)
    }
}

/**
 * Scaled exponential linear units.
 *
 * Reference:
 *  - G Klambauer, T Unterthiner, A Mayr, S Hochreiter (2017): Self-normalizing neural networks. NIPS.
 */
object SELU extends PolyOp1 {
  implicit def seluF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = "SELU"
      def tag = T.ground[A]
      def forward(x: T[A]) = ???
      def backward(dy: T[A], y: T[A], x: T[A]) = ???
    }
}

/**
 * Sigmoid (a.k.a. logistic) activation function that maps any real output to the interval (0, 1).
 * - Input: any tensor 「bb"x"」.
 * - Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = 1/(1 + e^(-x_i))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends PolyOp1 {
  implicit def sigmoidF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = "Sigmoid"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.sigmoid(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y |*| T.addS(-y, T.R.one) // TODO: inplace
    }
}

/**
 * Softplus activation function.
 * - Input: any tensor 「bb"x"」.
 * - Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = log(1 + exp x_i)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object SoftPlus extends PolyOp1 {
  implicit def softPlusF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = "SoftPlus"
      def tag = T.ground[A]
      def forward(x: T[A]) = T.eLog1p(T.eExp(x))
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| T.sigmoid(x)
    }
}

/**
 * Softmax activation function.
 * - Input: a vector 「bb"x"」.
 * - Output: a vector 「bb"y"」, of the same size as 「bb"x"」, computed as
 * 「y_i = (exp x_i) / (sum_j exp x_j)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Softmax extends PolyOp1 {
  implicit def softmaxF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] =
    new F[T[A], T[A]] {
      type Tag[t] = IsRealTensor[t, R]
      def name = "Softmax"
      def tag = T.ground[A]
      def forward(x: T[A]) = {
        import T._
        val expX = eExp(x)
        expX :* R.inv(sum(expX)) //TODO: numerical stability
      }
      def backward(dy: T[A], y: T[A], x: T[A]) = {
        import T._
        val dyy = dy |*| y
        dyy - (y :* sum(dyy))
    }
  }
}
