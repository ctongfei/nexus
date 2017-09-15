package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.algebra.syntax._

@implicitNotFound("Cannot apply Scale to ${X1} and ${X2}.")
trait ScaleF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Scale"
}

object ScaleF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new ScaleF[T[A], R, T[A]] {
    def tag = T.ground[A]
    def forward(x1: T[A], x2: R) = x1 :* x2
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: R) = dy :* x2
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: R) = dy ⋅ x1
  }

}

@implicitNotFound("Cannot apply Dot to ${X1} and ${X2}.")
trait DotF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Dot"
}

object DotF {

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new DotF[T[A], T[A], R] {
    import T._
    def tag = T.R
    def forward(x1: T[A], x2: T[A]) = dot(x1, x2)
    def backward1(dy: R, y: R, x1: T[A], x2: T[A]) = x2 :* dy
    def backward2(dy: R, y: R, x1: T[A], x2: T[A]) = x1 :* dy
  }

}


@implicitNotFound("Cannot apply MatMul to ${X1} and ${X2}.")
trait MatMulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "MatMul"
}

object MatMulF {
  implicit def matrix[T[_ <: $$], R, A, B, C](implicit T: IsTypedRealTensor[T, R]): MatMulF[T[A::B::$], T[B::C::$], T[A::C::$]] =
    new MatMulF[T[A::B::$], T[B::C::$], T[A::C::$]] {
      import T._
      def tag = T.ground[A::C::$]
      def forward(x1: T[A::B::$], x2: T[B::C::$]) = mmMul(x1, x2)
      def backward1(dy: T[A::C::$], y: T[A::C::$], x1: T[A::B::$], x2: T[B::C::$]) = mmMul(dy, transpose(x2))
      def backward2(dy: T[A::C::$], y: T[A::C::$], x1: T[A::B::$], x2: T[B::C::$]) = mmMul(transpose(x1), dy)
    }
}

@implicitNotFound("Cannot apply Transpose to ${X}.")
trait TransposeF[X, Y] extends DOp1[X, Y] {
  def name = "Transpose"
}

object TransposeF {

  implicit def matrix[T[_ <: $$], R, A, B](implicit T: IsTypedRealTensor[T, R]) = new TransposeF[T[A::B::$], T[B::A::$]] {
    import T._
    def tag = T.ground[B::A::$]
    def forward(x: T[A::B::$]) = transpose(x)
    def backward(dy: T[B::A::$], y: T[B::A::$], x: T[A::B::$]) = transpose(dy)
  }

}

@implicitNotFound("Cannot apply MVMul to ${X1} and ${X2}.")
trait MVMulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "MVMul"
}

object MVMulF {

  implicit def mv[T[_ <: $$], R, A, B](implicit T: IsTypedRealTensor[T, R]): MVMulF[T[B::A::$], T[A::$], T[B::$]] =
    new MVMulF[T[B::A::$], T[A::$], T[B::$]] {
      import T._
      def tag = T.ground[B::$]
      def forward(x1: T[B::A::$], x2: T[A::$]) = mvMul(x1, x2)
      def backward1(dy: T[B::$], y: T[B::$], x1: T[B::A::$], x2: T[A::$]) = vvMul(dy, x2)
      def backward2(dy: T[B::$], y: T[B::$], x1: T[B::A::$], x2: T[A::$]) = mvMul(transpose(x1), dy)
    }

}

@implicitNotFound("Cannot apply Contract to ${X1} and ${X2}.")
trait ContractF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Contract"
}

object ContractF {

  implicit def tensor[T[_ <: $$], R, A <: $$, B <: $$, C <: $$](implicit T: IsTypedRealTensor[T, R], sd: SymDiff.Aux[A, B, C]) =
    new ContractF[T[A], T[B], T[C]] {
      import T._
      def tag = T.ground[C]
      def forward(x1: T[A], x2: T[B]) = x1 ⋈ x2
      def backward1(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = ??? // dy ⋈ x2
      def backward2(dy: T[C], y: T[C], x1: T[A], x2: T[B]) = ??? // dy ⋈ x1
    }

}
