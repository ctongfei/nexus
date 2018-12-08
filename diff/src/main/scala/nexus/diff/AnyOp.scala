package nexus.diff

/**
 * Basic trait for all operators, regardless of its arity.
 * @tparam Y Output type
 * @author Tongfei Chen
 */
trait AnyOp[Y] {

  /** The arity of this operator. */
  def arity: Int

  /** Name of this operation. */
  def name: String

  /** Is this operator differentiable to at least one of its arguments? */
  def differentiable: Boolean = true

  /** Type tag of the output type. */
  def tag: Tag[Y]

  override def toString = name
}
