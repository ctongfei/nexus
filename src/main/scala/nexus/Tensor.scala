package nexus

import shapeless._
import shapeless.ops.hlist._
import shapeless.ops.nat._

/**
 * @author Tongfei Chen
 */
trait Tensor[D <: DType, S <: HList] {

  type Storage
  type R

  val axes: S

  val storage: Storage
  val support: DTypeSupport[D, R, Storage]

  def apply(indices: Int*): R
  def update(indices: Int*)(newValue: R): Unit

  type Rank = Length[S]#Out
  def rank(implicit e: ToInt[Rank]) = e()

}

