package nexus.op

import nexus._
import nexus.cpu.DenseTensor
import shapeless.HList

/**
 * Negation of any tensor.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Neg extends GenOp1[NegF]

trait NegF[X, Y] extends Op1[X, Y] {
  def name = "Neg"
}

object NegF {

  implicit def NegImpl[UT[D], T[D, A <: HList], D, A <: HList](implicit env: Env[T, D]): NegF[T[D, A], T[D, A]] =
    new NegF[T[D, A], T[D, A]] {
      def forward(x: T[D, A]) = -x
      def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = -dy
    }

}

