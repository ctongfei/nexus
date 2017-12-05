package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Rectified linear unit.
 * - Shape: `RealTensor[Axes] => RealTensor[Axes]`
 * - Input: Real tensor 「bb"x"」 with axes `Axes`.
 * - Output: Real tensor 「bb"y」, axes `Axes`, computed as.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object ReLU extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
  def name = "ReLU"
  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.relu(x)
  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.pos(x)
}

/**
 * Sigmoid (a.k.a. logistic) activation function that maps any real output to the interval (0, 1).
 *
 * Input: any tensor 「bb"x"」.
 *
 * Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = 1/(1 + e^(-x_i))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
  def name = "Sigmoid"
  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.sigmoid(x)
  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| y |*| T.addS(-y, T.R.one)
}

/**
 * Softplus activation function.
 *
 * Input: any tensor 「bb"x"」.
 *
 * Output: a tensor 「bb"y"」, of the same shape as 「bb"x"」, computed as
 * 「y_i = log(1 + exp x_i)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object SoftPlus extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
  def name = "SoftPlus"
  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eLog1p(T.eExp(x))
  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.sigmoid(x)
}

/**
 * Softmax activation function.
 *
 * Input: a vector 「bb"x"」.
 *
 * Output: a vector 「bb"y"」, of the same size as 「bb"x"」, computed as
 * 「y_i = (exp x_i) / (sum_j exp x_j)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Softmax extends VaVaPolyDOp1 {
  def name = "Softmax"
  def forward[T[_ <: $$], E, A](x: T[A::$])(implicit T: IsTypedRealTensor[T, E]) = {
    import T._
    val expX = eExp(x) // TODO: a numerically stabler algorithm
    expX :* R.inv(sum(expX))
  }
  def backward[T[_ <: $$], E, A](dy: T[A::$], y: T[A::$], x: T[A::$])(implicit T: IsTypedRealTensor[T, E]) = {
    import T._
    val dyy = dy |*| y
    dyy - (y :* sum(dyy))
  }
}
