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

  type F[X, Y] <: Func1[X, Y]

  /** Given input value, computes the output value. */
  def apply[X, Y](xv: X)(implicit f: F[X, Y]): Y = {
    val x = Input[X]()
    val (y, _) = Forward.compute(f(x))(x <<- xv)
    y
  }

  /** Given input expression, constructs the output expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] =
    f(x)

}


trait PolyFunc2 { self =>

  type F[X1, X2, Y] <: Func2[X1, X2, Y]

  def apply[X1, X2, Y](x1v: X1, x2v: X2)(implicit f: F[X1, X2, Y]): Y = {
    val x1 = Input[X1]()
    val x2 = Input[X2]()
    val (y, _) = Forward.compute(f(x1, x2))(x1 <<- x1v, x2 <<- x2v)
    y
  }

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] =
    f(x1, x2)

}

trait PolyFunc3 {

  type F[X1, X2, X3, Y] <: Func3[X1, X2, X3, Y]

  def apply[X1, X2, X3, Y](x1v: X1, x2v: X2, x3v: X3)(implicit f: F[X1, X2, X3, Y]): Y = {
    val x1 = Input[X1]()
    val x2 = Input[X2]()
    val x3 = Input[X3]()
    val (y, _) = Forward.compute(f(x1, x2, x3))(x1 <<- x1v, x2 <<- x2v, x3 <<- x3v)
    y
  }

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] =
    f(x1, x2, x3)

}
