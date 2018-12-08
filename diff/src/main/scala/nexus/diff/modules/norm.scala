package nexus.diff.modules

import nexus.diff._
import nexus.diff.ops._
import nexus.tensor._


object L1Distance extends PolyModule2 {
  implicit def l1DistanceF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R] = F { (x1, x2) =>
    L1Norm(x1 - x2)
  }
}

object L2Distance extends PolyModule2 {
  implicit def l2DistanceF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A], R] = F { (x1, x2) =>
    L2Norm(x1 - x2)
  }
}

// LpDistance

object L1Normalize extends PolyModule1 {
  implicit def l1NormalizeF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = F { x =>
    Scale(Inv(L1Norm(x)), x)
  }
}

object L2Normalize extends PolyModule1 {
  implicit def l2NormalizeF[T[_], R, A](implicit T: IsRealTensorK[T, R]): F[T[A], T[A]] = F { x =>
    Scale(Inv(L2Norm(x)), x)
  }
}
