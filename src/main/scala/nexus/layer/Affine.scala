package nexus.layer

import nexus._
import nexus.op._

/**
 * A fully-connected neural layer (affine transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Affine[T[_, _ <: $$], D, A, B] private(
  val weight: Param[T[D, B::A::$]],
  val bias: Param[T[D, B::$]]
)(implicit val env: Env[T, D])
  extends Module[T[D, A::$], T[D, B::$]]
{
  def apply(x: Expr[T[D, A::$]]): Expr[T[D, B::$]] = Add(MVMul(weight, x), bias)
}

object Affine {

  @volatile private var i = 0

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes B::A::$)
   * @param b Bias vector (axes B::$)
   */
  def from[T[_, _ <: $$], D, A, B]
  (W: Param[T[D, B::A::$]], b: Param[T[D, B::$]])(implicit env: Env[T, D]) = new Affine[T, D, A, B](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_, _ <: $$], D, A, B](in: (A, Int), out: (B, Int))(implicit env: Env[T, D]) = {
    import env._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor(0f, 1f, aB::aA::$, Array(nB, nA)), name = s"Affine$i.weight")
    val b = Param(newGaussianTensor(0f, 1f, aB::$, Array(nB)), name = s"Affine$i.bias")
    i += 1
    from[T, D, A, B](W, b)
  }

}
