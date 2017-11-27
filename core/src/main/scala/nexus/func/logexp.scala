package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

@implicitNotFound("Cannot apply Exp on ${X}.")
trait ExpF[X, Y] extends DOp1[X, Y] {
  def name = "Exp"
}

object ExpF {

  implicit def scalar[R](implicit R: IsReal[R]) = new ExpF[R, R] {
    def tag = R
    def forward(x: R) = R.exp(x)
    def backward(dy: R, y: R, x: R) = dy * y
  }

}

@implicitNotFound("Cannot apply Exp.Elementwise on ${X}.")
trait EExpF[X, Y] extends DOp1[X, Y] {
  def name = "Exp.Elementwise"
}

object EExpF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new EExpF[T[A], T[A]] {
    import T._
    def tag = T.ground[A]
    def forward(x: T[A]) = eExp(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y
  }

}

trait LogF[X, Y] extends DOp1[X, Y] {
  def name = "Log"
}

object LogF {

  implicit def scalar[R](implicit R: IsReal[R]) = new LogF[R, R] {
    def tag = R
    def forward(x: R) = R.log(x)
    def backward(dy: R, y: R, x: R) = dy / x //TODO: ?
  }

}

trait ELogF[X, Y] extends DOp1[X, Y] {
  def name = "Log.Elementwise"
}

object ELogF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new ELogF[T[A], T[A]] {
    def tag = T.ground[A]
    def forward(x: T[A]) = T.eLog(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |/| x //TODO: ?
  }

}