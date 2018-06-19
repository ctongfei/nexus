package nexus

/**
 * Represents unary type-polymorphic functions that operates on expressions.
 *
 * Specifically, there are two important cases:
 *  - [[PolyOp1]]: where computation is directly encoded;
 *  - [[PolyModule1]]: where computation is interpreted.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait PolyFunc1 {

  /**
   * Type constraint / proof expressing what type of variables this polymorphic operation can apply to.
   *
   * Presence of an implicit `F[X, Y]` encodes the predicate
   * "This function can be applied on `X`, and the result type is `Y`."
   */
  type F[X, Y]

  /**
   * Grounds this polymorphic operator to an operator with type `Expr[X] => Expr[Y]` given proof that
   * this operator is applicable.
   */
  def ground[X, Y](implicit f: F[X, Y]): Func1[X, Y]

  /** Given input expression, constructs the output expression. */
  def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]): Expr[Y] =
    ground(f)(x)

}

/**
 * Binary type-polymorphic function.
 * @see [[PolyFunc1]]
 */
trait PolyFunc2 {

  /** Type constraint expressing what type of variables this polymorphic operation can apply to. */
  type F[X1, X2, Y]

  def ground[X1, X2, Y](implicit f: F[X1, X2, Y]): Func2[X1, X2, Y]

  def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]): Expr[Y] =
    ground(f)(x1, x2)

}


/**
 * Ternary type-polymorphic function.
 * @see [[PolyFunc1]], [[PolyFunc2]]
 */
trait PolyFunc3 {

  /** Type constraint expressing what type of variables this polymorphic operation can apply to. */
  type F[X1, X2, X3, Y]

  def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]): Func3[X1, X2, X3, Y]

  def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]): Expr[Y] =
    ground(f)(x1, x2, x3)

}
