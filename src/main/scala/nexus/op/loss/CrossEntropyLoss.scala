package nexus.op.loss

import nexus._
import nexus.cpu._
import shapeless._

/**
 * @author Tongfei Chen
 */
object CrossEntropyLoss extends GenOp2[CrossEntropyLossF]

trait CrossEntropyLossF[YP, YG, L] extends Op2[YP, YG, L] {
  def name = "CrossEntropyLoss"
}

object CrossEntropyLossF {

  implicit def CrossEntropyLossImpl[T[_, _ <: HList], D, A](implicit env: Env[T, D]): CrossEntropyLossF[T[D, A::$], T[D, A::$], T[D, $]] =
    new CrossEntropyLossF[T[D, A::$], T[D, A::$], T[D, $]] {
      import env._
      def forward(yp: T[D, A::$], yg: T[D, A::$]) = {
        val logYp = log(yp)
        val xent = mul(yg, logYp)
        reduceSum(xent)
      }
      def backward1(dl: T[D, $], l: T[D, $], yp: T[D, A::$], yg: T[D, A::$]) = div(yg, yp) // should times dl
      def backward2(dl: T[D, $], l: T[D, $], yp: T[D, A::$], yg: T[D, A::$]) = ???
    }
  
}
