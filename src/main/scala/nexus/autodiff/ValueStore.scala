package nexus.autodiff

import nexus._

import scala.collection._

/**
 * A table for storing values/gradients.
 * @author Tongfei Chen
 */
class ValueStore private(val map: mutable.HashMap[GenExpr, Any]) {

  def contains(x: GenExpr): Boolean = map.contains(x)

  def apply(x: GenExpr): Any = map(x)

  def update(x: GenExpr, value: Any): Unit = map += x -> value

  override def toString = map.toString()

}

object ValueStore {

  def apply(vs: (GenExpr, Any)*) = new ValueStore(mutable.HashMap(vs: _*))

}