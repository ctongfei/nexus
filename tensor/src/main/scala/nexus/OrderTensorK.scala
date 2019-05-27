package nexus

import nexus.typelevel.Remove

trait OrderTensorK[T[_], E] extends IsTensorK[T, E] {

  def min[U](x: T[U]): E
  def max[U](x: T[U]): E
  def median[U](x: T[U]): E

  def minAlong[U, I, V](x: T[U], i: I)(implicit rm: Remove.Aux[U, I, V]): T[V]
  def maxAlong[U, I, V](x: T[U], i: I)(implicit rm: Remove.Aux[U, I, V]): T[V]


}
