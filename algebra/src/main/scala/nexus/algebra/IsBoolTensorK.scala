package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsBoolTensorK[T[_], B] extends IsTensorK[T, B] { self =>

  val B: IsBool[B]
  def elementType = B

  def falseBy[A](x: T[A]): T[A]

  def trueBy[A](x: T[A]): T[A]

  def eNot[A](x: T[A]): T[A]

  def eAnd[A](x: T[A]): T[A]

  def eOr[A](x: T[A]): T[A]

  override def ground[A] = ???

}
