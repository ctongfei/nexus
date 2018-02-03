package nexus

import cats._
import scala.collection._

/**
 * A map whose keys are symbolic expressions.
 * @tparam V Higher-kinded type that specifies what is stored in this map.
 *           A key of type `Expr[X]` is associated with a value of type `V[X]`.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ExprMap[V[_]](val map: mutable.Map[Expr[_], V[_]]) extends (Expr ~> V) with Iterable[ExprValuePair[V]] {

  /** Tests if this expression is stored in this map. */
  def contains(x: Expr[_]) = map contains x

  /** Gets the value associated with the specified expression. */
  def apply[X](e: Expr[X]): V[X] = map(e).asInstanceOf[V[X]]

  def getOrElse[X](e: Expr[X], default: => V[X]): V[X] = map.getOrElse(e, default).asInstanceOf[V[X]]

  def update[X](e: Expr[X], v: V[X]) = map.update(e, v)

  def iterator: Iterator[ExprValuePair[V]] = map.iterator.map {
    case (e: Expr[eX], v) =>
      new ExprValuePair[V] {
        type Data = eX
        val expr = e
        val value = v.asInstanceOf[V[eX]]
      }
  }

}

object ExprMap {

  def apply[V[_]]() = new ExprMap[V](new mutable.HashMap[Expr[_], V[_]]())

}
