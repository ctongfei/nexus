package nexus

import nexus.typelevel._

/**
 * Tensor spaces whose elements form rings.
 * This unifies [[IsIntTensorK]] and [[IsRealTensorK]].
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait RingTensorK[T[_], R] extends IsTensorK[T, R] {


  def zeroBy[I](x: T[I]): T[I]


  def add[I](x: T[I], y: T[I]): T[I]
  def addInplace[I](x1: T[I], x2: T[I]): T[I]
  def addScalar[I](x: T[I], u: R): T[I]
  def neg[I](x: T[I]): T[I]
  def sub[I](x: T[I], y: T[I]): T[I]

  def subScalar[I](x: T[I], u: R): T[I]
  def mul[I](x: T[I], y: T[I]): T[I]
  def div[I](x: T[I], y: T[I]): T[I]
  def scale[I](x: T[I], u: R): T[I]

  def sqr[I](x: T[I]): T[I]


  def sum[I](x: T[I]): R
  def product[I](x: T[I]): R

  def dot[I](x: T[I], y: T[I]): R
  def matMul[I, J, K](x: T[(I, J)], y: T[(J, K)]): T[(I, K)]
  def mvMul[I, J](x: T[(I, J)], y: T[J]): T[I]
  def vvMul[I, J](x: T[I], y: T[J]): T[(I, J)]
  def contract[U, V, W](x: T[U], y: T[V])(implicit sd: SymDiff.Aux[U, V, W]): T[W]


}
