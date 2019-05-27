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

  def +=(g: X): Unit = if (grad.mutable)
    grad.addInplace(value, g)
  else value = grad.add(value, g)

  def -=(g: X): Unit = +=(grad.neg(g))

  /**
   * Coerces this parameter into one of the execution boxes ([[Symbolic]] / [[Traced]]).
   */
  def as[F[_]](implicit F: Algebra[F]): F[X] = F.fromParam(this)

  override def toString = name

}

object Param {

  /**
   * Shorthand syntax for creating a parameter.
   */  // Use `sourcecode.Name` to get the name of the Scala val
  def apply[X](value: X)(implicit name: sourcecode.Name, grad: Grad[X]): Param[X] =
    new Param[X](value, name.value)(grad)

  def apply[X](value: X, name: String)(implicit grad: Grad[X]): Param[X] =
    new Param[X](value, name)(grad)

}
