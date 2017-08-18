package nexus.op

import nexus._
import nexus.impl._

/**
 * Element-wise exponentiation.
 *
 * Input: Any tensor 「bb"x"」 with axes 「i_1 , ... , i_d」.
 *
 * Output: A tensor 「bb"y"」 with the same shape as 「bb"x"」, computed as
 * 「y_(i_1, ..., i_d) = exp(x_(i_1, ..., i_d))」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object EExp extends PolyDOp1[EExpF]

trait EExpF[X, Y] extends DOp1[X, Y] {
  def name = "EExp"
}

object EExpF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedMathOps[T, D]) = new EExpF[T[A], T[A]] {
    import ops._
    def gradOps = ops.ground[A]
    def forward(x: T[A]) = exp(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y
  }

}
