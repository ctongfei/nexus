package nexus

import shapeless.HList

/**
 * Represents a device. Either CPUs or GPUs.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DeviceLike[Device <: DeviceLike[Device]] { self: Device =>

  def name: String

  type Tensor[D <: DType, A <: HList] <: nexus.TensorLike[Tensor[D, A]]

}
