package nexus.layer

import nexus._
import nexus.op._
import shapeless._

/**
 * A fully-connected neural layer (affine transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Affine[T[_, _ <: HList], D, A, B] private(
  val W: Param[T[D, B::A::$]],
  val b: Param[T[D, B::$]]
)(implicit val env: Env[T, D])
  extends Module[T[D, A::$], T[D, B::$]]
{

  def parameters = Seq(W, b)

  def apply(x: Expr[T[D, A::$]]): Expr[T[D, B::$]] = Add(MVMul(W, x), b)
}

object Affine {

  @volatile private var i = 0

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes B::A::$)
   * @param b Bias vector (axes B::$)
   */
  def withParams[T[_, _ <: HList], D, A, B]
  (W: Param[T[D, B::A::$]], b: Param[T[D, B::$]])(implicit env: Env[T, D]) = new Affine[T, D, A, B](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_, _ <: HList], D, A, B](in: (A, Int), out: (B, Int))(implicit env: Env[T, D]) = {
    import env._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newTensor(aB::aA::$, Array(nB, nA)), name = s"Affine$i.W")
    val b = Param(newTensor(aB::$, Array(nB)), name = s"Affine$i.b")
    i += 1
    withParams[T, D, A, B](W, b)
  }

}
