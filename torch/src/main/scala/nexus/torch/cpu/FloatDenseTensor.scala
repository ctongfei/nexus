package nexus.torch.cpu

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

  def shape = handle.shape.map(_.toInt)

  def apply(indices: Int*) = indices.length match {
    case 0 => ???
    case 1 => TH.THFloatTensor_get1d(handle, indices(0))
    case 2 => TH.THFloatTensor_get2d(handle, indices(0), indices(1))
    case 3 => TH.THFloatTensor_get3d(handle, indices(0), indices(1), indices(2))
    case 4 => TH.THFloatTensor_get4d(handle, indices(0), indices(1), indices(2), indices(3))
    case _ => ???
  }

  def stringPrefix = "TorchFloatTensor"

  def stringBody = ???


}
