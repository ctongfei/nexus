package nexus.layer

import nexus._
import nexus.algebra._
import nexus.op._

/**
 * A fully-connected neural layer (affine transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Affine[T[_ <: $$], R, A, B] private(
                                           val W: Param[T[B::A::$]],
                                           val b: Param[T[B::$]]
                                         )
                                         (implicit T: IsRealTensor[T, R])
  extends Module[T[A::$], T[B::$]]
{

  /**
   * The linear transformation matrix of this layer.
   */
  def weight = W

  /**
   * The additive bias of this layer.
   */
  def bias = b

  type Input = A
  val Input = T.typeOf(W.value).tail.head

  type Output = B
  val Output = T.typeOf(b.value).head

  def apply(x: Expr[T[A::$]]): Expr[T[B::$]] =
    Add(MVMul(W, x), b)

}

object Affine {

  @volatile private var i = 0

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes B::A::$)
   * @param b Bias vector (axes B::$)
   */
  def from[T[_ <: $$], R, A, B]
  (W: Param[T[B::A::$]], b: Param[T[B::$]])(implicit T: IsRealTensor[T, R]) = new Affine[T, R, A, B](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_ <: $$], R, A, B](in: (A, Int), out: (B, Int))(implicit T: IsRealTensor[T, R]) = {
    import T._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor(0f, 1f, aB::aA::$, Array(nB, nA)), name = s"Affine$i.weight")
    val b = Param(newGaussianTensor(0f, 1f, aB::$, Array(nB)), name = s"Affine$i.bias")
    i += 1
    from[T, R, A, B](W, b)
  }

}
