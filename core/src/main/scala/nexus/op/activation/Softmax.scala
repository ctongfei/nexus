package nexus.op.activation

import nexus._
import nexus.algebra._
import nexus.op._

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

  implicit def vector[T[_ <: $$], R, A](implicit T: TypedRealTensorOps[T, R]) = new SoftmaxF[T[A::$], T[A::$]] {
    import T._
    def gradOps = T.ground[A::$]
    def forward(x: T[A::$]) = { // TODO: numerical stable algorithm: first scale by max
      val expX = eExp(x)
      expX :* R.inv(sum(expX))
    }
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = {
      val dyy = dy |*| y
      dyy - (y :* sum(dyy))
    }
  }

}
