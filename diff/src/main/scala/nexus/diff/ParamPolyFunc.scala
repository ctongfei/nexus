package nexus.diff

/**
 * Represents a parameterized polymorphic unary function.
 * i.e., when instantiating such a function, some parameter has to be provided.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class ParameterizedPolyFunc1 { self: Singleton =>

  type F[X, Y]

  def ground[X, Y](implicit f: F[X, Y]): Func1[X, Y]

  class Proxy[P](val parameter: P) extends PolyFunc1 {
    type F[X, Y] = P => self.F[X, Y]
    def ground[X, Y](implicit f: F[X, Y]) = self.ground(f(parameter))
  }

  /**
   * Constructs a polymorphic function given the required parameters.
   */
  def apply[P](parameter: P): Proxy[P] = new Proxy(parameter)

}
