package nexus.diff.ops

import nexus.diff._
import nexus._

object Det extends PolyOp1 {
  implicit def detF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(I, I)], R] =
    new F[T[(I, I)], R] {
      def name = "Det"
      def tag = Tag.real[R]
      def forward(x: T[(I, I)]) = ???
      def backward(dy: R, y: R, x: T[(I, I)]) = ???
    }
}

object MatInv extends PolyOp1 {
  implicit def matInvF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(I, I)], T[(I, I)]] =
    new F[T[(I, I)], T[(I, I)]] {
      def name = "MatInv"
      def tag = ???
      def forward(x: T[(I, I)]) = ???
      def backward(dy: T[(I, I)], y: T[(I, I)], x: T[(I, I)]) = ???
    }
}

object Trace extends PolyOp1 {
  implicit def traceF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(I, I)], R] =
    new F[T[(I, I)], R] {
      def name = "Trace"
      def tag = Tag.real[R]
      def forward(x: T[(I, I)]) = ???
      def backward(dy: R, y: R, x: T[(I, I)]) = ???
    }
}

object Diag extends PolyOp1 {
  implicit def diagF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[(I, I)], T[I]] =
    new F[T[(I, I)], T[I]] {
      def name = "Diag"
      def tag = Tag.realTensor[T, R, I]
      def forward(x: T[(I, I)]) = ???
      def backward(dy: T[I], y: T[I], x: T[(I, I)]) = ???
    }
}

object MatFromDiag extends PolyOp1 {
  implicit def matFromDiagF[T[_], R, I <: Dim](implicit T: IsRealTensorK[T, R]): F[T[I], T[(I, I)]] =
    new F[T[I], T[(I, I)]] {
      override def name: String = "MatFromDiag"
      override def tag = Tag.realTensor[T, R, (I, I)]
      override def forward(x: T[I]): T[(I, I)] = ???
      override def backward(dy: T[(I, I)], y: T[(I, I)], x: T[I]): T[I] = ???
    }
}
