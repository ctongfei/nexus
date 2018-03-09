package nexus.execution

import scala.collection._
import scala.collection.generic._

/**
 * @author Tongfei Chen
 */
private[nexus] class ReversibleLinkedHashMap[A, B]() extends mutable.LinkedHashMap[A, B] with mutable.Map[A, B] with mutable.MapLike[A, B, ReversibleLinkedHashMap[A, B]] {

  def reverseIterator: Iterator[(A, B)] = new AbstractIterator[(A, B)] {
    private[this] var curr = lastEntry
    def hasNext = curr ne null
    def next() = {
      if (hasNext) {
        val res = (curr.key, curr.value)
        curr = curr.earlier
        res
      }
      else Iterator.empty.next()
    }
  }

  def reverse: Iterable[(A, B)] = new AbstractIterable[(A, B)] {
    def iterator = reverseIterator
  }

  override def empty = new ReversibleLinkedHashMap[A, B]()

  override def newBuilder = ReversibleLinkedHashMap.newBuilder[A, B]

  override def clone() = empty ++= repr

  override def -(key: A) = clone() -= key

}

private[nexus] object ReversibleLinkedHashMap extends MutableMapFactory[ReversibleLinkedHashMap] {
  implicit def cbf[A, B]: CanBuildFrom[Coll, (A, B), ReversibleLinkedHashMap[A, B]] = new MapCanBuildFrom[A, B]
  def empty[A, B] = new ReversibleLinkedHashMap[A, B]
}
