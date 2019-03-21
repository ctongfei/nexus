package nexus.diff.collection

import nexus._
import nexus.diff.{Assignment, BoxValuePair}

import scala.collection._

/**
 * @author Tongfei Chen
 */
class HashBoxMap[D[_], V[_]](val rawMap: mutable.HashMap[D[_], V[_]] = mutable.HashMap[D[_], V[_]]())
  extends BoxMap[D, V]
{
  def contains[X](x: D[X]) = rawMap contains x
  def apply[X](k: D[X]) = rawMap(k).asInstanceOf[V[X]]
  def getOrElse[X](x: D[X], default: => V[X]) = rawMap.getOrElse(x, default).asInstanceOf[V[X]]
  def update[X](e: D[X], v: V[X]): Unit = rawMap.update(e, v)
  def keys = rawMap.keys
  def values = rawMap.values
  def iterator = rawMap.iterator.map(asBoxValuePair)

  private def asBoxValuePair(p: (D[_], V[_])): BoxValuePair[D, V] = p match {
    case (e: D[t], v) => new BoxValuePair[D, V] {
      type Data = t
      val expr = e
      val value = v.asInstanceOf[V[t]]
    }
  }
}
