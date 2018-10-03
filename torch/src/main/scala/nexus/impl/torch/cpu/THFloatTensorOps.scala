package nexus.impl.torch.cpu

import jtorch.cpu._
import nexus._
import nexus.tensor.typelevel.util._
import shapeless._

/**
 * @author Tongfei Chen
 */
object THFloatTensorSyntax {

  implicit class THFloatTensorOps(val a: THFloatTensor) extends AnyVal {

    def get(i: Int) = TH.floatArray_getitem(a.getStorage.getData, i)
    def set(i: Int, v: Float) = TH.floatArray_setitem(a.getStorage.getData, i, v)

    def shape = Util.nativeI64ArrayToJvm(a.getSize, a.getNDimension).map(_.toInt)
    def shape_=(s: Array[Int]) = a.setSize(Util.jvmLongArrayToNative(s.map(_.toLong)))

    def strides = Util.nativeI64ArrayToJvm(a.getStride, a.getNDimension).map(_.toInt)
    def strides_=(s: Array[Int]) = a.setStride(Util.jvmLongArrayToNative(s.map(_.toLong)))

    def offset = a.getStorageOffset
    def offset_=(o: Int) = a.setStorageOffset(o)

    def rank = a.getNDimension
    def rank_=(n: Int) = a.setNDimension(n)

    def copy: THFloatTensor = TH.THFloatTensor_newClone(a)


    def slice(n: Int, i: Int): THFloatTensor = {
      val c = new THFloatTensor
      c.rank = a.rank - 1
      c.shape = ShapeUtils.removeAt(a.shape, n)
      c.offset = a.offset + a.strides(n) * i
      c.strides = ShapeUtils.removeAt(a.strides, n)
      c.setStorage(a.getStorage)
      c
    }

    def stringRepr: String = rank match {
      case 1 =>
        (0 until shape(0)).map { i => get(offset + i * strides(0)) }.mkString("[", ", \t", "]")
      case _ =>
        (0 until shape(0)).map { i => slice(0, i).stringRepr }.mkString("[", "\n", "]")
    }
  }

  def log(a: THFloatTensor) = {
    val c = a.copy
    TH.THFloatTensor_log(c, a)
    c
  }


}
