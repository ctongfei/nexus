package nexus.tensor

/**
 * Witnesses that a type is not differentiable.
 * @author Tongfei Chen
 */
sealed trait NonGrad[X]

object NonGrad {

  def apply[X] = nonGrad[X]

  private object singleton extends NonGrad[Nothing]

  implicit def nonGrad[X] = singleton.asInstanceOf[NonGrad[X]]

  implicit def ambiguity[X: Grad] = singleton.asInstanceOf[NonGrad[X]]

}
