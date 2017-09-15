package nexus.func

import nexus._
import nexus.algebra._

@implicitNotFound("Cannot apply WrapScalar to ${X}.")
trait WrapScalarF[X, Y] extends DOp1[X, Y] {
  def name = "WrapScalar"
}

object WrapScalarF {

  implicit def scalar[T[_ <: $$], R](implicit T: IsTypedRealTensor[T, R]): WrapScalarF[R, T[$]] =
    new WrapScalarF[R, T[$]] {
      def tag = T.ground[$]
      def backward(dy: T[$], y: T[$], x: R) = T.unwrapScalar(dy)
      def forward(x: R) = T.wrapScalar(x)
    }

}


@implicitNotFound("Cannot apply UnwrapScalar to ${X}.")
trait UnwrapScalarF[X, Y] extends DOp1[X, Y] {
  def name = "UnwrapScalar"
}

object UnwrapScalarF {

  implicit def scalar[T[_ <: $$], R](implicit T: IsTypedRealTensor[T, R]): UnwrapScalarF[T[$], R] =
    new UnwrapScalarF[T[$], R] {
      def tag = T.R
      def backward(dy: R, y: R, x: T[$]) = T.wrapScalar(dy)
      def forward(x: T[$]) = T.unwrapScalar(x)
    }

}


trait OneHotF[P, X, Y] extends (P => Op1[X, Y])

object OneHotF {

  //implicit def tensor[TI[_ <: $$], TR[_ <: $$], A <: $$, U] = new OneHotF[U, TI[A], TR[A::U::$]]

}