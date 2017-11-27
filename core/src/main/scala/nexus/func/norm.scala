package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.op.batch._

/**
 * @author Tongfei Chen
 */
trait AbsF[X, Y] extends DOp1[X, Y] {
  def name = "Abs"
}

object AbsF {

  implicit def scalar[R](implicit R: IsReal[R]): AbsF[R, R] =
    new AbsF[R, R] {
      def tag = R
      def backward(dy: R, y: R, x: R) = dy * R.sgn(x)
      def forward(x: R) = R.abs(x)
    }

}

object EAbsU extends RealElementwiseDOp1 {
  def name = "Abs.Elementwise"
  def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eAbs(x)
  def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| T.eSgn(x)
}


trait L1NormalizeF[X, Y] extends DOp1[X, Y] {
  def name = "L1Normalize"
}

object L1NormalizeF {
  implicit def vector[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]) = new L1NormalizeF[T[A::$], T[A::$]] {
    import T._
    def tag = T.ground[A::$]
    def forward(x: T[A::$]) = x :* R.inv(sum(x))
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = ???
  }
}
