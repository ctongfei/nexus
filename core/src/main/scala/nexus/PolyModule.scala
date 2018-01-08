package nexus

/**
 * Type polymorphic unary module.
 * @author Tongfei Chen
 */
trait PolyModule1 extends PolyFunc1 {

  trait F[X, Y] extends Func1[X, Y]

  object F {
    def apply[X, Y](f: Func1[X, Y]): F[X, Y] = new F[X, Y] {
      def apply(x: Expr[X]) = f(x)
    }
  }

}


/**
 * Type polymorphic binary module.
 * @see [[PolyModule1]]
 */
trait PolyModule2 extends PolyFunc2 {

  trait F[X1, X2, Y] extends Func2[X1, X2, Y]

  object F {
    def apply[X1, X2, Y](f: Func2[X1, X2, Y]): F[X1, X2, Y] = new F[X1, X2, Y] {
      def apply(x1: Expr[X1], x2: Expr[X2]) = f(x1, x2)
    }
  }

}


trait PolyModule3 extends PolyFunc3 {

  trait F[X1, X2, X3, Y] extends Func3[X1, X2, X3, Y]

  object F {
    def apply[X1, X2, X3, Y](f: Func3[X1, X2, X3, Y]): F[X1, X2, X3, Y] = new F[X1, X2, X3, Y] {
      def apply(x1: Expr[X1], x2: Expr[X2], x3: Expr[X3]) = f(x1, x2, x3)
    }
  }

}
