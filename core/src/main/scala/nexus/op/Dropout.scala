package nexus.op

import nexus._
import nexus.algebra._

/**
 * Dropout.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Dropout(parameter: Double) extends ParaPolyDOp1[Double, DropoutF]

trait DropoutF[P, X, Y] extends (P => DOp1[X, Y])

object DropoutF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: TypedRealTensorOps[T, D]) = new DropoutF[Double, T[A], T[A]] {
    def apply(rate: Double) = new DOp1[T[A], T[A]] {
      def name = s"Dropout[$rate]"
      def gradOps = T.ground[A]
      def forward(x: T[A]) = x // TODO: Not implemented!
      def backward(dy: T[A], y: T[A], x: T[A]) = dy // as if it doesn't exist
    }
  }

}
