package nexus.torch

import nexus.torch.jni.torchJNI._

import scala.collection._
import scala.reflect._

/**
 * @author Tongfei Chen
 */
abstract class Storage[@specialized(Boolean, Byte, Short, Int, Long, Float, Double) E] private[torch](
  val ptr: Long
) extends NativeObject
{ storage =>

  implicit def elementClassTag: ClassTag[E]

  def size: Int
  def apply(i: Int): E
  def update(i: Int, x: E): Unit

  def toArray: Array[E] = Array.tabulate(size)(apply)

  def toSeqView: SeqView[E, Storage[E]] = new SeqView[E, Storage[E]] {
    def iterator: Iterator[E] = new Iterator[E] {
      private[this] var i = 0
      def hasNext = i < size
      def next() = {
        val r = storage.apply(i)
        i += 1
        r
      }
    }
    protected def underlying = storage
    def length = size
    def apply(i: Int) = storage.apply(i)
  }
}

class FloatStorage private[torch](ptr: Long) extends Storage[Float](ptr) {
  implicit def elementClassTag: ClassTag[Float] = ClassTag.Float
  def size = THFloatStorage_size(ptr).toInt
  def apply(i: Int) = THFloatStorage_get(ptr, i)
  def update(i: Int, x: Float): Unit = THFloatStorage_set(ptr, i, x)
}

object FloatStorage extends StorageFactory[Float, FloatStorage] {
  def fromArray(a: Array[Float]) = {
    val s = new FloatStorage(THFloatStorage_newWithSize(a.length))
    var i = 0
    while (i < a.length) {
      s(i) = a(i)
      i += 1
    }
    s
  }
}
