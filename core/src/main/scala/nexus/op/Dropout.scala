package nexus.op

import nexus._

/**
 * Dropout.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Dropout(rate: Double) extends ParaPolyOp1[Double, DropoutF] {
  def parameter = rate
}

trait DropoutF[P, X, Y] extends (P => Op1[X, Y])

object DropoutF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit env: Env[T, D]) = new DropoutF[Double, T[A], T[A]] {
    def apply(rate: Double) = new Op1[T[A], T[A]] {
      def name = s"Dropout[$rate]"
      def forward(x: T[A]) = x // TODO: Not implemented!
      def backward(dy: T[A], y: T[A], x: T[A]) = dy // as if it doesn't exist
    }
  }

}
