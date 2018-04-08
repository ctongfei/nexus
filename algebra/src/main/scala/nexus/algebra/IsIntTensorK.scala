package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsIntTensorK[T[_], Z] extends IsTensorK[T, Z] { self =>

  val Z: IsInt[Z]
  def elementType = Z

  def zeroBy[A](x: T[A]): T[A]

  def add[A](x: T[A], y: T[A]): T[A] = ???

  def ground[A]: IsTensor[T[A], Z] = ???
}
