package nexus

/**
 * Represents a type-polymorphic unary operator that is parameterized.
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class ParameterizedPolyOp1 { self =>

  trait F[X, Y] extends Op1[X, Y]

  class Proxy[P](val parameter: P) extends PolyFunc1 {
    type F[X, Y] = P => self.F[X, Y]
    def ground[X, Y](implicit f: F[X, Y]) = f(parameter)
  }

  def apply[P](parameter: P) = new Proxy(parameter)

}

/**
 * Represents a type-polymorphic binary operator that is parameterized.
 */
abstract class ParameterizedPolyOp2 { self =>

  trait F[X1, X2, Y] extends Op2[X1, X2, Y]

  class Proxy[P](val parameter: P) extends PolyFunc2 {
    type F[X1, X2, Y] = P => self.F[X1, X2, Y]
    def ground[X1, X2, Y](implicit f: F[X1, X2, Y]) = f(parameter)
  }

  def apply[P](parameter: P) = new Proxy(parameter)

}

/**
 * Represents a type-polymorphic ternary operator that is parameterized.
 */
abstract class ParameterizedPolyOp3 { self =>

  trait F[X1, X2, X3, Y] extends Op3[X1, X2, X3, Y]

  class Proxy[P](val parameter: P) extends PolyFunc3 {
    type F[X1, X2, X3, Y] = P => self.F[X1, X2, X3, Y]
    override def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]) = f(parameter)
  }

  def apply[P](parameter: P) = new Proxy(parameter)

}
