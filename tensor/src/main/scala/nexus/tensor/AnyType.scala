package nexus.tensor

/**
 * @author Tongfei Chen
 * @since 0.1.0
 */
sealed trait AnyType[X]

object AnyType {

  private object singleton extends AnyType[Nothing]

  def apply[X]: AnyType[X] = singleton.asInstanceOf[AnyType[X]]

}
