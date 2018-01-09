package nexus.layer

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.op._
import nexus.util._

/**
 * A fully-connected neural layer (affine transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Affine[T[_], R, X: Label, Y: Label] private(
                                           val W: Param[T[(Y, X)]],
                                           val b: Param[T[Y]]
                                         )
                                         (implicit T: IsRealTensorH[T, R])
  extends Func1[T[X], T[Y]]
{

  /** The linear transformation matrix of this layer. */
  def weight = W

  /** The additive bias of this layer. */
  def bias = b

  type Input = X

  type Output = Y

  def apply(x: Expr[T[X]]): Expr[T[Y]] =
    Add(MVMul(W, x), b)

}

object Affine {

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes B::A::$)
   * @param b Bias vector (axes B::$)
   */
  def from[T[_], R, A: Label, B: Label]
  (W: Param[T[(B, A)]], b: Param[T[B]])(implicit T: IsRealTensorH[T, R]) = new Affine[T, R, A, B](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_], R, A: Label, B: Label](
                                          in: (A, Int),
                                          out: (B, Int),
                                          name: String = ExprName.nextId("Affine")
                                        )
                                        (implicit T: IsRealTensorH[T, R]) = {
    import T._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor[(B, A)](0f, 1f, Array(nB, nA)), name = s"$name.weight")
    val b = Param(newGaussianTensor[B](0f, 1f, Array(nB)), name = s"$name.bias")
    from[T, R, A, B](W, b)
  }

}
