package nexus.op.activation

import nexus._
import nexus.impl._

/**
 * Softmax activation function.
 *
 * Input: a vector \(\mathbf{x}\).
 *
 * Output: a vector \(\mathbf{y}\), computed as
 *
 * \( y_i = \dfrac {\exp{x_i}} {\sum_j {\exp{x_j} } } \).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Softmax extends PolyOp1[SoftmaxF]

@implicitNotFound("Cannot apply Softmax on ${X}.")
trait SoftmaxF[X, Y] extends Op1[X, Y] {
  def name = "Softmax"
}

object SoftmaxF {

  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedMathOps[T, D]) = new SoftmaxF[T[A::$], T[A::$]] {
    import ops._
    def _ops = ops.ground[A::$]
    def forward(x: T[A::$]) = {
      val expX = exp(x)
      expX :* inv(sum(exp(x)))
    }
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = {
      val dyy = dy |*| y
      dyy - (y :* sum(dyy))
    }
  }

}
