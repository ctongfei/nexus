package nexus.tensor

import nexus.tensor.typelevel._

/**
 * Tensor spaces whose elements form rings.
 * This unifies [[IsIntTensorK]] and [[IsRealTensorK]].
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait RingTensorK[T[_], R] extends IsTensorK[T, R] {


  def zeroBy[A](x: T[A]): T[A]


  def add[A](x: T[A], y: T[A]): T[A]
  def addInplace[A](x1: T[A], x2: T[A]): T[A]
  def addScalar[A](x: T[A], u: R): T[A]
  def neg[A](x: T[A]): T[A]
  def sub[A](x: T[A], y: T[A]): T[A]

  def subScalar[A](x: T[A], u: R): T[A]
  def mul[A](x: T[A], y: T[A]): T[A]
  def div[A](x: T[A], y: T[A]): T[A]
  def scale[A](x: T[A], u: R): T[A]

  def sqr[A](x: T[A]): T[A]


  def sum[A](x: T[A]): R
  def product[A](x: T[A]): R

  def dot[A](x: T[A], y: T[A]): R
  def matMul[U, V, W](x: T[(U, V)], y: T[(V, W)]): T[(U, W)]
  def mvMul[U, V](x: T[(U, V)], y: T[V]): T[U]
  def vvMul[U, V](x: T[U], y: T[V]): T[(U, V)]
  def contract[U, V, W](x: T[U], y: T[V])(implicit sd: SymDiff.Aux[U, V, W]): T[W]


}
