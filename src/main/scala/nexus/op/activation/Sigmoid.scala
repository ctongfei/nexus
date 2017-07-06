package nexus.op.activation

import nexus._
import nexus.cpu.DenseTensor
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
object Sigmoid extends GenOp1[SigmoidF]

@impMsg("Cannot apply Sigmoid on ${X}.")
trait SigmoidF[X, Y] extends Op1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  implicit def SigmoidImpl[T[_, _ <: HList], D, A <: HList](implicit env: Env[T, D]) = new SigmoidF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = sigmoid(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy |*| y |*| addScalar(-y, one)
  }
}
