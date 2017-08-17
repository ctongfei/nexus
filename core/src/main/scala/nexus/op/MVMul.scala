package nexus.op

import nexus._
import nexus.impl._

/**
 * Matrix-vector multiplication.
 *
 * Inputs:
 *  - Matrix \(\mathbf{X}_1\) with axes and shape \((B \to m, A \to n)\).
 *  - Vector \(\mathbf{x}_2\) with axes and shape \((A \to n)\).
 *
 * Output:
 *  - Vector \(\mathbf{y} = \mathbf{X}_1 \mathbf{x}_2\), with shape \((B \to m)\).
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MVMul extends PolyOp2[MVMulF]

trait MVMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "MVMul"
}

object MVMulF {

  implicit def mv[T[_ <: $$], D, A, B](implicit ops: TypedMathOps[T, D]): MVMulF[T[B::A::$], T[A::$], T[B::$]] =
    new MVMulF[T[B::A::$], T[A::$], T[B::$]] {
      import ops._
      def _ops = ops.ground[B::$]
      def forward(x1: T[B::A::$], x2: T[A::$]) = mvMul(x1, x2)
      def backward1(dy: T[B::$], y: T[B::$], x1: T[B::A::$], x2: T[A::$]) = vvMul(dy, x2)
      def backward2(dy: T[B::$], y: T[B::$], x1: T[B::A::$], x2: T[A::$]) = mvMul(transpose(x1), dy)
    }

}
