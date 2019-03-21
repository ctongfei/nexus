package nexus.diff.collection

import cats.~>
import nexus.diff.BoxValuePair

import scala.collection.Iterable

/**
 * A map whose keys are symbolic expressions.
 *
 * @tparam V Higher-kinded type that specifies what is stored in this map.
 *           A key of type `D[X]` is associated with a value of type `V[X]`.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait BoxMap[D[_], V[_]] extends (D ~> V) with Iterable[BoxValuePair[D, V]] {

  def contains[X](x: D[X]): Boolean

  def apply[X](x: D[X]): V[X]

  def getOrElse[X](x: D[X], default: => V[X]): V[X]

  def update[X](e: D[X], v: V[X]): Unit

  def keys: Iterable[D[_]]

  def values: Iterable[V[_]]

}
