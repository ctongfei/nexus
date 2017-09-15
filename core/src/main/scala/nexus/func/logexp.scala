package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

@implicitNotFound("")
trait ExpF[X, Y] extends DOp1[X, Y] {
  def name = "Exp"
}

trait EExpF[X, Y] extends DOp1[X, Y] {
  def name = "EExp"
}

object EExpF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: IsTypedRealTensor[T, D]) = new EExpF[T[A], T[A]] {
    import T._
    def tag = T.ground[A]
    def forward(x: T[A]) = eExp(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y
  }

}
