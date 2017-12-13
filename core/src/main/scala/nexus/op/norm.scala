package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.instances.all._
import nexus.algebra.syntax._
import nexus.op.base._

/**
 * Absolute value.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Abs extends TypeInvariantPolyDOp1[IsReal] {

  def name = "Abs"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.abs(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.sgn(x)

  object Elementwise extends TypeInvariantTensorPolyDOp1[IsRealTensor] {
    def name = "Abs.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsRealTensor[T, R]) = T.eAbs(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsRealTensor[T, R]) = dy |*| T.eSgn(x)
  }

}

object L1Norm extends PolyDOp1 {

  implicit def instance[T[_ <: $$], R, A <: $$](implicit T: IsRealTensor[T, R]): F[T[A], R] =
    new F[T[A], R] {
      def name = "L1Norm"
      def tag = T.R
      def forward(x: T[A]) = T.sum(T.eAbs(x))
      def backward(dy: R, y: R, x: T[A]) = T.eSgn(x) :* dy
    }
}

object L2Norm extends TaSPolyDOp1 {
  def name = "L2Norm"
  def forward[T[_ <: $$], R, As <: $$](x: T[As])(implicit T: IsRealTensor[T, R]) = T.R.sqrt(T.dot(x, x))
  def backward[T[_ <: $$], R, As <: $$](dy: R, y: R, x: T[As])(implicit T: IsRealTensor[T, R]) = x :* T.R.div(dy, y)
}

object L1Distance extends PolyModule2 {
  implicit def synthesize[T[_ <: $$], R, As <: $$](implicit T: IsRealTensor[T, R]): F[T[As], T[As], R] = F {
    (x1, x2) => L1Norm(x1 - x2)
  }
}

object L2Distance extends PolyModule2 {
  implicit def synthesize[T[_ <: $$], R, As <: $$](implicit T: IsRealTensor[T, R]): F[T[As], T[As], R] = F {
    (x1, x2) => L2Norm(x1 - x2)
  }
}

object L1Normalize extends PolyModule1 {
  implicit def synthesize[T[_ <: $$], R, A <: $$](implicit T: IsRealTensor[T, R]): F[T[A], T[A]] = F {
    x => Scale(Inv(L1Norm(x)), x)
  }
}

object L2Normalize extends PolyModule1 {
  implicit def synthesize[T[_ <: $$], R, As <: $$](implicit T: IsRealTensor[T, R]): F[T[As], T[As]] = F {
    x => Scale(Inv(L2Norm(x)), x)
  }
}

object CosineSimilarity extends PolyModule2 {
  implicit def synthesize[T[_ <: $$], R, As <: $$](implicit T: IsRealTensor[T, R]): F[T[As], T[As], R] = F {
    (x1, x2) => Dot(x1, x2) / L2Norm(x1) / L2Norm(x2)
  }
}
