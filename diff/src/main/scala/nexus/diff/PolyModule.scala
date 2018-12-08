package nexus.diff

/**
 * Type polymorphic unary module.
 * @author Tongfei Chen
 */
trait PolyModule1 extends PolyFunc1 {

  trait F[X, Y] extends Module1[X, Y]

  object F {
    def apply[X, Y](f: Func1[X, Y]): F[X, Y] = new F[X, Y] {
      def parameters = Set()
      def apply(x: Symbolic[X]) = f(x)
    }
  }

  def ground[X, Y](implicit f: F[X, Y]) = f
}

/**
 * Type polymorphic binary module.
 * @see [[PolyModule1]]
 */
trait PolyModule2 extends PolyFunc2 {

  trait F[X1, X2, Y] extends Module2[X1, X2, Y]

  object F {
    def apply[X1, X2, Y](f: Func2[X1, X2, Y]): F[X1, X2, Y] = new F[X1, X2, Y] {
      def parameters = Set()
      def apply(x1: Symbolic[X1], x2: Symbolic[X2]) = f(x1, x2)
    }
  }

  def ground[X1, X2, Y](implicit f: F[X1, X2, Y]) = f

}


/**
 * Type-polymorphic ternary module.
 * @see [[PolyModule1]], [[PolyModule2]]
 */
trait PolyModule3 extends PolyFunc3 {

  trait F[X1, X2, X3, Y] extends Module3[X1, X2, X3, Y]

  object F {
    def apply[X1, X2, X3, Y](f: Func3[X1, X2, X3, Y]): F[X1, X2, X3, Y] = new F[X1, X2, X3, Y] {
      def parameters = Set()
      def apply(x1: Symbolic[X1], x2: Symbolic[X2], x3: Symbolic[X3]) = f(x1, x2, x3)
    }
  }


  def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]) = f

}
