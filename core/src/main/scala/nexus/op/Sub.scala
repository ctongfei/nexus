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

  implicit def tensor[T[D, A <: HList], D, A <: HList](implicit env: Env[T, D]) = new SubF[T[D, A], T[D, A], T[D, A]] {
    def forward(x1: T[D, A], x2: T[D, A]) = x1 - x2
    def backward1(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = dy
    def backward2(dy: T[D, A], y: T[D, A], x1: T[D, A], x2: T[D, A]) = -dy
  }

}
