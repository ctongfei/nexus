package nexus.torch

import nexus.torch.jni.torchJNI._

import scala.collection._
import scala.reflect._

/**
 * Wraps around a native array in C.
 * @author Tongfei Chen
 */ // TODO: opaque type
class NativeArray[@specialized E] private[torch](
  val ptr: Long,
  val len: Int,
  val tag: NativeArray.TypeTag[E]
) extends NativeObject { self =>

  import tag._

  def apply(i: Int): E = get(ptr, i)
  def update(i: Int, x: E): Unit = set(ptr, i, x)
  def length = len

  def toArray: Array[E] = nativeToJvm(ptr, len)

  def view: mutable.IndexedSeqView[E, NativeArray[E]] =
    new mutable.IndexedSeqView[E, NativeArray[E]] {
      def update(idx: Int, elem: E): Unit = self(idx) = elem
      def length = self.length
      def apply(idx: Int) = self(idx)
      protected def underlying = self
    }

  def free(): Unit = freeNativeArray(ptr)

  override def hashCode() = ptr.toInt
  override def equals(o: Any) = o match {
    case o: NativeArray[E] => this.ptr == o.ptr
    case _ => false
  }
  override def toString =
    s"$tag[@0x${ptr.toHexString}, length = $len]: " +
    (0 until len).map(apply).mkString("[", ", ", "]")
}


object NativeArray {

  /** Creates a native array by copying data from the JVM. */
  def fromJvm[@specialized E](array: Array[E])(implicit E: TypeTag[E]): NativeArray[E] =
    new NativeArray[E](
      ptr = E.jvmToNative(array),
      len = array.length,
      tag = E
    )

  /**
   * Handler of native C arrays.
   * @tparam E Type of elements in the array
   */
  trait TypeTag[@specialized E] {
    val classTag: ClassTag[E]
    def newNativeArray(length: Int): Long
    def freeNativeArray(ptr: Long): Unit
    def get(ptr: Long, i: Int): E
    def set(ptr: Long, i: Int, x: E): Unit
    def jvmToNative(array: Array[E]): Long = {
      val ptr = newNativeArray(array.length)
      var i = 0
      while (i < array.length) {
        set(ptr, i, array(i))
        i += 1
      }
      ptr
    }
    def nativeToJvm(ptr: Long, length: Int): Array[E] = {
      val array = classTag.newArray(length)
      var i = 0
      while (i < length) {
        array(i) = get(ptr, i)
        i += 1
      }
      array
    }
    override def toString = s"Native${classTag}Array"
  }

  object TypeTag {

    implicit object Float extends TypeTag[Float] {
      val classTag = ClassTag.Float
      def newNativeArray(length: Int) = new_CFloatArray(length)
      def freeNativeArray(ptr: Long): Unit = delete_CFloatArray(ptr)
      def get(ptr: Long, i: Int) = CFloatArray_getitem(ptr, null, i)
      def set(ptr: Long, i: Int, x: Float): Unit = CFloatArray_setitem(ptr, null, i, x)
    }

    implicit object Double extends TypeTag[Double] {
      val classTag = ClassTag.Double
      def newNativeArray(length: Int) = new_CDoubleArray(length)
      def freeNativeArray(ptr: Long): Unit = delete_CDoubleArray(ptr)
      def get(ptr: Long, i: Int) = CDoubleArray_getitem(ptr, null, i)
      def set(ptr: Long, i: Int, x: Double): Unit = CDoubleArray_setitem(ptr, null, i, x)
    }

    implicit object Long extends TypeTag[Long] {
      val classTag = ClassTag.Long
      def newNativeArray(length: Int) = new_CInt64Array(length)
      def freeNativeArray(ptr: Long): Unit = delete_CInt64Array(ptr)
      def get(ptr: Long, i: Int) = CInt64Array_getitem(ptr, null, i)
      def set(ptr: Long, i: Int, x: Long): Unit = CInt64Array_setitem(ptr, null, i, x)
    }

    implicit object Int extends TypeTag[Int] {
      val classTag = ClassTag.Int
      def newNativeArray(length: Int) = new_CInt32Array(length)
      def freeNativeArray(ptr: Long): Unit = delete_CInt32Array(ptr)
      def get(ptr: Long, i: Int) = CInt32Array_getitem(ptr, null, i)
      def set(ptr: Long, i: Int, x: Int): Unit = CInt32Array_setitem(ptr, null, i, x)
    }

    implicit object Short extends TypeTag[Short] {
      val classTag = ClassTag.Short
      def newNativeArray(length: Int) = new_CInt16Array(length)
      def freeNativeArray(ptr: Long): Unit = delete_CInt16Array(ptr)
      def get(ptr: Long, i: Int) = CInt16Array_getitem(ptr, null, i)
      def set(ptr: Long, i: Int, x: Short): Unit = CInt16Array_setitem(ptr, null, i, x)
    }
    
    implicit object Byte extends TypeTag[Byte] {
      val classTag = ClassTag.Byte
      def newNativeArray(length: Int) = new_CInt8Array(length)
      def freeNativeArray(ptr: Long): Unit = delete_CInt8Array(ptr)
      def get(ptr: Long, i: Int) = CInt8Array_getitem(ptr, null, i)
      def set(ptr: Long, i: Int, x: Byte): Unit = CInt8Array_setitem(ptr, null, i, x)
    }

    implicit object Boolean extends TypeTag[Boolean] {
      val classTag = ClassTag.Boolean
      def newNativeArray(length: Int) = new_CUInt8Array(length)
      def freeNativeArray(ptr: Long): Unit = delete_CUInt8Array(ptr)
      def get(ptr: Long, i: Int) = CUInt8Array_getitem(ptr, null, i) != 0
      def set(ptr: Long, i: Int, x: Boolean): Unit = CUInt8Array_setitem(ptr, null, i, if (x) 1 else 0)
    }
    
  }

}
