package nexus

import nexus.shape._
import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * @author Tongfei Chen
 */
trait Tensor[D <: DType, S <: HList] {

  def apply[@specialized R](indices: Int*)(implicit ev: DType.Ev[R, D]): R
  def update[@specialized R](indices: Int*)(newValue: R)(implicit ev: DType.Ev[R, D]): Unit

  def isDense: Boolean

  type Rank = Length[S]#Out
  def rank(implicit e: ToInt[Rank]) = e()

}
