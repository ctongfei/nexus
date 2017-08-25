package nexus.op.activation

import nexus._
import nexus.algebra._

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
object SoftPlus extends PolyDOp1[SoftPlusF]

trait SoftPlusF[X, Y] extends DOp1[X, Y] {
  def name = "SoftPlus"
}

object SoftPlusF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit ops: TypedRealTensorOps[T, R]): SoftPlusF[T[A], T[A]] =
    new SoftPlusF[T[A], T[A]] {
      import ops._
      def gradOps = ops.ground[A]
      def forward(x: T[A]) = log1p(exp(x))
      def backward(dy: T[A], y: T[A], x: T[A]) = sigmoid(x)
    }

}
