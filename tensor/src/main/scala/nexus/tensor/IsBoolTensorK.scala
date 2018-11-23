package nexus.tensor

/**
 * @author Tongfei Chen
 */
trait IsBoolTensorK[T[_], @specialized(Boolean) B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]
  val B: IsBool[B]
  def elementType = B

  def eNot[a](x: T[a]): T[a]
  def eAnd[a](x1: T[a], x2: T[a]): T[a]
  def eOr[a](x1: T[a], x2: T[a]): T[a]
  def eXor[a](x1: T[a], x2: T[a]): T[a]

}
