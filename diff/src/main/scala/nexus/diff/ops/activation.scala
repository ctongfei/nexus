package nexus.diff.ops

import nexus.diff._
import nexus._
import nexus.diff.ops.mixin._
import nexus.syntax._

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
object ReLU extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "ReLU"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = ???
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = ???
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = T.relu(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = dy |*| T.pos(x)
}

/**
 * Logistic (a.k.a. sigmoid) activation function that maps any real output to the interval (0, 1).
 * - Input: any tensor 「bb"x"」.
 * - Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = 1/(1 + e^(-x_i))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Logistic extends PolyOp1 { // TODO: ufunc
  implicit def logisticF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I]] =
    new P[T[I], T[I]] {
      def name = "Logistic"
      def tag = Tag.realTensor[T, R, I]
      def forward(x: T[I]) = T.logistic(x)
      def backward(dy: T[I], y: T[I], x: T[I]) = dy |*| y |*| T.addScalar(-y, T.R.one) // TODO: inplace
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
  implicit def softPlusF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I]] =
    new P[T[I], T[I]] {
      def name = "SoftPlus"
      def tag = Tag.realTensor[T, R, I]
      def forward(x: T[I]) = T.log1p(T.exp(x))
      def backward(dy: T[I], y: T[I], x: T[I]) = dy |*| T.logistic(x)
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
  implicit def softmaxF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I]] =
    new P[T[I], T[I]] {
      def name = "Softmax"
      def tag = Tag.realTensor[T, R, I]
      def forward(x: T[I]) = {
        import T._
        val expX = exp(x)
        expX :* R.recip(sum(expX)) //TODO: numerical stability
      }
      def backward(dy: T[I], y: T[I], x: T[I]) = {
        import T._
        val dyy = dy |*| y
        dyy - (y :* sum(dyy))
    }
  }
}

/**
 * The sigmoid-weighted linear unit (SiLU) function.
 * This is equivalent to the Swish-1 activation function.
 *
 * References:
 *  - S Elfwing, E Uchibe, K Doya (2018):
 *    Sigmoid-weighted linear units for neural network function approximation in reinforcement learning.
 *    **Neural networks** (107), pp. 3-11. https://doi.org/10.1016/j.neunet.2017.12.012.
 *  - P Ramachandran, B Zoph, Q V Le (2017):
 *    Searching for activation functions. https://arxiv.org/abs/1710.05941
 */
object SiLU extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "SiLU"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = ???
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = ???
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = x |*| T.logistic(x)
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) =
    dy |*| (y + T.logistic(x) |*| (-y +# 1.0))
}

/**
 * The Gaussian error linear unit (GELU) function.
 *
 * Reference:
 *  - D Hendrycks, K Gimpel (2016): Gaussian error linear units. https://arxiv.org/abs/1606.08415
 */
object GELU extends PolyOp1 with RealElementwisePolyOp1Mixin {
  def name = "GELU"
  def forwardR[R](x: R)(implicit R: IsReal[R]) = ???
  def backwardR[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = ???
  def forwardTR[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) = ???
  def backwardTR[T[_], R, A](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensorK[T, R]) = ???
}
