package nexus.modules

import nexus._
import nexus.ops._
import nexus.tensor._
import nexus.util._

/**
 * A fully-connected neural layer without bias term (linear transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Linear[T[_], R, a <: Dim, b <: Dim] private(
  val weight: Param[T[(b, a)]]
)(implicit T: IsRealTensorK[T, R])
  extends Module1[T[a], T[b]]
{

  type Input = a

  type Output = b

  def parameters = Set(weight)

  def apply(x: Expr[T[a]]): Expr[T[b]] = MVMul(weight, x)

}

object Linear {

  def from[T[_], R, a <: Dim, b <: Dim]
  (W: Param[T[(b, a)]])(implicit T: IsRealTensorK[T, R]) = new Linear[T, R, a, b](W)

  /**
   * Constructs a linear layer with default parameters.
   * @example  Linear(In -> 784, Out -> 200)
   */
  def apply[T[_], R, a <: Dim, b <: Dim](in: (a, Int), out: (b, Int))(implicit ops: IsRealTensorK[T, R]): Unit = {
    import ops._
    val (aA, nA) = in
    val (aB, nB) = out
    val key = ExprName.nextId(typeName(Linear))
    val weight = Param(newGaussianTensor[(b, a)](0f, 1f, Array(nB, nA)), name = s"$key.weight")
    from[T, R, a, b](weight)
  }

}
