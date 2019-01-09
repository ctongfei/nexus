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
  def add[T[_], R, A](x: T[A], y: T[A])(implicit T: RingTensorK[T, R]): T[A] = T.add(x, y)

  /** Negates a tensor. */
  def neg[T[_], R, A](x: T[A])(implicit T: RingTensorK[T, R]): T[A] = T.neg(x)

  /** Subtracts a tensor from another of the same shape. */
  def sub[T[_], R, A](x: T[A], y: T[A])(implicit T: RingTensorK[T, R]): T[A] = T.sub(x, y)

  /** Elementwise-multiplies two real tensors of the same shape. */
  def mul[T[_], R, A](x: T[A], y: T[A])(implicit T: RingTensorK[T, R]): T[A] = T.mul(x, y)

  /** Elementwise-divides a real tensor by another of the same shape. */
  def div[T[_], R, A](x: T[A], y: T[A])(implicit T: RingTensorK[T, R]): T[A] = T.div(x, y)

  /** Matrix multiplication of two matrices. */
  def matMul[T[_], R, U, V, W](x: T[(U, V)], y: T[(V, W)])(implicit T: RingTensorK[T, R]): T[(U, W)] = T.matMul(x, y)

  def mvMul[T[_], R, U, V](x: T[(U, V)], y: T[V])(implicit T: RingTensorK[T, R]): T[U] = T.mvMul(x, y)

  def transpose[T[_], R, U, V](x: T[(U, V)])(implicit T: RingTensorK[T, R]): T[(V, U)] = T.transpose(x)

  /** Sum of all elements in a real tensor. */
  def sum[T[_], R, A](x: T[A])(implicit T: RingTensorK[T, R]): R = T.sum(x)

  /** Sum along an axis of a real tensor, resulting in a tensor with that axis removed. */
  def sum[T[_], R, U, X, V](x: T[U], dim: X)(implicit T: RingTensorK[T, R], rx: Remove.Aux[U, X, V]): T[V] =
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