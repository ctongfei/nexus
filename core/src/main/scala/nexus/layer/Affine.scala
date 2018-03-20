package nexus.layer

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.op._
import nexus.util._

/**
 * A fully-connected neural layer (affine transformation).
 * Variously known as `Dense` or `FullyConnected` in other libraries.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Affine[T[_], R, X: Label, Y: Label] private(
                                                   val W: Param[T[(Y, X)]],
                                                   val b: Param[T[Y]]
                                                 )
                                                 (implicit T: IsRealTensorK[T, R])
  extends Module1[T[X], T[Y]]
{

  def parameters = Set(W, b)

  /** The linear transformation matrix of this layer. */
  def weight = W

  /** The additive bias of this layer. */
  def bias = b

  def apply(x: Expr[T[X]]): Expr[T[Y]] =
    Add(MVMul(W, x), b)

}


object Affine {

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes `(Y, X)`)
   * @param b Bias vector (axes `Y`)
   */
  def from[T[_], R, X: Label, Y: Label]
  (W: Param[T[(Y, X)]], b: Param[T[Y]])(implicit T: IsRealTensorK[T, R]) = new Affine[T, R, X, Y](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_], R, X: Label, Y: Label](
                                          inAxisAndSize: (X, Int),
                                          outAxisAndSize: (Y, Int),
                                          name: String = ExprName.nextId("Affine")
                                        )
                                        (implicit T: IsRealTensorK[T, R]) = {
    import T._
    val (_, nX) = inAxisAndSize
    val (_, nY) = outAxisAndSize
    val W = Param(newGaussianTensor[(Y, X)](0f, 1f, Array(nY, nX)), name = s"$name.weight")
    val b = Param(newGaussianTensor[Y](0f, 1f, Array(nX)), name = s"$name.bias")
    from[T, R, X, Y](W, b)
  }

}
