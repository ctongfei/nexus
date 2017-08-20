package nexus.op

import nexus._
import nexus.impl._

/**
 * Inner product of two tensors.
 *
 * Inputs: two tensors 「bb "a"」 and 「bb "b"」 with the same axes and shape.
 *
 * Output: a scalar, computed as 「y = bb"a" cdot bb"b" = sum_(i_1 i_2 ... i_d)  a_(i_1 ... i_d) b_(i_1 ... i_d)」.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Dot extends PolyDOp2[DotF]

trait DotF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Dot"
}

object DotF {
  
  implicit def tensor[T[_ <: $$], D, A <: $$](implicit ops: TypedRealTensorOps[T, D]) = new DotF[T[A], T[A], T[$]] {
    import ops._
    def gradOps = ops.ground[$]
    def forward(x1: T[A], x2: T[A]) = dot(x1, x2)
    def backward1(dy: T[$], y: T[$], x1: T[A], x2: T[A]) = x2 :* dy
    def backward2(dy: T[$], y: T[$], x1: T[A], x2: T[A]) = x1 :* dy
  }
  
}
