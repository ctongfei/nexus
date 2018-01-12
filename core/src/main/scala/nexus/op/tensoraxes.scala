package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import shapeless.Nat

/**
 * Renaming an axis in any tensor.
 *
 * @example {{{ Rename(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Rename extends ParameterizedPolyOp1 {

  implicit def renameF[T[_], R, A, U: Label, V: Label, B]
  (implicit r: Replace.Aux[A, U, V, B], T: IsRealTensorK[T, R]) = (uv: (U, V)) =>
    new F[T[A], T[B]] {
      val (u, v) = uv
      import T._
      def name = s"Rename[${objTypeName(u)} -> ${objTypeName(v)}]"
      def tag(tx: Type[T[A]]) = T.ground[B]
      def forward(x: T[A]) = typeWith[B](untype(x))
      def backward(dy: T[B], y: T[B], x: T[A]) = typeWith[A](untype(dy))
    }

}


object Concat extends ParameterizedPolyOp2 {

  implicit def concatF[T[_], R, A, U: Label, N <: Nat]
  (implicit n: IndexOf.Aux[A, U, N], T: IsRealTensorK[T, R]) = (u: U) =>
    new F[T[A], T[A], T[A]] {
        def name = s"Concat[${objTypeName(u)}]"
        def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = T.ground[A] // TODO: size-aware
        def forward(x1: T[A], x2: T[A]) = ???
        def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
        def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
    }

}


object ExpandDim extends ParameterizedPolyOp1 {

  implicit def expandDimF[T[_], R, A, N <: Nat, U: Label, B]
  (implicit ia: InsertAt.Aux[A, N, U, B], T: IsRealTensorK[T, R]) = (nu: (N, U)) =>
    new F[T[A], T[B]] {
        val (n, u) = nu
        def name = s"ExpandDim[$n -> ${objTypeName(u)}]"
        def tag(tx1: Type[T[A]]) = T.ground[B]
        def forward(x: T[A]) = ???
        def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }

}

object Squeeze extends ParameterizedPolyOp1 {

  implicit def squeezeF[T[_], R, A, N <: Nat, U: Label, B]
  (implicit ix: IndexOf.Aux[A, U, N], rx: RemoveAt.Aux[A, N, B], T: IsRealTensorK[T, R]) = (u: U) =>
    new F[T[A], T[B]] {
        def name = s"Squeeze[${objTypeName(u)}]"
        def tag(tx: Type[T[A]]) = T.ground[B]
        def forward(x: T[A]) = ???
        def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }

}
