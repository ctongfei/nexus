package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.func.factory._
import nexus.op._

object AbsF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Abs"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.abs(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.sgn(x)

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Abs.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eAbs(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.eSgn(x)
  }
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
