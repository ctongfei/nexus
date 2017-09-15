package nexus

import shapeless._

/**
 * Object holding some information associated with a symbolic expression.
 * @tparam V Higher-kinded type that defines the type of information stored for a specific type of expression
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait ExprValuePair[V[_]] {

  /** Existential type for the data held in the expression. */
  type Data

  /** The symbolic expression of this object. */
  val expr: Expr[Data]

  /** The stored information assigned to the specific expression. */
  val value: V[Data]

  def _1 = expr
  def _2 = value

}

trait Assignment extends ExprValuePair[Id]

object Assignment {

  def apply[X](x: Expr[X], v: X): Assignment = new Assignment {
    type Data = X
    val expr = x
    val value = v
  }


}
