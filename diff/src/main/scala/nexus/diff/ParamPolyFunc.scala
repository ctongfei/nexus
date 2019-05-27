package nexus.diff

/**
 * Represents a parameterized polymorphic unary function.
 * i.e., when instantiating such a function, some parameter has to be provided.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class ParameterizedPolyFunc1 { self: Singleton =>

  type P[X, Y]

  def ground[X, Y](implicit f: P[X, Y]): Func1[X, Y]

  class Proxy[PP](val parameter: PP) extends PolyFunc1 {
    type P[X, Y] = PP => self.P[X, Y]
    def ground[X, Y](implicit f: P[X, Y]) = self.ground(f(parameter))
  }

  /**
   * Constructs a polymorphic function given the required parameters.
   */
  def apply[PP](parameter: PP): Proxy[PP] = new Proxy(parameter)

}
