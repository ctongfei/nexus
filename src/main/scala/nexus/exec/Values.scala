package nexus.exec

import nexus._
import shapeless.HList

import scala.collection._

/**
 * A table for storing values/gradients.
 * It is essentially a map from symbolic expressions to their actual values.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Values[T[_, _ <: HList], D] private(val map: mutable.HashMap[GenExpr, Any])(implicit val env: Env[T, D]) {

  def contains(x: GenExpr): Boolean = map.contains(x)

  def apply(x: GenExpr): Any = map(x)

  def update(x: GenExpr, value: Any): Unit = map.update(x, value)

  def increment(e: GenExpr, v: Any) = {
    import env._
    if (contains(e))
      this(e) = addU(untype(this(e).asInstanceOf[T[D, _]]), untype(v.asInstanceOf[T[D, _]]))
    else
      this(e) = v
  }

  override def toString = map.toString()

}

object Values {

  def apply[T[_, _ <: HList], D](vs: Assignment[_]*)(implicit env: Env[T, D]) =
    new Values(mutable.HashMap(vs.map(a => a.expr -> a.value): _*))

}