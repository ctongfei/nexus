package nexus.diff

import scala.language.higherKinds

/**
 * Object holding some information associated with a symbolic expression.
 * @tparam F Box type
 * @tparam G Higher-kinded type that defines the type of information stored for a specific type of expression
 * @note Isomorphic to `(F[A], G[A]) forSome { type A }`
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait ExprValuePair[F[_], G[_]] extends Product2[F[_], G[_]] {

  /** Existential type for the data held in this expression. */
  type Data

  /** The symbolic expression of this object. */
  val expr: F[Data]

  /** The stored information assigned to the specific expression. */
  val value: G[Data]

  // Conforms to Scala idioms
  def _1 = expr
  def _2 = value

  final def canEqual(that: Any) = false
  override def toString = s"$expr : $value"

}
