package nexus.syntax

import nexus._
import nexus.ops._
import nexus.tensor._

/**
 * @author Tongfei Chen
 */
trait ExprRealMixin {

  implicit class ExprRealOps[R](val x: Expr[R])(implicit R: IsReal[R]) {

    def +(y: Expr[R]): Expr[R] = Add(x, y)

    def -(y: Expr[R]): Expr[R] = Sub(x, y)

    def *(y: Expr[R]): Expr[R] = Mul(x, y)

    def /(y: Expr[R]): Expr[R] = Div(x, y)

  }

  implicit class ExprRealOps2[T[_], R, A](val x: Expr[R])(implicit T: IsRealTensorK[T, R]) {

    def *:(y: Expr[T[A]]): Expr[T[A]] = Scale(x, y)

  }

}
