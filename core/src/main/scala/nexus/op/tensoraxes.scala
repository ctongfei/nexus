package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import shapeless.Nat

import scala.annotation._

/**
 * Renaming an axis in any tensor.
 *
 * @example {{{ Rename(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Rename extends ParamPolyOp1 {

  implicit def instance[T[_ <: $$], R, A <: $$, U, V, B <: $$]
  (implicit r: Replace.Aux[A, U, V, B], T: IsRealTensorH[T, R]) =
    new F[(U, V), T[A], T[B]] {
      def apply(p: (U, V)) = new Op1[T[A], T[B]] {
        val (u, v) = p
        import T._
        def name = s"Rename[${objTypeName(u)} -> ${objTypeName(v)}]"
        def tag(tx: Type[T[A]]) = T.ground[B]
        def differentiable = true
        def forward(x: T[A]) = typeWith[B](untype(x))
        def backward(dy: T[B], y: T[B], x: T[A]) = typeWith[A](untype(dy))
      }
    }

}


object Concat extends ParamPolyOp2 {

  implicit def instance[T[_ <: $$], R, A <: $$, U, N <: Nat]
  (implicit n: IndexOf.Aux[A, U, N], T: IsRealTensorH[T, R]) =
    new F[U, T[A], T[A], T[A]] {
      def apply(u: U) = new Op2[T[A], T[A], T[A]] {
        def name = s"Concat[${objTypeName(u)}]"
        def tag(tx1: Type[T[A]], tx2: Type[T[A]]) = T.ground[A] // TODO: size-aware
        def differentiable = true
        def forward(x1: T[A], x2: T[A]) = ???
        def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
        def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
      }
    }

}


object ExpandDim extends ParamPolyOp1 {

  implicit def instance[T[_ <: $$], R, A <: $$, N <: Nat, U, B <: $$]
  (implicit ia: InsertAt.Aux[A, N, U, B], T: IsRealTensorH[T, R]) =
    new F[(N, U), T[A], T[B]] {
      def apply(nu: (N, U)) = new Op1[T[A], T[B]] {
        val (n, u) = nu
        def name = s"ExpandDim[$n -> ${objTypeName(u)}]"
        def tag(tx1: Type[T[A]]) = T.ground[B]
        def differentiable = true
        def forward(x: T[A]) = ???
        def backward(dy: T[B], y: T[B], x: T[A]) = ???
      }
    }

}

object Squeeze extends ParamPolyOp1 {

  implicit def instance[T[_ <: $$], R, A <: $$, N <: Nat, U, B <: $$]
  (implicit ix: IndexOf.Aux[A, U, N], rx: RemoveAt.Aux[A, N, B], T: IsRealTensorH[T, R]) =
    new F[U, T[A], T[B]] {
      def apply(u: U) = new Op1[T[A], T[B]] {
        def name = s"Squeeze[${objTypeName(u)}]"
        def tag(tx: Type[T[A]]) = T.ground[B]
        def differentiable = true
        def forward(x: T[A]) = ???
        def backward(dy: T[B], y: T[B], x: T[A]) = ???
      }
    }

}
