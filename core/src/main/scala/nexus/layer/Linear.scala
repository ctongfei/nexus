package nexus.layer

import nexus._
import nexus.impl._
import nexus.op._

/**
 * A fully-connected neural layer without bias term (linear transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Linear[T[_ <: $$], D, A, B] private(
  val weight: Param[T[B::A::$]]
)(implicit val ops: TypedRealTensorOps[T, D])
  extends DModule[T[A::$], T[B::$]]
{
  def apply(x: Expr[T[A::$]]): DExpr[T[B::$]] = MVMul(weight, x)
}

object Linear {

  @volatile private var i = 0

  def from[T[_ <: $$], D, A, B]
  (W: Param[T[B::A::$]])(implicit ops: TypedRealTensorOps[T, D]) = new Linear[T, D, A ,B](W)

  /**
   * Constructs a linear layer with default parameters.
   * @example  Linear(In -> 784, Out -> 200)
   */
  def apply[T[_ <: $$], D, A, B](in: (A, Int), out: (B, Int))(implicit ops: TypedRealTensorOps[T, D]): Unit = {
    import ops._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor(0f, 1f, aB::aA::$, Array(nB, nA)), name = s"Linear$i.weight")
    i += 1
    from[T, D, A, B](W)
  }
}
