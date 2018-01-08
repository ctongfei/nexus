package nexus

import cats._
import nexus.algebra._
import nexus.exception._

import scala.collection._

/**
 * A table for storing values/gradients.
 * It is essentially a map from symbolic expressions to their actual values.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ExprValueMap extends ExprMap[Id] with Iterable[Assignment] {

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

  override def iterator: Iterator[Assignment] = map.iterator.map {
    case (e: Expr[eX], v) =>
      new Assignment {
        type Data = eX
        val expr = e
        val value = v.asInstanceOf[eX]
      }
  }

}

object ExprValueMap {

  def apply(as: Assignment*) = {
    val m = new ExprValueMap
    for (a <- as)
      m(a.expr) = a.value
    m
  }

}
