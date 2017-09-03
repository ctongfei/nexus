package nexus.op

import nexus._
import nexus.algebra._
import shapeless._

trait DivF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Div"
}

object DivF {

  implicit def scalar[R](implicit R: RealOps[R]) = new DivF[R, R, R] {
    def gradOps = R
    def backward1(dy: R, y: R, x1: R, x2: R) = dy / x2
    def backward2(dy: R, y: R, x1: R, x2: R) = -dy * x1 / R.sqr(x2)
    def forward(x1: R, x2: R) = x1 / x2
  }

}

trait EDivF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "EDiv"
}

object EDivF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: TypedRealTensorOps[T, D]): EDivF[T[A], T[A], T[A]] =
    new EDivF[T[A], T[A], T[A]] {
      import T._
      def gradOps = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |/| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |/| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = -dy |*| x1 |/| eSqr(x2)
    }

}
