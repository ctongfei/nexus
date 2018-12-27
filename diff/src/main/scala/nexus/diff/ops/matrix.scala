package nexus.diff.ops

import nexus.diff._
import nexus._

object Det extends PolyOp1 {
  implicit def detF[T[_], R, A <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(A, A)], R] =
    new F[T[(A, A)], R] {
      def name = "Det"
      def tag = Tag.real[R]
      def forward(x: T[(A, A)]) = ???
      def backward(dy: R, y: R, x: T[(A, A)]) = ???
    }
}

object MatInv extends PolyOp1 {
  implicit def matInvF[T[_], R, A <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(A, A)], T[(A, A)]] =
    new F[T[(A, A)], T[(A, A)]] {
      def name = "MatInv"
      def tag = ???
      def forward(x: T[(A, A)]) = ???
      def backward(dy: T[(A, A)], y: T[(A, A)], x: T[(A, A)]) = ???
    }
}

object Trace extends PolyOp1 {
  implicit def traceF[T[_], R, A <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(A, A)], R] =
    new F[T[(A, A)], R] {
      def name = "Trace"
      def tag = Tag.real[R]
      def forward(x: T[(A, A)]) = ???
      def backward(dy: R, y: R, x: T[(A, A)]) = ???
    }
}
