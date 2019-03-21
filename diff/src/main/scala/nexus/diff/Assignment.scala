package nexus.diff

import shapeless._

/**
 * Represents an assignment to a symbolic expression, which takes the form `D[X] := X`, where `D` is a differentiable box.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Assignment[D[_]] extends BoxValuePair[D, Id] {

  override def toString = s"$expr := $value"

}

object Assignment {

  /** Creates an assignment for an symbolic expression. */
  def apply[D[_], X](x: D[X], v: X): Assignment[D] = new Assignment[D] {
    type Data = X
    val expr = x
    val value = v
  }

  // Use of dependent types in the return type
  def unapply[D[_]](a: Assignment[D]): Option[(D[a.Data], a.Data)] =
    Some((a.expr, a.value))

}
