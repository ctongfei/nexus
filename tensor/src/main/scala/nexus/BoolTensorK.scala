package nexus

/**
 * @author Tongfei Chen
 */
trait BoolTensorK[T[_], B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]

  val B: IsBool[B]
  def elementType = B

  def falseBy[A](x: T[A]): T[A]

  def trueBy[A](x: T[A]): T[A]

  def not[A](x: T[A]): T[A]

  def and[A](x: T[A]): T[A]

  def or[A](x: T[A]): T[A]

  override def ground[A] = ???

}
