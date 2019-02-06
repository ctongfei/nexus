package nexus.diff

import cats._
import scala.collection._

/**
 * A map whose keys are symbolic expressions.
 * @tparam V Higher-kinded type that specifies what is stored in this map.
 *           A key of type `Symbolic[X]` is associated with a value of type `V[X]`.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class BoxMap[K[_], V[_]](val rawMap: mutable.Map[K[_], V[_]]) extends (K ~> V) with Iterable[ExprValuePair[K, V]] {

  /** Tests if this expression is stored in this map. */
  def contains(x: K[_]) = rawMap contains x

  /** Gets the value associated with the specified expression. */
  def apply[X](e: K[X]): V[X] = rawMap(e).asInstanceOf[V[X]]

  def getOrElse[X](e: K[X], default: => V[X]): V[X] = rawMap.getOrElse(e, default).asInstanceOf[V[X]]

  def update[X](e: K[X], v: V[X]): Unit = rawMap.update(e, v)

  def iterator: Iterator[ExprValuePair[K, V]] = rawMap.iterator.map {
    case (e: K[eX], v) =>
      new ExprValuePair[K, V] {
        type Data = eX
        val expr = e
        val value = v.asInstanceOf[V[Data]]
      }
  }

}

object BoxMap {

  def apply[K[_], V[_]]() = new BoxMap[K, V](new mutable.HashMap[K[_], V[_]]())

}
