package nexus.diff.modules

import nexus.diff._
import nexus.diff.ops._
import nexus._


object L1Distance extends PolyModule2 {
  implicit def l1DistanceF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I], R] =
    new P[T[I], T[I], R] {
      def apply[F[_] : Algebra](x1: F[T[I]], x2: F[T[I]]) = L1Norm(Sub(x1, x2))
      def parameters = Set()
    }
}

object L2Distance extends PolyModule2 {
  implicit def l2DistanceF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I], R] =
    new P[T[I], T[I], R] {
      def apply[F[_] : Algebra](x1: F[T[I]], x2: F[T[I]]) = L2Norm(Sub(x1, x2))
      def parameters = Set()
    }
}

// LpDistance

object L1Normalize extends PolyModule1 {
  implicit def l1NormalizeF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I]] =
    new P[T[I], T[I]] {
      def apply[F[_] : Algebra](x: F[T[I]]) = Scale(x, Inv(L1Norm(x)))
      def parameters = Set()
  }
}

object L2Normalize extends PolyModule1 {
  implicit def l2NormalizeF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I]] =
    new P[T[I], T[I]] {
      def apply[F[_] : Algebra](x: F[T[I]]) = Scale(x, Inv(L2Norm(x)))
      def parameters = Set()
    }
}
