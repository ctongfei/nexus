package nexus.tensor

/**
 * @author Tongfei Chen
 */
trait IsIntTensorK[T[_], Z] extends IsTensorK[T, Z] { self =>

  type ElementTag[z] = IsInt[z]

  override type TensorTag[ta] = IsTensor[ta, Z] // TODO

  val Z: IsInt[Z]
  def elementType = Z

  def zeroBy[a](x: T[a]): T[a]

  def add[a](x: T[a], y: T[a]): T[a] = ???
  def neg[a](x: T[a]): T[a] = ???
  def sub[a](x: T[a], y: T[a]): T[a] = ???
  def eMul[a](x: T[a], y: T[a]): T[a] = ???

  def ground[a]: IsTensor[T[a], Z] = ???
}
