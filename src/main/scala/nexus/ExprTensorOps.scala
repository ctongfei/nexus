package nexus

import nexus.op._
import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait ExprTensorMixin {

  implicit class ExprTensorOps[T[_, _ <: HList], D, A <: HList](val x: Expr[T[D, A]]) {

    def +(y: Expr[T[D, A]])(implicit env: Env[T, D]): Expr[T[D, A]] = Add(x, y)

    def |>[Y](op: Module[T[D, A], Y]) = op(x)

    def |>[F[X, Y] <: Op1[X, Y], Y](op: GenOp1[F])(implicit f: F[T[D, A], Y]) = f(x)

  }

}