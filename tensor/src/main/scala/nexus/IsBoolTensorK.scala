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

  def not[U](x: T[U]): T[U]
  def and[U](x1: T[U], x2: T[U]): T[U]
  def or[U](x1: T[U], x2: T[U]): T[U]
  def xor[U](x1: T[U], x2: T[U]): T[U]

}
