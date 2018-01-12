package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsIntTensorK[T[_], I] extends IsTensorK[T, I] { self =>

  val I: IsInt[I]
  def elementType = I

  def zeroBy[A](x: T[A]): T[A]

  def add[A](x: T[A], y: T[A]): T[A] = ???

  def ground[A]: IsTensor[T[A], I] = ???
}
