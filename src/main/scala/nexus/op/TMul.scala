package nexus.op

import nexus._
import shapeless._
import shapeless.ops.hlist._

/**
 * General tensor multiplication that marginalizes out all axes between two tensors that match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object TMul extends GenOp2[TMulF]

trait TMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "TMul"
}

object TMulF {

  implicit def TMulImpl[T[D, _ <: HList], D, A <: HList, B <: HList, C <: HList](implicit env: Env[T, D]) = new TMulF[T[D, A], T[D, B], T[D, C]] {
    def forward(x1: T[D, A], x2: T[D, B]) = ???
    def backward1(dy: T[D, C], y: T[D, C], x1: T[D, A], x2: T[D, B]) = ???
    def backward2(dy: T[D, C], y: T[D, C], x1: T[D, A], x2: T[D, B]) = ???
  }

}
