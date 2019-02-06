package nexus

import nexus.typelevel._
import shapeless._

/**
 * Contains common math operations on tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object tensormath {

  // ALGEBRAIC OPERATIONS

  /** Adds two tensors of the same shape. */
  def add[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.add(x, y)

  /** Negates a tensor. */
  def neg[T[_], R, I](x: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.neg(x)

  /** Subtracts a tensor from another of the same shape. */
  def sub[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.sub(x, y)

  /** Elementwise-multiplies two real tensors of the same shape. */
  def mul[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.mul(x, y)

  /** Elementwise-divides a real tensor by another of the same shape. */
  def div[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.div(x, y)

  /** Matrix multiplication of two matrices. */
  def matMul[T[_], R, I, J, K](x: T[(I, J)], y: T[(J, K)])(implicit T: RingTensorK[T, R]): T[(I, K)] = T.matMul(x, y)

  def mvMul[T[_], R, I, J](x: T[(I, J)], y: T[J])(implicit T: RingTensorK[T, R]): T[I] = T.mvMul(x, y)

  def transpose[T[_], R, I, J](x: T[(I, J)])(implicit T: RingTensorK[T, R]): T[(J, I)] = T.transpose(x)

  /** Sum of all elements in a real tensor. */
  def sum[T[_], R, I](x: T[I])(implicit T: RingTensorK[T, R]): R = T.sum(x)

  /** Sum along an axis of a real tensor, resulting in a tensor with that axis removed. */
  def sum[T[_], R, U, I, V](x: T[U], dim: I)(implicit T: RingTensorK[T, R], rx: Remove.Aux[U, I, V]): T[V] =
    ???

  def sum[T[_], R, U, N <: Nat, V](x: T[U], dim: N)(implicit T: RingTensorK[T, R], rx: RemoveAt.Aux[U, N, V]): T[V] =
    ???

  def concat = ???
  def chunk = ???
  def gather = ???



  def arithmeticRange = ???
  def linearlySpaced = ???
  def logarithmicallySpaced = ???


}