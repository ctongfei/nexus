package nexus

import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait GenOp1 {
  def apply[D <: DType, S <: HList](x: Tensor[D, S]): Tensor[D, S]
  def backward[D <: DType, S <: HList](dy: Tensor[D, S], x: Tensor[D, S], y: Tensor[D, S]): Tensor[D, S]
}

trait GenOp2 {
  def apply[D <: DType, S <: HList](x1: Tensor[D, S], x2: Tensor[D, S]): Tensor[D, S]
  def backward1[D <: DType, S <: HList](dy: Tensor[D, S], x1: Tensor[D, S], y: Tensor[D, S]): Tensor[D, S]
  def backward2[D <: DType, S <: HList](dy: Tensor[D, S], x2: Tensor[D, S], y: Tensor[D, S]): Tensor[D, S]
}
