package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

@implicitNotFound("Cannot apply ReLU to ${X}.")
trait ReLUF[X, Y] extends DOp1[X, Y] {
  def name = "ReLU"
}

object ReLUF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]): ReLUF[T[A], T[A]] =
    new ReLUF[T[A], T[A]] {
      import T._
      def tag = T.ground[A]
      def forward(x: T[A]) = relu(x)
      def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| pos(x)
    }

}

@implicitNotFound("Cannot apply Sigmoid to ${X}.")
trait SigmoidF[X, Y] extends DOp1[X, Y] {
  def name = "Sigmoid"
}

object SigmoidF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new SigmoidF[T[A], T[A]] {
    import T._
    def tag = T.ground[A]
    def forward(x: T[A]) = sigmoid(x)
    def backward(dy: T[A], y: T[A], x: T[A]) = dy |*| y |*| addS(-y, R.one)
  }

}

@implicitNotFound("Cannot apply SoftPlus to ${X}.")
trait SoftPlusF[X, Y] extends DOp1[X, Y] {
  def name = "SoftPlus"
}

object SoftPlusF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit ops: IsTypedRealTensor[T, R]): SoftPlusF[T[A], T[A]] =
    new SoftPlusF[T[A], T[A]] {
      import ops._
      def tag = ops.ground[A]
      def forward(x: T[A]) = eLog1p(eExp(x))
      def backward(dy: T[A], y: T[A], x: T[A]) = sigmoid(x)
    }

}

@implicitNotFound("Cannot apply Softmax to ${X}.")
trait SoftmaxF[X, Y] extends DOp1[X, Y] {
  def name = "Softmax"
}

object SoftmaxF {

  implicit def vector[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]) = new SoftmaxF[T[A::$], T[A::$]] {
    import T._
    def tag = T.ground[A::$]
    def forward(x: T[A::$]) = { // TODO: numerical stable algorithm: first scale by max
      val expX = eExp(x)
      expX :* R.inv(sum(expX))
    }
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = {
      val dyy = dy |*| y
      dyy - (y :* sum(dyy))
    }
  }

}
