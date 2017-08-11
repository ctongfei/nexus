package nexus.torch.cpu

import jtorch._
import nexus._
import nexus.util._
import shapeless._

/**
 * @author Tongfei Chen
 */
object THFloatTensorSyntax {

  implicit class THFloatTensorOps(val a: THFloatTensor) extends AnyVal {

    def shape = nativeLongArrayToJvm(a.getSize, a.getNDimension)
    def shape_=(s: Array[Long]) = a.setSize(jvmLongArrayToNative(s))

    def strides = nativeLongArrayToJvm(a.getStride, a.getNDimension)
    def strides_=(s: Array[Long]) = a.setStride(jvmLongArrayToNative(s))

    def offset = a.getStorageOffset // TODO: wrong! how can I dereference SWIGTYPE_p_ptrdiff_t ?

    def rank = a.getNDimension
    def rank_=(n: Int) = a.setNDimension(n)

    def copy: THFloatTensor = {
      val n = TH.THFloatTensor_newWithTensor(a)
      TH.THFloatTensor_copy(n, a)
      n
    }

    def +=(b: THFloatTensor) = TH.THFloatTensor_add(a, b, 1f)
    def +(b: THFloatTensor): THFloatTensor = {
      val c = a.copy
      c += b
      c
    }

    def -=(b: THFloatTensor) = TH.THFloatTensor_sub(a, b, 1f)
    def -(b: THFloatTensor): THFloatTensor = {
      val c = a.copy
      c -= b
      c
    }

    def slice(n: Int, i: Int): THFloatTensor = {
      val c = new THFloatTensor
      c.rank = n - 1
      c.shape = ShapeUtils.removeAt(a.shape, n)
      c
    }

  }

  def log(a: THFloatTensor) = {
    val c = a.copy
    TH.THFloatTensor_log(c, a)
    c
  }


}
