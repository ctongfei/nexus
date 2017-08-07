package nexus.torch.cpu

import nexus._
import jtorch.th._

/**
 * @author Tongfei Chen
 */
class FloatTensor[A <: $$](val handle: THFloatTensor, val axes: A) extends DenseTensor[Float, A] {

  override def rank = TH.THFloatTensor_nDimension(handle)

  def shape = {
    val n = rank
    Array.tabulate(n)(i => TH.THFloatTensor_size(handle, i))
  }

  def apply(indices: Int*) = ???
  def stringPrefix = "THFloatTensor"
  def stringBody = ???

}
