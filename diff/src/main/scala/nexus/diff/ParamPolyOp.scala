package nexus.diff

abstract class ParameterizedPolyOp0 { self =>

  trait P[Y] extends Op0[Y]

  class Proxy[PP](val parameter: PP) extends PolyFunc0 {
    type P[Y] = PP => self.P[Y]
    def ground[Y](implicit f: P[Y]) = f(parameter)
  }

  def apply[PP](parameter: PP): Proxy[PP] = new Proxy(parameter)
}

/**
 * Represents a type-polymorphic unary operator that is parameterized.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class ParameterizedPolyOp1 { self =>

  trait P[X, Y] extends Op1[X, Y]

  class Proxy[PP](val parameter: PP) extends PolyFunc1 {
    type P[X, Y] = PP => self.P[X, Y]
    def ground[X, Y](implicit f: P[X, Y]) = f(parameter)
  }

  /**
   * Constructs a polymorphic operator given the required parameter.
   */
  def apply[PP](parameter: PP): Proxy[PP] = new Proxy(parameter)

}

/**
 * Represents a type-polymorphic binary operator that is parameterized.
 */
abstract class ParameterizedPolyOp2 { self =>

  trait P[X1, X2, Y] extends Op2[X1, X2, Y]

  class Proxy[PP](val parameter: PP) extends PolyFunc2 {
    type P[X1, X2, Y] = PP => self.P[X1, X2, Y]
    def ground[X1, X2, Y](implicit f: P[X1, X2, Y]) = f(parameter)
  }

  def apply[PP](parameter: PP): Proxy[PP] = new Proxy(parameter)

}

/**
 * Represents a type-polymorphic ternary operator that is parameterized.
 */
abstract class ParameterizedPolyOp3 { self =>

  trait P[X1, X2, X3, Y] extends Op3[X1, X2, X3, Y]

  class Proxy[PP](val parameter: PP) extends PolyFunc3 {
    type P[X1, X2, X3, Y] = PP => self.P[X1, X2, X3, Y]
    override def ground[X1, X2, X3, Y](implicit f: P[X1, X2, X3, Y]) = f(parameter)
  }

  def apply[PP](parameter: PP): Proxy[PP] = new Proxy(parameter)

}
