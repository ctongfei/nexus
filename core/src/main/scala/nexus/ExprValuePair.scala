package nexus

/**
 * Object holding some information associated with a symbolic expression.
 * @tparam V Higher-kinded type that defines the type of information stored for a specific type of expression
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait ExprValuePair[V[_]] extends Product2[Expr[_], V[_]] {

  /** Existential type for the data held in this expression. */
  type Data

  /** The symbolic expression of this object. */
  val expr: Expr[Data]

  /** The stored information assigned to the specific expression. */
  val value: V[Data]

  def _1 = expr
  def _2 = value

  // Conforms to Scala idioms

  final def canEqual(that: Any) = false
  override def toString = s"$expr : $value"

}
