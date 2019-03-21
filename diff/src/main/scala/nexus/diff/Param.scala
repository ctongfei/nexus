package nexus.diff

import nexus._

/**
 * A parameter of a model.
 * @param value Initial value of this parameter
 * @note A `Param` has to be differentiable by providing a `Grad[X]` instance as its type tag.
 */
case class Param[X] private(var value: X, name: String)(implicit grad: Grad[X]) extends Symbolic[X] with Traced[X] {

  // Circumvent typechecking issues when loading from parameterMap
  private[nexus] def assign_!(newValue: Any): Unit =
    value = newValue.asInstanceOf[X]

  final def requireGrad = true // or else, how could it be updated?

  def tag: Tag.Aux[X, Grad] = Tag of grad

  private[this] val ev = tag.ev

  def +=(g: X): Unit = if (ev.mutable)
    ev.addInplace(value, g)
  else value = ev.add(value, g)

  def -=(g: X): Unit = +=(ev.neg(g))

  /**
   * Coerces this parameter into one of the execution boxes ([[Symbolic]] / [[Traced]]).
   */
  def as[D[_]](implicit D: Algebra[D]): D[X] = D.fromParam(this)

  override def toString = name

}

object Param {

  /**
   * Shorthand syntax for creating a parameter.
   */  // Use `sourcecode.Name` to value the name of the Scala val
  def apply[X](value: X)(implicit name: sourcecode.Name, grad: Grad[X]): Param[X] =
    new Param[X](value, name.value)(grad)

  def apply[X](value: X, name: String)(implicit grad: Grad[X]): Param[X] =
    new Param[X](value, name)(grad)

}
