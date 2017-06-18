package nexus.cpu

import nexus._
import nexus.shape._
import shapeless._

abstract class UntypedTensor[D] extends UntypedTensorLike[D, UntypedTensor[D]] { self =>
  val handle: Array[D]
  val stride: Array[Int]
  val offset: Int
  val shape: Array[Int]


  def typeWith[A <: HList](_axes: A): Tensor[D, A] = new Tensor[D, A] {
    val handle = self.handle
    val stride = self.stride
    val offset = self.offset
    val shape = self.shape
    val axes = _axes
  }

}
