package nexus.modules

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.ops._
import nexus.util._

/**
 * A fully-connected neural layer without bias term (linear transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Linear[T[_], R, A <: Dim, B <: Dim] private(
  val weight: Param[T[(B, A)]]
)(implicit T: IsRealTensorK[T, R])
  extends Module1[T[A], T[B]]
{

  type Input = A

  type Output = B

  def parameters = Set(weight)

  def apply(x: Expr[T[A]]): Expr[T[B]] = MVMul(weight, x)

}

object Linear {

  def from[T[_], R, A <: Dim, B <: Dim]
  (W: Param[T[(B, A)]])(implicit T: IsRealTensorK[T, R]) = new Linear[T, R, A, B](W)

  /**
   * Constructs a linear layer with default parameters.
   * @example  Linear(In -> 784, Out -> 200)
   */
  def apply[T[_], R, A <: Dim, B <: Dim](in: (A, Int), out: (B, Int))(implicit ops: IsRealTensorK[T, R]): Unit = {
    import ops._
    val (aA, nA) = in
    val (aB, nB) = out
    val key = ExprName.nextId(typeName(Linear))
    val weight = Param(newGaussianTensor[(B, A)](0f, 1f, Array(nB, nA)), name = s"$key.weight")
    from[T, R, A, B](weight)
  }

}
