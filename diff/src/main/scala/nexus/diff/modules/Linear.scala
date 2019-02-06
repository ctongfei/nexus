package nexus.diff.modules

import nexus.diff._
import nexus.diff.ops._
import nexus._
import nexus.diff.util._

/**
 * A fully-connected neural layer without bias term (linear transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Linear[T[_], R, X <: Dim, Y <: Dim] private(
  val weight: Param[T[(Y, X)]]
)(implicit T: IsRealTensorK[T, R])
  extends Module1[T[X], T[Y]]
{

  type Input = X

  type Output = Y

  def parameters = Set(weight)

  def apply[F[_] : Algebra](x: F[T[X]]) = MVMul(weight.as, x)
}

object Linear {

  def from[T[_], R, a <: Dim, b <: Dim]
  (W: Param[T[(b, a)]])(implicit T: IsRealTensorK[T, R]) = new Linear[T, R, a, b](W)

  /**
   * Constructs a linear layer with default parameters.
   * @example  Linear(In -> 784, Out -> 200)
   */
  def apply[T[_], R, X <: Dim, Y <: Dim]
    (in: (X, Int), out: (Y, Int))
    (implicit T: IsRealTensorK[T, R], name: sourcecode.Name): Unit =
  {
    val (aX, nX) = in
    val (aY, nY) = out
    val weight = Param(
      T.newGaussianTensor[(Y, X)](0f, 1f, Array(nY, nX)),
      name = s"${name.value}.weight"
    )
    from[T, R, X, Y](weight)
  }

}
