package nexus.tensor

/**
 * Encapsulates operations on Boolean tensors.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait IsBoolTensorK[T[_], @specialized(Boolean) B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]
  val B: IsBool[B]
  def elementType = B

  def not[A](x: T[A]): T[A]
  def and[A](x1: T[A], x2: T[A]): T[A]
  def or[A](x1: T[A], x2: T[A]): T[A]
  def xor[A](x1: T[A], x2: T[A]): T[A]

}
