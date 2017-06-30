package nexus.op.activation

import nexus._
import nexus.cpu.DenseTensor
import nexus.op._
import shapeless._

/**
 * Sigmoid activation function that maps any real output to the interval (0, 1).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sigmoid extends GenOp1[SigmoidF]

trait SigmoidF[X, Y] extends Op1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  implicit def SigmoidImpl[T[_, _ <: HList], D, A <: HList](implicit env: Env[T, D]) = new SigmoidF[T[D, A], T[D, A]] {
    import env._
    def forward(x: T[D, A]) = sigmoid(x)
    def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = mul(dy, mul(y, addScalar(-y, one)))
  }
}
