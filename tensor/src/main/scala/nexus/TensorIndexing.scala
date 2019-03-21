package nexus

import nexus.typelevel._
import shapeless._
import shapeless.ops.tuple._

/**
 * @tparam T Type of the indexee tensors
 * @tparam E Type of the indexee elements
 * @tparam TZ Underlying storage type of index tensors (e.g. LongTensor)
 * @tparam Z Underlying type of indices (e.g. Long)
 */
trait TensorIndexing[T[_], E, TZ[_], Z] {

  def get[U, V, N <: Nat](x: T[U], i: V)(implicit xl: Len.Aux[U, N], il: Len.Aux[V, N], l: ToList.Aux[V, Int, List[Int]]): E

  /**
   * Gather values along a specific axis.
   * @param x Indexee, a tensor of shape (..., I, ...) with element type E
   * @param i Indexer, a tensor of shape (..., J, ...) with element type Z
   * @return A tensor, with shape (..., J, ...) and element type E
   */
  def gather[U, V, I <: Dim, J <: Dim](x: T[U], i: TZ[V])(implicit df: Diff1.Aux[U, V, I, J]): T[V]

}
