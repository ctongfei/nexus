package nexus.syntax

import nexus._
import nexus.algebra._
import nexus.op._

/**
 * @author Tongfei Chen
 */
trait ExprRealMixin {

  implicit class ExprRealOps[R](val x: Expr[R])(implicit R: IsReal[R]) {

    def +(y: DExpr[R]): Expr[R] = Add(x, y)

    def -(y: Expr[R]): Expr[R] = Sub(x, y)

    def *(y: Expr[R]): Expr[R] = Mul(x, y)

  }

  implicit class ExprRealOps2[T[_ <: $$], R, A <: $$](val x: Expr[R])(implicit T: IsTypedRealTensor[T, R]) {

    def *:(y: Expr[T[A]]): Expr[T[A]] = Scale(y, x)

  }

}
