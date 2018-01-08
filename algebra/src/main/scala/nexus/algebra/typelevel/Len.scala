package nexus.algebra.typelevel

import nexus.algebra._
import shapeless.{HList, HNil}

/**
 * A simpler [[shapeless.ops.hlist.Length]] without using the `Aux` pattern.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Len[L <: HList] {
  def apply(): Int
}

object Len {
  def apply[L <: HList](implicit l: Len[L]): Len[L] = l

  implicit def case0: Len[HNil] = new Len[HNil] {
    def apply() = 0
  }

  implicit def caseN[H, T <: HList](implicit t: Len[T]): Len[H :: T] = new Len[H :: T] {
    override def apply() = t() + 1
  }

}
