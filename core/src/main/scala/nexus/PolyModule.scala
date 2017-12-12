package nexus

import nexus.exec._

/**
 * Type polymorphic unary module.
 * @author Tongfei Chen
 */
trait PolyModule1 {

  trait F[X, Y] extends Module[X, Y]

  object F {
    def apply[X, Y](f: Expr[X] => Expr[Y]): F[X, Y] = new F[X, Y] {
      def apply(x: Expr[X]) = f(x)
    }
  }

  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] = f(x)

  def apply[X, Y](x: X)(implicit f: F[X, Y]): Y = {
    val xi = Input[X]()
    val (y, _) = Forward.compute(f(xi))(xi <<- x)
    y
  }

}


/**
 * Type polymorphic binary module.
 * @see [[PolyModule1]]
 */
trait PolyModule2 {

  trait F[X1, X2, Y] extends Module2[X1, X2, Y]

  object F {
    def apply[X1, X2, Y](f: (Expr[X1], Expr[X2]) => Expr[Y]): F[X1, X2, Y] = new F[X1, X2, Y] {
      def apply(x1: Expr[X1], x2: Expr[X2]) = f(x1, x2)
    }
  }

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] = f(x1, x2)

  def apply[X1, X2, Y](x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): Y = {
    val x1i = Input[X1]()
    val x2i = Input[X2]()
    val (y, _) = Forward.compute(f(x1i, x2i))(x1i <<- x1, x2i <<- x2)
    y
  }
}
