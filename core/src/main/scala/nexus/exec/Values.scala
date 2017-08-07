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
class Values[T[_, _ <: HList], D] private(val map: mutable.HashMap[Expr[_], Any])(implicit val env: Env[T, D]) {

  def contains[X](x: Expr[X]): Boolean = map.contains(x)

  def apply[X](x: Expr[X]): X = map(x).asInstanceOf[X]

  def update[X](x: Expr[X], value: X): Unit = map.update(x, value)

  def increment[X](e: Expr[X], v: X) = {
    import env._
    if (contains(e))
      this.map(e) = addU(untype(this(e).asInstanceOf[T[D, _]]), untype(v.asInstanceOf[T[D, _]]))
    else
      this.map(e) = v
  }

  override def toString = map.toString()

}

object Values {

  def apply[T[_, _ <: HList], D](vs: Assignment*)(implicit env: Env[T, D]) =
    new Values(mutable.HashMap(vs.map(a => a.expr -> a.value): _*))

}
