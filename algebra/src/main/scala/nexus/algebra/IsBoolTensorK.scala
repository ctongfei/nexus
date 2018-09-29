package nexus.algebra

/**
 * @author Tongfei Chen
 */
trait IsBoolTensorK[T[_], B] extends IsTensorK[T, B] { self =>

  type ElementTag[b] = IsBool[b]
  val B: IsBool[B]
  def elementType = B



}
