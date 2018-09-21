package nexus
import nexus.algebra._

import scala.annotation._

/**
 * Any generic unary function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type member `Op`.
 *
 * Essentially, this function can be applied to a symbolic expression of type [[Expr]]`[X]` '''if and only if''' an
 * implicit `Op[X, Y]` is found, and the application returns a symbolic expression of type [[Expr]]`[Y]`.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class PolyOp1 extends PolyFunc1 {

  /**
   * Type of the actual grounded operator.
   * This acts as a type constraint expressing what type of variables this polymorphic operation can apply to.
   */
  @implicitNotFound("This operator cannot be applied to an argument of type ${X}.")
  trait F[X, Y] extends Op1[X, Y]

  override def apply[X, Y](x: Expr[X])(implicit f: F[X, Y]) = f(x)

  /** Applies this operation to a concrete value (forward computation). */
  def apply[X, Y](x: X)(implicit f: F[X, Y]): Y = f.forward(x)

  def ground[X, Y](implicit f: F[X, Y]) = f

}

/**
 * Any generic binary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type member `Op`.
 * @see [[PolyOp1]]
 * @since 0.1.0
 */
abstract class PolyOp2 extends PolyFunc2 { self =>

  @implicitNotFound("This operator cannot be applied to arguments of type ${X1} and ${X2}.")
  trait F[X1, X2, Y] extends Op2[X1, X2, Y]

  override def apply[X1, X2, Y](x1: Expr[X1], x2: Expr[X2])(implicit f: F[X1, X2, Y]) = f(x1, x2)

  /** Applies this operation to concrete values (forward computation). */
  def apply[X1, X2, Y](x1: X1, x2: X2)(implicit f: F[X1, X2, Y]): Y = f.forward(x1, x2)

  def ground[X1, X2, Y](implicit f: F[X1, X2, Y]) = f

  object Curried1 extends ParameterizedPolyOp1 {

    implicit def curriedLiteral1[X1, X2, Y](implicit f: self.F[X1, X2, Y]) = (x1: X1) =>
      new F[X2, Y] {
        type Tag[y] = f.Tag[y]
        def name = s"$f.curried1($x1)"
        def tag = f.tag
        override def differentiable = f.differentiable
        def forward(x2: X2) = f.forward(x1, x2)
        def backward(dy: Y, y: Y, x2: X2) = f.backward2(dy, y, x1, x2)
      }
  }

  object Curried2 extends ParameterizedPolyOp1 {
    implicit def curriedLiteral2[X1, X2, Y](implicit f: self.F[X1, X2, Y]) = (x2: X2) =>
      new F[X1, Y] {
        type Tag[y] = f.Tag[y]
        def name = s"$f.curried2($x2)"
        def tag = f.tag
        override def differentiable = f.differentiable
        def forward(x1: X1) = f.forward(x1, x2)
        def backward(dy: Y, y: Y, x1: X1) = f.backward1(dy, y, x1, x2)
      }
  }

}

/**
 * Any generic ternary neural (differential) function whose type parameters (axes, etc.) are not yet grounded:
 * itself can be applied to variables of different types constrained by the type member `Op`.
 * @see [[PolyOp1]]
 * @since 0.1.0
 */
abstract class PolyOp3 extends PolyFunc3 {

  @implicitNotFound("This operator cannot be applied to arguments of type ${X1}, ${X2} and ${X3}.")
  trait F[X1, X2, X3, Y] extends Op3[X1, X2, X3, Y]

  override def apply[X1, X2, X3, Y](x1: Expr[X1], x2: Expr[X2], x3: Expr[X3])(implicit f: F[X1, X2, X3, Y]) = f(x1, x2, x3)

  def apply[X1, X2, X3, Y](x1: X1, x2: X2, x3: X3)(implicit f: F[X1, X2, X3, Y]): Y = f.forward(x1, x2, x3)

  def ground[X1, X2, X3, Y](implicit f: F[X1, X2, X3, Y]) = f
}
