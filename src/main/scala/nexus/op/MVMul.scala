package nexus.op

import nexus._
import shapeless._

/**
 * Matrix-vector multiplication.
 * - Shape: (B::A, A) => B
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MVMul extends GenOp2[MVMulF]

trait MVMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "MVMul"
}

object MVMulF {

  implicit def MVMulImpl[T[D, _ <: HList], D, A, B](implicit env: Env[T, D]): MVMulF[T[D, B::A::$], T[D, A::$], T[D, B::$]] =
    new MVMulF[T[D, B::A::$], T[D, A::$], T[D, B::$]] {
      import env._
      def forward(x1: T[D, B::A::$], x2: T[D, A::$]) = mvMul(x1, x2)
      def backward1(dy: T[D, B::$], y: T[D, B::$], x1: T[D, B::A::$], x2: T[D, A::$]) = vvMul(dy, x2)
      def backward2(dy: T[D, B::$], y: T[D, B::$], x1: T[D, B::A::$], x2: T[D, A::$]) = mvMul(transpose(x1), dy)
    }

}
