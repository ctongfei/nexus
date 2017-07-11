package nexus.layer

import nexus._
import nexus.op._

/**
 * A fully-connected neural layer without bias term (linear transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Linear[T[_, _ <: $$], D, A, B] private(
  val weight: Param[T[D, B::A::$]]
)(implicit val env: Env[T, D])
  extends Module[T[D, A::$], T[D, B::$]]
{
  def apply(x: Expr[T[D, A::$]]): Expr[T[D, B::$]] = MVMul(weight, x)
}

object Linear {

  @volatile private var i = 0

  def from[T[_, _ <: $$], D, A, B]
  (W: Param[T[D, B::A::$]])(implicit env: Env[T, D]) = new Linear[T, D, A ,B](W)

  /**
   * Constructs a linear layer with default parameters.
   * @example  Linear(In -> 784, Out -> 200)
   */
  def apply[T[_, _ <: $$], D, A, B](in: (A, Int), out: (B, Int))(implicit env: Env[T, D]): Unit = {
    import env._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor(0f, 1f, aB::aA::$, Array(nB, nA)), name = s"Linear$i.weight")
    i += 1
    from[T, D, A, B](W)
  }
}
