package nexus.autodiff

import nexus._

import scala.collection._

/**
 * A table for storing values/gradients.
 * @author Tongfei Chen
 */
class ValueStore {

  val map = mutable.HashMap[Expr[Any], Any]()

  def contains[X](x: Expr[X]): Boolean = map.contains(x)

  def apply[X](x: Expr[X]): X = map(x).asInstanceOf[X]

  def update[X](x: Expr[X], value: X) = map += x -> value

}
