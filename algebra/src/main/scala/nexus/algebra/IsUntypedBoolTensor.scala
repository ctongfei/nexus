package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsUntypedBoolTensor[T, @specialized(Boolean) B] extends IsUntypedTensor[T, B] {

  val B: IsBool[B]

  def falseBy(x: T): T

  def trueBy(x: T): T

  def eAnd(x: T, y: T): T

  def eOr(x: T, y: T): T

  def eNot(x: T): T

  def eXor(x: T, xy: T): T

}
