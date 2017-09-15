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
  val weight: Param[T[B::A::$]],
  val bias: Param[T[B::$]]
)(implicit val ops: IsTypedRealTensor[T, R])
  extends DModule[T[A::$], T[B::$]]
{

  def apply(x: Expr[T[A::$]]): DExpr[T[B::$]] =
    Add(MVMul(weight, x), bias)

}

object Affine {

  @volatile private var i = 0

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes B::A::$)
   * @param b Bias vector (axes B::$)
   */
  def from[T[_ <: $$], D, A, B]
  (W: Param[T[B::A::$]], b: Param[T[B::$]])(implicit ops: IsTypedRealTensor[T, D]) = new Affine[T, D, A, B](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_ <: $$], D, A, B](in: (A, Int), out: (B, Int))(implicit ops: IsTypedRealTensor[T, D]) = {
    import ops._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor(0f, 1f, aB::aA::$, Array(nB, nA)), name = s"Affine$i.weight")
    val b = Param(newGaussianTensor(0f, 1f, aB::$, Array(nB)), name = s"Affine$i.bias")
    i += 1
    from[T, D, A, B](W, b)
  }

}
