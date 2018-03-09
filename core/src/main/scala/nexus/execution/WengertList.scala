package nexus.execution

import cats._
import nexus.algebra._
import nexus.exception._
import nexus._

import scala.collection._

/**
 * A table for storing values/gradients.
 * It is essentially a map from symbolic expressions to their actual values.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class WengertList(override val map: ReversibleLinkedHashMap[Expr[_], Id[_]] = ReversibleLinkedHashMap[Expr[_], Id[_]]())
  extends ExprMap[Id](map) with Iterable[Assignment] {

  def increment[X](e: Expr[X], v: X) = e.tag match {
    case gX: Grad[X] => // if differentiable
      if (contains(e)) {
        if (gX.mutable)
          gX.addI(this (e), v)
        else this (e) = gX.add(this(e), v)
      }
      else update(e, v)
    case _ => throw new ExpressionNotDifferentiableException(e)
  }

  private def asAssignment(p: (Expr[_], Id[_])): Assignment = p match {
    case (e: Expr[t], v) => new Assignment {
      type Data = t
      val expr = e
      val value = v.asInstanceOf[t]
    }
  }

  override def iterator: Iterator[Assignment] = map.iterator.map(asAssignment)

  def reverseIterator: Iterator[Assignment] = map.reverseIterator.map(asAssignment)

  def reverse: Iterable[Assignment] = new AbstractIterable[Assignment] {
    def iterator = reverseIterator
  }

}

object WengertList {

  def apply(as: Assignment*) = {
    val m = new WengertList
    for (a <- as)
      m(a.expr) = a.value
    m
  }

}
