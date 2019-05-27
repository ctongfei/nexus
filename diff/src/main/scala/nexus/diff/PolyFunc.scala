package nexus.diff

trait PolyFunc0 {
  type P[Y]
  def ground[Y](implicit f: P[Y]): Func0[Y]
  def apply[F[_]: Algebra, Y]()(implicit f: P[Y]): F[Y] = ground(f)()
}

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
   * Presence of an implicit `P[X, Y]` encodes the predicate
   * "This function can be applied on `X`, and the result type is `Y`."
   */
  type P[X, Y]

  /**
   * Grounds this polymorphic operator to an operator with concrete types given a proof that
   * this operator is applicable to those types.
   */
  def ground[X, Y](implicit f: P[X, Y]): Func1[X, Y]

  /** Given input expression, constructs the output expression. */
  def apply[F[_]: Algebra, X, Y](x: F[X])(implicit f: P[X, Y]): F[Y] = ground(f)(x)

}

/**
 * Binary type-polymorphic function.
 * @see [[PolyFunc1]]
 */
trait PolyFunc2 {

  /**
   * Type constraint / proof expressing what type of variables this polymorphic operation can apply to.
   * Presence of an implicit `F[X1, X2, Y]` encodes the predicate
   * "This function can be applied on `X1` and `X2`, and the result type is `Y`."
   */
  type P[X1, X2, Y]

  def ground[X1, X2, Y](implicit f: P[X1, X2, Y]): Func2[X1, X2, Y]

  def apply[F[_]: Algebra, X1, X2, Y](x1: F[X1], x2: F[X2])(implicit f: P[X1, X2, Y]): F[Y] = ground(f)(x1, x2)

}


/**
 * Ternary type-polymorphic function.
 * @see [[PolyFunc1]], [[PolyFunc2]]
 */
trait PolyFunc3 {

  /** Type constraint expressing what type of variables this polymorphic operation can apply to. */
  type P[X1, X2, X3, Y]

  def ground[X1, X2, X3, Y](implicit f: P[X1, X2, X3, Y]): Func3[X1, X2, X3, Y]

  def apply[F[_]: Algebra, X1, X2, X3, Y](x1: F[X1], x2: F[X2], x3: F[X3])(implicit f: P[X1, X2, X3, Y]): F[Y] = ground(f)(x1, x2, x3)

}
