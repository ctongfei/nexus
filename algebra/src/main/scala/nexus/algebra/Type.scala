package nexus.algebra

/**
 * Runtime type information related to a specific type. (akin to Scala's [[scala.reflect.ClassTag]]).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Type[@specialized(Boolean, Int, Float, Double) X]

object Type {

  case class Tuple2[A, B](_1: Type[A], _2: Type[B]) extends Type[(A, B)]
  case class Tuple3[A, B, C](_1: Type[A], _2: Type[B], _3: Type[C]) extends Type[(A, B, C)]

  private[this] object NonDifferentiableSingleton extends Type[Nothing]

  /**
   * Creates a type tag for any non-differentiable type [[X]].
   */
  def nonDifferentiable[X]: Type[X] = NonDifferentiableSingleton.asInstanceOf[Type[X]]

}
