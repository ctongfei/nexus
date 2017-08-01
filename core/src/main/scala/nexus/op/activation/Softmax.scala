package nexus.op.activation

import nexus._

/**
 * Softmax activation function.
 *
 * Input: a vector \(\mathbf{x}\).
 *
 * Output: a vector \(\mathbf{y}\), computed as
 *
 * \( y_i = \dfrac {\exp{x_i}} {\sum_j {\exp{x_j}}} \).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Softmax extends PolyOp1[SoftmaxF]

@impMsg("Cannot apply Softmax on ${X}.")
trait SoftmaxF[X, Y] extends Op1[X, Y] {
  def name = "Softmax"
}

object SoftmaxF {

  implicit def SoftmaxImpl[T[_, _ <: $$], D, A](implicit env: Env[T, D]) = new SoftmaxF[T[D, A::$], T[D, A::$]] {
    import env._
    def forward(x: T[D, A::$]) = {
      val expX = exp(x)
      scale(expX, getScalar(untype(inv(sum(expX)))))
    }
    def backward(dy: T[D, A::$], y: T[D, A::$], x: T[D, A::$]) = {
      val dyy = dy |*| y
      val sumdyy = sum(dyy)
      val r = dyy - scale(y, getScalar(untype(sumdyy)))
      r
    }
  }

}
