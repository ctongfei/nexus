package nexus.torch

import jtorch._

/**
 * @author Tongfei Chen
 */
package object cpu {

  System.loadLibrary("jnith")
  System.loadLibrary("TH.1")

  type Float_* = SWIGTYPE_p_float
  type Double_* = SWIGTYPE_p_double
  type Int_* = SWIGTYPE_p_int
  type Long_* = SWIGTYPE_p_long

  def nativeLongArrayToJvm(a: Long_*, n: Int): Array[Long] =
    Array.tabulate(n)(i => TH.longArray_getitem(a, i))


  def jvmFloatArrayToNative(a: Array[Float]): Float_* = {
    val na = TH.new_floatArray(a.length)
    var i = 0
    while (i < a.length) {
      TH.floatArray_setitem(na, i, a(i))
      i += 1
    }
    na
  }

  def jvmLongArrayToNative(a: Array[Long]): Long_* = {
    val na = TH.new_longArray(a.length)
    var i = 0
    while (i < a.length) {
      TH.longArray_setitem(na, i, a(i).toInt) //TODO: something wrong with SWIG's generated code
      i += 1
    }
    na
  }

}
