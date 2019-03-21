package nexus.diff.collection

import nexus._
import nexus.diff._
import nexus.diff.exception._
import nexus.diff.syntax._

import scala.collection._

/**
 * A table for storing values/gradients.
 * It is essentially a map from symbolic expressions to their actual values.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class WengertList[D[_]: DifferentiableAlgebra]
  (val rawMap: ReversibleLinkedHashMap[D[_], Id[_]] = ReversibleLinkedHashMap[D[_], Id[_]]())
  extends BoxMap[D, Id] with Iterable[Assignment[D]] {


  /** Tests if this expression is stored in this map. */
  def contains[X](x: D[X]) = rawMap contains x

  /** Gets the value associated with the specified expression. */
  def apply[X](e: D[X]): X = rawMap(e).asInstanceOf[X]

  def getOrElse[X](e: D[X], default: => X): X = rawMap.getOrElse(e, default).asInstanceOf[X]

  def update[X](e: D[X], v: X): Unit = rawMap.update(e, v)

  def keys: Iterable[D[_]] = rawMap.keys

  def values: Iterable[_] = rawMap.values
  
  def increment[X](e: D[X], v: X): Unit = e.tag.ev match {
    case gX: Grad[X] => // if differentiable
      if (contains(e)) {
        if (gX.mutable)
          gX.addInplace(this(e), v)
        else this(e) = gX.add(this(e), v)
      }
      else update(e, v)
    case _ => throw new ExpressionNotDifferentiableException(e)
  }

  override def iterator: Iterator[Assignment[D]] = rawMap.iterator.map(asAssignment)

  def reverseIterator: Iterator[Assignment[D]] = rawMap.reverseIterator.map(asAssignment)

  def reverse: Iterable[Assignment[D]] = new AbstractIterable[Assignment[D]] {
    def iterator = reverseIterator
  }

  private def asAssignment(p: (D[_], Id[_])): Assignment[D] = p match {
    case (e: D[t], v) => new Assignment[D] {
      type Data = t
      val expr = e
      val value = v.asInstanceOf[t]
    }
  }

}

object WengertList {

  def apply[D[_]: DifferentiableAlgebra](as: Assignment[D]*) = {
    val m = new WengertList[D]
    for (a <- as)
      m(a.expr) = a.value
    m
  }

}
