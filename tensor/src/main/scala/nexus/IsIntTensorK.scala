package nexus

/**
 * @author Tongfei Chen
 */
trait IsIntTensorK[T[_], @specialized(Long, Int, Short, Byte) Z] extends RingTensorK[T, Z] { self =>

  type ElementTag[z] = IsInt[z]

  override type TensorTag[ta] = IsTensor[ta, Z] // TODO

  val Z: IsInt[Z]
  def elementType = Z

  def zeroBy[I](x: T[I]): T[I]

  def ground[I]: IsTensor[T[I], Z] = ???
}
