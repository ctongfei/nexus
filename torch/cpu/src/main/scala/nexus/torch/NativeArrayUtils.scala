package nexus.torch

import nexus.torch.jni._
import nexus.torch.jni.torchJNI._

/**
 * @author Tongfei Chen
 */
object NativeArrayUtils {

  def toNativeArray(a: Array[Long]) = {
    val r = new CInt64Array(a.length)
    var i = 0
    while (i < a.length) {
      r.setitem(i, a(i))
      i += 1
    }
    r
  }

  def toNativeArrayPtr(a: Array[Long]) = {
    val r = toNativeArray(a)
    SWIGTYPE_p_long.getCPtr(r.cast())
  }

}
