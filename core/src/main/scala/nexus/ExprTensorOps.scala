package nexus

import nexus.op._
import nexus.typelevel._
import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait ExprTensorMixin {

  implicit class ExprTensorOps[T[_ <: HList], D, A <: HList](val x: Expr[T[A]])(implicit val env: Env[T, D]) {

    def +(y: Expr[T[A]]): Expr[T[A]] = Add(x, y)
    def -(y: Expr[T[A]]): Expr[T[A]] = Sub(x, y)

    def |*|(y: Expr[T[A]]): Expr[T[A]] = EMul(x, y)
    def ⊙(y: Expr[T[A]]): Expr[T[A]] = EMul(x, y)

    def |/|(y: Expr[T[A]]): Expr[T[A]] = EDiv(x, y)

  }

}