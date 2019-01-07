package nexus.torch

import nexus.torch.jni.torchJNI._
import nexus.util._

import scala.collection._

/**
 * Wraps around a Torch Storage object.
 * @author Tongfei Chen
 */ // TODO: opaque type
class Storage[@specialized E] private[torch](
  val ptr: Long,
  val tag: Storage.TypeTag[E]
) extends NativeObject { self =>

  import tag._

  lazy val data = new NativeArray[E](
    ptr = dataPtr(ptr),
    len = len(ptr),
    tag = tag.nativeTypeTag
  )

  def apply(i: Int) = get(ptr, i)
  def update(i: Int, x: E): Unit = set(ptr, i, x)
  def length = len(ptr)

  /** Copies this array to JVM. */
  def toArray: Array[E] = nativeTypeTag.nativeToJvm(data.ptr, length)

  /** A `scala.collection`-compatible view to this native storage. */
  def view: mutable.IndexedSeqView[E, Storage[E]] = new Storage.View(this)

  def free(): Unit = freeStorage(ptr)

  override def hashCode() = ptr.toInt
  override def equals(o: Any) = o match {
    case o: Storage[E] => this.ptr == o.ptr
    case _ => false
  }

  @specialized override def toString =
    s"$tag[@0x${ptr.toHexString}, length = $length]: " + StringUtils.arraySummary(view)
}

object Storage {

  class View[E](storage: Storage[E]) extends mutable.IndexedSeqView[E, Storage[E]] {
    def length = storage.length
    def apply(idx: Int) = storage.apply(idx)
    def update(idx: Int, elem: E): Unit = storage.update(idx, elem)
    protected def underlying = storage
  }

  def fromJvm[@specialized E](array: Array[E])(implicit E: TypeTag[E]): Storage[E] = {
    val storage = ofSize(array.length)
    var i = 0
    while (i < array.length) {
      storage(i) = array(i)
      i += 1
    }
    storage
  }

  def ofSize[@specialized E](length: Int)(implicit E: TypeTag[E]): Storage[E] = {
    val storage = new Storage[E](ptr = E.newStorage(length), tag = E)
    storage
  }

  trait TypeTag[@specialized E] {
    def nativeTypeTag: NativeArray.TypeTag[E]
    def classTag = nativeTypeTag.classTag
    def newStorage(length: Int): Long
    def freeStorage(ptr: Long): Unit
    def len(ptr: Long): Int
    def get(ptr: Long, i: Int): E
    def set(ptr: Long, i: Int, x: E): Unit
    def dataPtr(ptr: Long): Long
    override def toString = s"${nativeTypeTag.classTag}Storage"
  }

  object TypeTag {

    implicit object Float extends TypeTag[Float] {
      val nativeTypeTag = NativeArray.TypeTag.Float
      def newStorage(length: Int) = THFloatStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THFloatStorage_free(ptr)
      def get(ptr: Long, i: Int) = THFloatStorage_get(ptr, i)
      def set(ptr: Long, i: Int, x: Float): Unit = THFloatStorage_set(ptr, i, x)
      def len(ptr: Long) = THFloatStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THFloatStorage_data(ptr)
    }

    implicit object Double extends TypeTag[Double] {
      val nativeTypeTag = NativeArray.TypeTag.Double
      def newStorage(length: Int) = THDoubleStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THDoubleStorage_free(ptr)
      def get(ptr: Long, i: Int) = THDoubleStorage_get(ptr, i)
      def set(ptr: Long, i: Int, x: Double): Unit = THDoubleStorage_set(ptr, i, x)
      def len(ptr: Long) = THDoubleStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THDoubleStorage_data(ptr)
    }

    implicit object Long extends TypeTag[Long] {
      val nativeTypeTag = NativeArray.TypeTag.Long
      def newStorage(length: Int) = THLongStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THLongStorage_free(ptr)
      def get(ptr: Long, i: Int) = THLongStorage_get(ptr, i)
      def set(ptr: Long, i: Int, x: Long): Unit = THLongStorage_set(ptr, i, x)
      def len(ptr: Long) = THLongStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THLongStorage_data(ptr)
    }

    implicit object Int extends TypeTag[Int] {
      val nativeTypeTag = NativeArray.TypeTag.Int
      def newStorage(length: Int) = THIntStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THIntStorage_free(ptr)
      def get(ptr: Long, i: Int) = THIntStorage_get(ptr, i)
      def set(ptr: Long, i: Int, x: Int): Unit = THIntStorage_set(ptr, i, x)
      def len(ptr: Long) = THIntStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THIntStorage_data(ptr)
    }

    implicit object Short extends TypeTag[Short] {
      val nativeTypeTag = NativeArray.TypeTag.Short
      def newStorage(length: Int) = THShortStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THShortStorage_free(ptr)
      def get(ptr: Long, i: Int) = THShortStorage_get(ptr, i)
      def set(ptr: Long, i: Int, x: Short): Unit = THShortStorage_set(ptr, i, x)
      def len(ptr: Long) = THShortStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THShortStorage_data(ptr)
    }

    implicit object Byte extends TypeTag[Byte] {
      val nativeTypeTag = NativeArray.TypeTag.Byte
      def newStorage(length: Int) = THCharStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THCharStorage_free(ptr)
      def get(ptr: Long, i: Int) = THCharStorage_get(ptr, i)
      def set(ptr: Long, i: Int, x: Byte): Unit = THCharStorage_set(ptr, i, x)
      def len(ptr: Long) = THCharStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THCharStorage_data(ptr)
    }
    
    implicit object Boolean extends TypeTag[Boolean] {
      val nativeTypeTag = NativeArray.TypeTag.Boolean
      def newStorage(length: Int) = THByteStorage_newWithSize(length)
      def freeStorage(ptr: Long): Unit = THByteStorage_free(ptr)
      def get(ptr: Long, i: Int) = THByteStorage_get(ptr, i) != 0
      def set(ptr: Long, i: Int, x: Boolean): Unit = THByteStorage_set(ptr, i, if (x) 1 else 0)
      def len(ptr: Long) = THByteStorage_size(ptr).toInt
      def dataPtr(ptr: Long) = THByteStorage_data(ptr)
    }
  }

}
