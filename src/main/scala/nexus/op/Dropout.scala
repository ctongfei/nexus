package nexus.op

import nexus._

/**
 * Dropout.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Dropout(rate: Double) extends ArgPolyOp1[Double, DropoutF] {
  def arg = rate
}

trait DropoutF[Arg, X, Y] extends ArgOp1[Arg, X, Y]

object DropoutF {

  implicit def DropoutImpl[T[_, _ <: $$], D, A <: $$](implicit env: Env[T, D]) = new DropoutF[Double, T[D, A], T[D, A]] {
    def apply(rate: Double) = new Op1[T[D, A], T[D, A]] {
      def name = s"Dropout[$rate]"
      def forward(x: T[D, A]) = x // TODO: Not implemented!
      def backward(dy: T[D, A], y: T[D, A], x: T[D, A]) = dy // as if it doesn't exist
    }
  }

}
