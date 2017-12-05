package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import nexus.func.factory._

object ReLUF extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
  def name = "ReLU"
  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.relu(x)
  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.pos(x)
}

object SigmoidF extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
  def name = "Sigmoid"
  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.sigmoid(x)
  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| y |*| T.addS(-y, T.R.one)
}

object SoftPlusF extends AxesInvariantTensorOp1Factory[IsTypedRealTensor] {
  def name = "SoftPlus"
  def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eLog1p(T.eExp(x))
  def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.sigmoid(x)
}

object SoftmaxF extends AxesInvariantVectorOp1Factory[IsTypedRealTensor] {
  def name = "Softmax"
  def forward[T[_ <: $$], E, A](x: T[A::$])(implicit T: IsTypedRealTensor[T, E]) = {
    import T._
    val expX = eExp(x) // TODO: a numerically stabler algorithm
    expX :* R.inv(sum(expX))
  }
  def backward[T[_ <: $$], E, A](dy: T[A::$], y: T[A::$], x: T[A::$])(implicit T: IsTypedRealTensor[T, E]) = {
    import T._
    val dyy = dy |*| y
    dyy - (y :* sum(dyy))
  }
}
