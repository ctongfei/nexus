package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.func.factory._

object SinF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Sin"
  def forward[X](x: X)(implicit X: IsReal[X]) = X.sin(x)
  def backward[X](dy: X, y: X, x: X)(implicit X: IsReal[X]) = dy * X.cos(x)

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Sin.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eSin(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.eCos(x)
  }
}

object CosF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Cos"
  def forward[X](x: X)(implicit X: IsReal[X]) = X.cos(x)
  def backward[X](dy: X, y: X, x: X)(implicit X: IsReal[X]) = -dy * X.sin(x)

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Cos.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eCos(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = -dy |*| T.eSin(x)
  }
}

object TanF extends TypeInvariantDOp1Factory[IsReal] {
  def name = "Tan"
  def forward[X](x: X)(implicit X: IsReal[X]) = X.tan(x)
  def backward[X](dy: X, y: X, x: X)(implicit X: IsReal[X]) = dy * (X.sqr(y) + X.one)

  object Elementwise extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
    def name = "Tan.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eTan(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.addS(T.eSqr(y), T.R.one)
  }
}


//TODO: ATan2
//TODO: Sinh, Cosh, Tanh
//TODO: Arcsin, Arccos, Arctan
