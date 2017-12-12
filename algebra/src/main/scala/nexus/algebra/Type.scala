package nexus.algebra

/**
 * Runtime type information related to a specific type. (akin to Scala's [[scala.reflect.ClassTag]]).
 * @tparam X The type
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Type[X]

object Type {

  private[this] val singleton = new Type[Nothing] {}

  def empty[X]: Type[X] = singleton.asInstanceOf[Type[X]]

}

trait TypeH[T[_ <: $$]] {
  def ground[A <: $$]: Type[T[A]]
}
