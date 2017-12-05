package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.func.factory._

object ExpF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Exp"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.exp(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * y

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Exp.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eExp(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| y
  }
}

object LogF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Log"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.log(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy / x //TODO: ?

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Log.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eLog(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |/| x //TODO: ?
  }
}
