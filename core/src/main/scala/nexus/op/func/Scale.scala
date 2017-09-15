package nexus.op.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._


trait ScaleF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Scale"
}

object ScaleF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new ScaleF[T[A], R, T[A]] {
    def tag = T.ground[A]
    def forward(x1: T[A], x2: R) = x1 :* x2
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: R) = dy :* x2
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: R) = dy ⋅ x1
  }

}
