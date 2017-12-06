package nexus


abstract class PolyDOp1 {

  trait F[X, Y] extends DOp1[X, Y]

  /** Applies this operation to a concrete value (forward computation). */
  def apply[X, Y](x: X)(implicit f: F[X, Y]): Y = f.forward(x)

  /** Applies this operation to a symbolic expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] = Apply1(f, x)
}

abstract class PolyDOp2 {

  trait F[X1, X2, Y] extends DOp2[X1, X2, Y]

  /** Applies this operation to concrete values (forward computation). */
  def apply[X1, X2, Y](x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): Y = f.forward(x1, x2)

  /** Applies this operation to symbolic expressions. */
  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] = Apply2(f, x1, x2)

}

abstract class PolyDOp3 {

  trait F[X1, X2, X3, Y] extends DOp3[X1, X2, X3, Y]

  def apply[X1, X2, X3, Y](x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): Y = f.forward(x1, x2, x3)

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] = Apply3(f, x1, x2, x3)

}
