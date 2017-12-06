package nexus.layer

import nexus._
import nexus.algebra._
import nexus.op._

/**
 * A fully-connected neural layer without bias term (linear transformation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Linear[T[_ <: $$], R, A, B] private(
  val weight: Param[T[B::A::$]]
)(implicit T: IsTypedRealTensor[T, R])
  extends Module[T[A::$], T[B::$]]
{

  type Input = A
  val Input = T.typeOf(weight.value).tail.head

  type Output = B
  val Output = T.typeOf(weight.value).head

  def apply(x: Expr[T[A::$]]): Expr[T[B::$]] = MVMul(weight, x)
}

object Linear {

  @volatile private var i = 0

  def from[T[_ <: $$], R, A, B]
  (W: Param[T[B::A::$]])(implicit T: IsTypedRealTensor[T, R]) = new Linear[T, R, A, B](W)

  /**
   * Constructs a linear layer with default parameters.
   * @example  Linear(In -> 784, Out -> 200)
   */
  def apply[T[_ <: $$], R, A, B](in: (A, Int), out: (B, Int))(implicit ops: IsTypedRealTensor[T, R]): Unit = {
    import ops._
    val (aA, nA) = in
    val (aB, nB) = out
    val W = Param(newGaussianTensor(0f, 1f, aB::aA::$, Array(nB, nA)), name = s"Linear$i.weight")
    i += 1
    from[T, R, A, B](W)
  }
}
