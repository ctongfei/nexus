package nexus

import shapeless.HList

/**
 * Represents a device. Either CPUs or GPUs.
 *
 * @author Tongfei Chen
 */
trait Device {

  def name: String

  def tensorFromStorage[D <: DType, A <: HList, R, S](storage: S, axes: A, shape: Seq[Int])(implicit support: DTypeSupport[D, R, S]): DenseTensor[D, A]

  def view[D <: DType, A <: HList, R, S](storage: S, axes: A, offset: Int, shape: Seq[Int], stride: Seq[Int])(implicit support: DTypeSupport[D, R, S]): DenseTensor[D, A]

  def add[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A]
  def sub[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A]
  def mul[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A]
  def div[D <: DType, A <: HList](a: Tensor[D, A], b: Tensor[D, A]): Tensor[D, A]

  def neg[D <: DType, A <: HList](a: Tensor[D, A]): Tensor[D, A]

}
