package nexus

import nexus.exec._

/**
 * Any unary type-polymorphic function that can operate
 * on both the expression level and the value level.
 * Can be either of these two:
 *  - [[PolyOp1]]: where computation is direct;
 *  - [[PolyModule1]]: where computation is interpreted.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait PolyFunc1 {

  /** Type constraint expressing what type of variables this polymorphic operation can apply to. */
  type F[X, Y]

  /** Given input expression, constructs the output expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] =
    ground(f)(x)

  def ground[X, Y](implicit f: F[X, Y]): Func1[X, Y]

}


trait PolyFunc2 {

  /** Type constraint expressing what type of variables this polymorphic operation can apply to. */
  type F[X1, X2, Y]

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] =
    ground(f)(x1, x2)

  def ground[X1, X2, Y](implicit f: F[X1, X2, Y]): Func2[X1, X2, Y]

}

trait PolyFunc3 {

  /** Type constraint expressing what type of variables this polymorphic operation can apply to. */
  type F[X1, X2, X3, Y]

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] =
    ground(f)(x1, x2, x3)

  def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]): Func3[X1, X2, X3, Y]

}
