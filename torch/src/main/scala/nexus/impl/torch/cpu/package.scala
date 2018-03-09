package nexus.impl.torch

import jtorch.cpu._
import nexus.algebra._

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val implicitFloat32Tensor = Float32Tensor


  type F32_* = SWIGTYPE_p_float
  type F64_* = SWIGTYPE_p_double
  type Int_* = SWIGTYPE_p_int
  type I32_* = SWIGTYPE_p_long
  type I64_* = SWIGTYPE_p_long_long

  def nativeI32ArrayToJvm(a: I32_*, n: Int): Array[Int] =
    Array.tabulate(n)(i => TH.longArray_getitem(a, i))

  def nativeI64ArrayToJvm(a: I64_*, n: Int): Array[Long] = {
    Array.tabulate(n)(i => TH.longLongArray_getitem(a, i))
  }


  def jvmFloatArrayToNative(a: Array[Float]): F32_* = {
    val na = TH.new_floatArray(a.length)
    var i = 0
    while (i < a.length) {
      TH.floatArray_setitem(na, i, a(i))
      i += 1
    }
    na
  }

  def jvmIntArrayToNative(a: Array[Int]): I32_* = {
    val na = TH.new_longArray(a.length)
    var i = 0
    while (i < a.length) {
      TH.longArray_setitem(na, i, a(i))
      i += 1
    }
    na
  }

  def jvmLongArrayToNative(a: Array[Long]): I64_* = {
    val na = TH.new_longLongArray(a.length)
    var i = 0
    while (i < a.length) {
      TH.longLongArray_setitem(na, i, a(i))
      i += 1
    }
    na
  }

}
