package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.func.factory._

object IdF extends TypeInvariantDOp1Factory[Grad] {
  def name = "Id"
  def forward[X](x: X)(implicit X: Grad[X]) = x
  def backward[X](dy: X, y: X, x: X)(implicit X: Grad[X]) = dy
}

object AddF extends TypeInvariantDOp2Factory[Grad] {
  def name = "Add"
  def forward[R](x1: R, x2: R)(implicit R: Grad[R]) = x1 + x2
  def backward1[R](dy: R, y: R, x1: R, x2: R)(implicit R: Grad[R]) = dy
  def backward2[R](dy: R, y: R, x1: R, x2: R)(implicit R: Grad[R]) = dy
}

object SubF extends TypeInvariantDOp2Factory[Grad] {
  def name = "Sub"
  def forward[X](x1: X, x2: X)(implicit X: Grad[X]) = x1 - x2
  def backward1[X](dy: X, y: X, x1: X, x2: X)(implicit X: Grad[X]) = dy
  def backward2[X](dy: X, y: X, x1: X, x2: X)(implicit X: Grad[X]) = -dy
}

object NegF extends TypeInvariantDOp1Factory[Grad] {
  def name = "Neg"
  def forward[X](x: X)(implicit X: Grad[X]) = -x
  def backward[X](dy: X, y: X, x: X)(implicit X: Grad[X]) = -dy
}

object MulF extends TypeInvariantDOp2Factory[IsReal] {
  def name = "Mul"
  def forward[X](x1: X, x2: X)(implicit X: IsReal[X]) = x1 * x2
  def backward1[X](dy: X, y: X, x1: X, x2: X)(implicit X: IsReal[X]) = dy * x2
  def backward2[X](dy: X, y: X, x1: X, x2: X)(implicit X: IsReal[X]) = dy * x1

  object Elementwise extends AxesInvariantTensorOp2Factory[IsTypedRealTensor] {
    def name = "Mul.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, E]) = x1 |*| x2
    def backward1[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| x2
    def backward2[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| x1
  }
}

object DivF extends TypeInvariantDOp2Factory[IsReal] {
  def name = "Div"
  def forward[X](x1: X, x2: X)(implicit X: IsReal[X]) = x1 / x2
  def backward1[X](dy: X, y: X, x1: X, x2: X)(implicit X: IsReal[X]) = dy / x2
  def backward2[X](dy: X, y: X, x1: X, x2: X)(implicit X: IsReal[X]) = -dy * x1 / X.sqr(x2)

  object Elementwise extends AxesInvariantTensorOp2Factory[IsTypedRealTensor] {
    def name = "Div.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, E]) = x1 |/| x2
    def backward1[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |/| x2
    def backward2[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x1: T[A], x2: T[A])(implicit T: IsTypedRealTensor[T, E]) = -dy |*| x1 |/| T.eSqr(x2)
  }
}

object InvF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Inv"
  def forward[X](x: X)(implicit X: IsReal[X]) = X.inv(x)
  def backward[X](dy: X, y: X, x: X)(implicit X: IsReal[X]) = -dy * X.sqr(y)

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Inv.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eInv(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = -dy |*| T.eSqr(y)
  }
}
