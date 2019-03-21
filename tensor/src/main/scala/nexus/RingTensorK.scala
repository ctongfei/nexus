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


  def zeroBy[U](x: T[U]): T[U]


  def add[U](x: T[U], y: T[U]): T[U]
  def addInplace[U](x1: T[U], x2: T[U]): T[U]
  def addScalar[U](x: T[U], u: R): T[U]
  def neg[U](x: T[U]): T[U]
  def sub[U](x: T[U], y: T[U]): T[U]

  def subScalar[U](x: T[U], u: R): T[U]
  def mul[U](x: T[U], y: T[U]): T[U]
  def div[U](x: T[U], y: T[U]): T[U]
  def scale[U](x: T[U], u: R): T[U]

  def sqr[U](x: T[U]): T[U]


  def sum[U](x: T[U]): R
  def product[U](x: T[U]): R

  def dot[U](x: T[U], y: T[U]): R
  def matMul[U, J, K](x: T[(U, J)], y: T[(J, K)]): T[(U, K)]
  def mvMul[U, J](x: T[(U, J)], y: T[J]): T[U]
  def vvMul[U, J](x: T[U], y: T[J]): T[(U, J)]
  def contract[U, V, W](x: T[U], y: T[V])(implicit sd: SymDiff.Aux[U, V, W]): T[W]


}
