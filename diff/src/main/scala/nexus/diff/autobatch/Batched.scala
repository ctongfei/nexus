package nexus.diff.autobatch

import nexus._

import scala.collection._

/**
 * @author Tongfei Chen
 */
trait Batched[+X] {
  def batchSize: Int = ???
  def apply(idx: Int) = ???
  def asIndexedSeq: immutable.IndexedSeq[X] = ???
}

trait BatchedTensor[T[_], E, U] extends Batched[T[U]] {
  type V
  def T: IsTensorK[T, E]
  def underlying: T[V]
}
