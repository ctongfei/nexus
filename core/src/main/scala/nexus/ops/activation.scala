package nexus.ops

import nexus._
import nexus.tensor._
import nexus.tensor.syntax._

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
  implicit def reLUF[T[_], R, a](implicit T: IsRealTensorK[T, R]): F[T[a], T[a]] =
    new F[T[a], T[a]] {
      def name = "ReLU"
      def tag = Tag.realTensor[T, R, a]
      def forward(x: T[a]) = T.relu(x)
      def backward(dy: T[a], y: T[a], x: T[a]) = dy |*| T.pos(x)
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
object Sigmoid extends PolyOp1 { // TODO: ufunc
  implicit def sigmoidF[T[_], R, a](implicit T: IsRealTensorK[T, R]): F[T[a], T[a]] =
    new F[T[a], T[a]] {
      def name = "Sigmoid"
      def tag = Tag.realTensor[T, R, a]
      def forward(x: T[a]) = T.sigmoid(x)
      def backward(dy: T[a], y: T[a], x: T[a]) = dy |*| y |*| T.addS(-y, T.R.one) // TODO: inplace
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
  implicit def softPlusF[T[_], R, a](implicit T: IsRealTensorK[T, R]): F[T[a], T[a]] =
    new F[T[a], T[a]] {
      def name = "SoftPlus"
      def tag = Tag.realTensor[T, R, a]
      def forward(x: T[a]) = T.eLog1p(T.eExp(x))
      def backward(dy: T[a], y: T[a], x: T[a]) = dy |*| T.sigmoid(x)
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
  implicit def softmaxF[T[_], R, a](implicit T: IsRealTensorK[T, R]): F[T[a], T[a]] =
    new F[T[a], T[a]] {
      def name = "Softmax"
      def tag = Tag.realTensor[T, R, a]
      def forward(x: T[a]) = {
        import T._
        val expX = eExp(x)
        expX :* R.inv(sum(expX)) //TODO: numerical stability
      }
      def backward(dy: T[a], y: T[a], x: T[a]) = {
        import T._
        val dyy = dy |*| y
        dyy - (y :* sum(dyy))
    }
  }
}
