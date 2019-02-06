package nexus.diff

import nexus.diff.util._

/**
 * A placeholder for inputs to a computation graph.
 * See `tf.Placeholder`
 */
class Input[X] private[nexus](name: String = ExprName.nextInput) extends Symbolic[X] { self =>

  def tag = Tag.none[X] // no need to compute the gradient of the input
  def requireGrad = false

  // /** Constructs a neural function (lambda expression). */
  // def =>>[Y](y: Symbolic[Y]): Lambda1[X, Y] = Lambda1(this, y)

  override def toString = name

}

object Input {

  def apply[X](implicit name: sourcecode.Name): Symbolic[X] =
    new Input[X](name.value)

  def apply[X](name: String): Symbolic[X] = new Input[X](name)

}
