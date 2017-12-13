package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsIntTensor[T[_ <: $$], I] extends IsTensor[T, I] with AxisTyping[T] { self =>

  val I: IsInt[I]

  def zeroBy[A <: $$](x: T[A]): T[A]

  def add[A <: $$](x: T[A], y: T[A]): T[A] = ???

  def ground[A <: $$]: Type[T[A]] = new Type[T[A]] { }
}
