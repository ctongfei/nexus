package nexus.op

import nexus._
import shapeless._

/**
 * Subtraction of two tensors of the same axes and shape.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sub extends PolyOp2[SubF]

trait SubF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "Sub"
}

object SubF {

  implicit def tensor[T[A <: HList], D, A <: HList](implicit env: Env[T, D]) = new SubF[T[A], T[A], T[A]] {
    def forward(x1: T[A], x2: T[A]) = x1 - x2
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = -dy
  }

}
