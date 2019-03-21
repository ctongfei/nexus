package nexus.functions

import nexus._

trait MatrixFunctions {

  /** Matrix multiplication of two matrices. */
  def matMul[T[_], R, I <: Dim, J <: Dim, K <: Dim]
  (x: T[(I, J)], y: T[(J, K)])(implicit T: RingTensorK[T, R]): T[(I, K)] = T.matMul(x, y)

  def mvMul[T[_], R, I <: Dim, J <: Dim](x: T[(I, J)], y: T[J])(implicit T: RingTensorK[T, R]): T[I] = T.mvMul(x, y)

  def trace[T[_], R, I <: Dim](x: T[(I, I)])(implicit T: RingTensorK[T, R]): R = ???

  def det[T[_], R, I <: Dim](x: T[(I, I)])(implicit T: IsRealTensorK[T, R]): R = ???

  def transpose[T[_], R, I <: Dim, J <: Dim](x: T[(I, J)])(implicit T: RingTensorK[T, R]): T[(J, I)] = T.transpose(x)


}
