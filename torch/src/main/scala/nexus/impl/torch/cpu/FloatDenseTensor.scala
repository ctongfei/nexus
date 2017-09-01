package nexus.impl.torch.cpu

import nexus._
import jtorch._

/**
 * 32-bit floating point dense tensor, implemented in Torch.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class FloatDenseTensor[A <: $$](val handle: THFloatTensor, val axes: A) extends DenseTensor[Float, A] {

  import THFloatTensorSyntax._

  override def rank = handle.rank

  def shape = handle.shape

  def apply(indices: Int*) = indices.length match {
    case 0 => ???
    case 1 => TH.THFloatTensor_get1d(handle, indices(0))
    case 2 => TH.THFloatTensor_get2d(handle, indices(0), indices(1))
    case 3 => TH.THFloatTensor_get3d(handle, indices(0), indices(1), indices(2))
    case 4 => TH.THFloatTensor_get4d(handle, indices(0), indices(1), indices(2), indices(3))
    case _ => ???
  }

  def stringPrefix = "TorchCPUFloatTensor"

  def stringBody = handle.stringRepr

  override def finalize() = {
    TH.THFloatTensor_free(handle)
    super.finalize()
  }

}

object FloatDenseTensor extends DenseTensorFactory[FloatDenseTensor, Float] {

  // Torch does not support 0-dim tensors.
  // Implement it manually.
  class ZeroDim(val value: Float) extends FloatDenseTensor(null, $) {
    override def rank = 0
    override def shape = Array()
    def strides = Array()
  }

  def scalar(x: Float) = new ZeroDim(x)

  def fill[A <: $$](value: => Float, axes: A, shape: Array[Int]) = ???

  def fromFlatArray[A <: $$](array: Array[Float], axes: A, shape: Array[Int]) = {
    val data = jvmFloatArrayToNative(array)
    val s = TH.THFloatStorage_newWithData(data, array.length)
    val h = TH.THFloatTensor_newWithStorage(
      s,
      0,
      TH.THLongStorage_newWithData(jvmLongArrayToNative(shape.map(_.toLong)), shape.length),
      TH.THLongStorage_newWithData(jvmLongArrayToNative(shape.scanRight(1)(_ * _).tail.map(_.toLong)), shape.length)
    )
    new FloatDenseTensor[A](h, axes)
  }
}
