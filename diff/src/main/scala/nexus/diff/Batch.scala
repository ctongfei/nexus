package nexus.diff

import nexus._
import shapeless._
import scala.collection._

/**
 * @author Tongfei Chen
 */
trait Batch[+X] extends IndexedSeq[X] {
  def batchSize: Int
  def apply(i: Int): X
  final def length = batchSize
}

object Batch {
  def apply[X](xs: X*) = fromSeq(xs)

  def fromSeq[X](xs: Seq[X]): Batch[X] = new BatchObject[X](xs.toIndexedSeq)

  def fromTensors[T[_], E, U](xs: Seq[T[U]])(implicit T: IsTensorK[T, E]): Batch[T[U]] = {
    val v = ??? // T.stack(xs, batch)
    ???
  }
}

class BatchObject[X](xs: IndexedSeq[X]) extends Batch[X] with IndexedSeq[X] {
  def batchSize = xs.length
  def apply(i: Int) = xs(i)
  def asSeq = this
}

trait BatchTensor[T[_], E, U] extends Batch[T[U]] {
  type Uh <: HList
  def underlying: T[BatchDim :: Uh]

  implicit def T: IsTensorK[T, E]

  def batchSize = T.sizeOfDim(underlying, 0)

  override def apply(i: Int) = {
    val v: T[Uh] = T.sliceAlong(underlying, BatchDim, i)
    v.asInstanceOf[T[U]]  // this cast is safe
  }
}
