package nexus.diff

import shapeless._

/**
 * Represents an assignment to a symbolic expression, which takes the form `F[X] := X`, where `F` is a computation box.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Assignment[F[_]] extends BoxValuePair[F, Id] {

  override def toString = s"$expr := $value"

}

object Assignment {

  /** Creates an assignment for an symbolic expression. */
  def apply[F[_], X](x: F[X], v: X): Assignment[F] = new Assignment[F] {
    type Data = X
    val expr = x
    val value = v
  }

  // Use of dependent types in the return type
  def unapply[F[_]](a: Assignment[F]): Option[(F[a.Data], a.Data)] =
    Some((a.expr, a.value))

}
