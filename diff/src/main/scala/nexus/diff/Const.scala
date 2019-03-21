package nexus.diff

/**
 * A constant value in a computational graph.
 * @param value Value of this constant
 */
case class Const[X] private[nexus](value: X, name: String) extends Symbolic[X] with Traced[X] {

  final def tag = Tag.none[X]
  override def requireGrad = false
  override def toString = name

}

object Const {

  def apply[D[_], X](value: X)(implicit name: sourcecode.Name, D: Algebra[D]): D[X] =
    D.const(value, name.value)

  def apply[D[_], X](value: X, name: String)(implicit D: Algebra[D]): D[X] =
    D.const(value, name)

}
