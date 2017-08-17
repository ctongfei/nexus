package nexus.op.activation

import nexus._
import nexus.cpu.DenseTensor
import nexus.impl._
import nexus.op._
import shapeless._

/**
 * Sigmoid activation function that maps any real output to the interval (0, 1).
 * The input should be a vector \(\mathbf{x}\).
 *
 * The output is also a vector \(\mathbf{y}\), computed as
 *
 * \(y_i = \dfrac{1}{1 + e^{-x_i}}\).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends PolyOp1[SigmoidF]

@implicitNotFound("Cannot apply Sigmoid on ${X}.")
trait SigmoidF[X, Y] extends Op1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]) = new SigmoidF[T[A], T[A]] {
    import ops._
    def _ops = ops.ground[A]
    def forward(x: T[A]) = sigmoid(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y |*| addS(-y, D.one)
  }

}
