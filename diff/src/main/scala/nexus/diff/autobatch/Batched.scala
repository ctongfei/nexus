package nexus.diff.autobatch

import nexus._
import nexus.syntax._
import scala.collection._

/**
 * @author Tongfei Chen
 */
trait Batched[+X] {
  def batchSize: Int
  def apply(idx: Int): X
  def asSeq: immutable.IndexedSeq[X]
}

trait BatchedTensor[T[_], E, U] extends Batched[T[U]] {
  type V
  implicit def T: IsTensorK[T, E]
  def underlying: T[V]

  def batchSize = underlying.shape.head

  override def apply(i: Int) = ???

  override def asSeq = ???
}
