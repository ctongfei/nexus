package nexus

import shapeless._

/**
 * Represents an assignment to a symbolic expression, which takes the form `Expr[X] := X`.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Assignment extends ExprValuePair[Id] {

  override def toString = s"$expr := $value"

}

object Assignment {

  /** Creates an assignment for an symbolic expression. */
  def apply[X](x: Symbolic[X], v: X): Assignment = new Assignment {
    type Data = X
    val expr = x
    val value = v
  }

  // Use of dependent types in the return type
  def unapply(a: Assignment): Option[(Symbolic[a.Data], a.Data)] = Some(a.expr, a.value)

}
