package nexus.op

import nexus._
import nexus.impl._

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
object Softmax extends PolyDOp1[SoftmaxF]

@implicitNotFound("Cannot apply Softmax on ${X}.")
trait SoftmaxF[X, Y] extends DOp1[X, Y] {
  def name = "Softmax"
}

object SoftmaxF {

  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedRealTensorOps[T, D]) = new SoftmaxF[T[A::$], T[A::$]] {
    import ops._
    def gradOps = ops.ground[A::$]
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
