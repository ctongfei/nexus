package nexus.syntax

import nexus.algebra._
import nexus.op._
import nexus.{$$, Expr}

/**
 * @author Tongfei Chen
 */
trait ExprTensorMixin {

  implicit class ExprTensorOps[T[_ <: $$], R, A <: $$](val x: Expr[T[A]])(implicit T: TypedRealTensorOps[T, R]) {

    def +(y: Expr[T[A]]): Expr[T[A]] = Add(x, y)
    def -(y: Expr[T[A]]): Expr[T[A]] = Sub(x, y)

    def |*|(y: Expr[T[A]]): Expr[T[A]] = EMul(x, y)
    def ⊙(y: Expr[T[A]]): Expr[T[A]] = EMul(x, y)

    def |/|(y: Expr[T[A]]): Expr[T[A]] = EDiv(x, y)

  }

}