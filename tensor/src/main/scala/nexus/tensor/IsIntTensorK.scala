package nexus.tensor

/**
 * @author Tongfei Chen
 */
trait IsIntTensorK[T[_], Z] extends IsTensorK[T, Z] { self =>

  type ElementTag[z] = IsInt[z]
  val Z: IsInt[Z]
  def elementType = Z

  def zeroBy[A](x: T[A]): T[A]

  def add[A](x: T[A], y: T[A]): T[A] = ???
  def neg[A](x: T[A]): T[A] = ???
  def sub[A](x: T[A], y: T[A]): T[A] = ???
  def eMul[A](x: T[A], y: T[A]): T[A] = ???

  def ground[A]: IsTensor[T[A], Z] = ???
}
