package nexus.op

import nexus._
import nexus.impl._

/**
 * Sigmoid activation function that maps any real output to the interval (0, 1).
 *
 * Input: a vector 「bb"x"」.
 *
 * Output: a vector 「bb"y"」, of the same length as 「bb"x"」, computed as
 * 「y_i = 1/(1 + e^(-x_i))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends PolyDOp1[SigmoidF]

@implicitNotFound("Cannot apply Sigmoid on ${X}.")
trait SigmoidF[X, Y] extends DOp1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedRealTensorOps[T, D]) = new SigmoidF[T[A], T[A]] {
    import ops._
    def gradOps = ops.ground[A]
    def forward(x: T[A]) = sigmoid(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y |*| addS(-y, D.one)
  }

}
