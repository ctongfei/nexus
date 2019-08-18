package nexus.functions

import nexus._
import nexus.typelevel._
import shapeless.Nat

trait TensorFunctions {

  // TENSOR ELEMENT OPERATIONS
  /** Concatenates a sequence of tensors along a specific axis. */
  def concat[T[_], E, U, I <: Dim, N <: Nat]
  (xs: Seq[T[U]], dim: I)(implicit T: IsTensorK[T, E], ix: IndexOf.Aux[U, I, N]): T[U] =
    ???

  def chunk[T[_], E, U, I <: Dim, N <: Nat]
  (x: T[U], dim: I, n: Int)(implicit T: IsTensorK[T, E], ix: IndexOf.Aux[U, I, N]): Seq[T[U]] =
    ???

  /** Adds two tensors of the same shape. */
  def add[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.add(x, y)

  def addScalar[T[_], R, I](x: T[I], y: R)(implicit T: RingTensorK[T, R]): T[I] = T.addScalar(x, y)

  /** Negates a tensor. */
  def neg[T[_], R, I](x: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.neg(x)

  /** Subtracts a tensor from another of the same shape. */
  def sub[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.sub(x, y)

  def subScalar[T[_], R, I](x: T[I], y: R)(implicit T: RingTensorK[T, R]): T[I] = T.subScalar(x, y)

  /** Elementwise-multiplies two real tensors of the same shape. */
  def mul[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.mul(x, y)

  /** Elementwise-reciprocal of a tensor. */
  def inv[T[_], R, I](x: T[I])(implicit T: IsRealTensorK[T, R]): T[I] = T.inv(x)

  /** Elementwise-divides a real tensor by another of the same shape. */
  def div[T[_], R, I](x: T[I], y: T[I])(implicit T: RingTensorK[T, R]): T[I] = T.div(x, y)


  // TENSOR REDUCTION
  /** Sum of all elements in a real tensor. */
  def sum[T[_], R, I](x: T[I])(implicit T: RingTensorK[T, R]): R = T.sum(x)


  // TENSOR AXIS REDUCTION
  /** Sum along an axis of a tensor, resulting in a tensor with that axis removed. */
  def sumAlong[T[_], R, U, I <: Dim, V](x: T[U], dim: I)(implicit T: RingTensorK[T, R], rx: Remove.Aux[U, I, V]): T[V] =
    ???

  def sumAlong[T[_], R, U, N <: Nat, V](x: T[U], dim: N)(implicit T: RingTensorK[T, R], rx: RemoveAt.Aux[U, N, V]): T[V] =
    ???


  def contract[T[_], R, U, V, W](x: T[U], y: T[V])(implicit T: RingTensorK[T, R], sd: SymDiff.Aux[U, V, W]): T[W] =
    T.contract(x, y)


  def arithmeticRange = ???
  def linearlySpaced = ???
  def logarithmicallySpaced = ???


}
