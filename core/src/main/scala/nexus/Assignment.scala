package nexus

/**
 * An assignment to a symbolic expression.
 * @since 0.1.0
 * @author Tongfei Chen
 */
trait Assignment {
  type Data // existential type

  /** The symbolic expression of this assignment. */
  val expr: Expr[Data]

  /** The actual value assigned to the expression. */
  val value: Data
}

object Assignment {

  def apply[X](x: Expr[X], v: X): Assignment = new Assignment {
    type Data = X
    val expr = x
    val value = v
  }

}
