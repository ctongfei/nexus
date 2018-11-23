package nexus.tensor

import nexus.tensor.typelevel._

/**
 * @author Tongfei Chen
 */
package object syntax extends
  Priority1Implicits
{

  /** Adds two real tensors of the same shape. */
  def add[T[_], R, U](x: T[U], y: T[U])(implicit T: IsRealTensorK[T, R]): T[U] =
    T.add(x, y)

  /** Subtracts a real tensor from another of the same shape. */
  def sub[T[_], R, U](x: T[U], y: T[U])(implicit T: IsRealTensorK[T, R]): T[U] =
    T.sub(x, y)

  /** Elementwise-multiplies two real tensors of the same shape. */
  def mul[T[_], R, U](x: T[U], y: T[U])(implicit T: IsRealTensorK[T, R]): T[U] =
    T.eMul(x, y)

  /** Elementwise-divides a real tensor by another of the same shape. */
  def div[T[_], R, U](x: T[U], y: T[U])(implicit T: IsRealTensorK[T, R]): T[U] =
    T.eDiv(x, y)

  /** Matrix multiplication of two real matrices. */
  def matMul[T[_], R, U, V, W](x: T[(U, V)], y: T[(V, W)])(implicit T: IsRealTensorK[T, R]): T[(U, W)] =
    T.matMul(x, y)

  /** Sum of all elements in a real tensor. */
  def sum[T[_], R, U](x: T[U])(implicit T: IsRealTensorK[T, R]): R =
    T.sum(x)

  /** Sum along an axis of a real tensor, resulting in a tensor with that axis removed. */
  def sum[T[_], R, U, X, V](x: T[X], dim: X)(implicit T: IsRealTensorK[T, R], rx: Remove.Aux[U, X, V]): T[V] =
    ???

}
