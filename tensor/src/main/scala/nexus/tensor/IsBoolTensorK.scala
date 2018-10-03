package nexus.tensor

/**
 * @author Tongfei Chen
 */
trait IsBoolTensorK[T[_], B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]
  val B: IsBool[B]
  def elementType = B

  def eNot[A](x: T[A]): T[A]
  def eAnd[A](x1: T[A], x2: T[A]): T[A]
  def eOr[A](x1: T[A], x2: T[A]): T[A]
  def eXor[A](x1: T[A], x2: T[A]): T[A]

}
