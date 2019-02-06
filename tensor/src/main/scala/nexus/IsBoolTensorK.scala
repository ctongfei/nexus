package nexus

/**
 * Encapsulates operations on Boolean tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsBoolTensorK[T[_], @specialized(Boolean) B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]
  val B: IsBool[B]
  def elementType = B

  def not[I](x: T[I]): T[I]
  def and[I](x1: T[I], x2: T[I]): T[I]
  def or[I](x1: T[I], x2: T[I]): T[I]
  def xor[I](x1: T[I], x2: T[I]): T[I]

}
