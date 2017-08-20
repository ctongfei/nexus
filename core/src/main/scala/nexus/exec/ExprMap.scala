package nexus.exec

import nexus._
import shapeless._

import scala.collection._

/**
 * @author Tongfei Chen
 */
class ExprMap[V[_]] extends Iterable[Item[V]] {

  protected val map = mutable.HashMap[Expr[_], V[_]]()

  def contains(x: Expr[_]) = map contains x

  def apply[X](x: Expr[X]): V[X] = map(x).asInstanceOf[V[X]]

  def update[X](x: Expr[X], v: V[X]) = map.update(x, v)

  def iterator: Iterator[Item[V]] = map.iterator.map { case (e: Expr[eX], v) => new Item[V] {
    type Data = eX
    val expr = e
    val value = v.asInstanceOf[V[eX]]
  }}

}
