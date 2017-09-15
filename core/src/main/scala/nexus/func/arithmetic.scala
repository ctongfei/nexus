package nexus.func

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._


trait IdF[X, Y] extends DOp1[X, Y] with IdNF[X, Y]

object IdF {
  implicit def any[X](implicit X: Grad[X]): IdF[X, X] = new IdF[X, X] {
    def tag = X
    def backward(dy: X, y: X, x: X) = dy
    def forward(x: X) = x
  }
}

trait IdNF[X, Y] extends Op1[X, Y] {
  def name = "Id"
}

object IdNF {
  implicit def any[X]: IdNF[X, X] = new IdNF[X, X] {
    def forward(x: X) = x
  }
}

@implicitNotFound("Cannot apply Add to ${X1} and ${X2}.")
trait AddF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Add"
}

object AddF {

  implicit def scalar[R](implicit ops: IsReal[R]): AddF[R, R, R] =
    new AddF[R, R, R] {
      import ops._
      def tag = ops
      def forward(x1: R, x2: R) = add(x1, x2)
      def backward1(dy: R, y: R, x1: R, x2: R) = dy
      def backward2(dy: R, y: R, x1: R, x2: R) = dy
    }

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]): AddF[T[A], T[A], T[A]] =
    new AddF[T[A], T[A], T[A]] {
      def tag = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 + x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy
    }

}

@implicitNotFound("Cannot apply Sub to ${X1} and ${X2}.")
trait SubF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Sub"
}

object SubF {

  implicit def scalar[R](implicit R: IsReal[R]): SubF[R, R, R] =
    new SubF[R, R, R] {
      def tag = R
      def backward1(dy: R, y: R, x1: R, x2: R) = dy
      def backward2(dy: R, y: R, x1: R, x2: R) = -dy
      def forward(x1: R, x2: R) = x1 - x2
    }

  implicit def tensor[T[A <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new SubF[T[A], T[A], T[A]] {
    def tag = T.ground[A]
    def forward(x1: T[A], x2: T[A]): T[A] = x1 - x2
    def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]): T[A] = dy
    def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]): T[A] = -dy
  }

}

@implicitNotFound("Cannot apply Neg to ${X}.")
trait NegF[X, Y] extends DOp1[X, Y] {
  def name = "Neg"
}

object NegF {

  implicit def scalar[R](implicit R: IsReal[R]): NegF[R, R] =
    new NegF[R, R] {
      def tag = R
      def backward(dy: R, y: R, x: R) = -dy
      def forward(x: R) = -x
    }

  implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]): NegF[T[A], T[A]] =
    new NegF[T[A], T[A]] {
      def tag = T.ground[A]
      def forward(x: T[A]) = -x
      def backward(dy: T[A], y: T[A], x: T[A]) = -dy
    }

}

@implicitNotFound("Cannot apply Mul to ${X1} and ${X2}.")
trait MulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Mul"
}

object MulF {

  implicit def scalar[R](implicit R: IsReal[R]): MulF[R, R, R] =
    new MulF[R, R, R] {
      def tag = R
      def backward1(dy: R, y: R, x1: R, x2: R) = dy * x2
      def backward2(dy: R, y: R, x1: R, x2: R) = dy * x1
      def forward(x1: R, x2: R) = x1 * x2
    }

}

@implicitNotFound("Cannot apply Mul.Elementwise to ${X1} and ${X2}.")
trait EMulF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Mul.Elementwise"
}

object EMulF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: IsTypedRealTensor[T, D]): EMulF[T[A], T[A], T[A]] =
    new EMulF[T[A], T[A], T[A]] {
      def tag = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |*| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |*| x1
    }

}

@implicitNotFound("Cannot apply Div to ${X1} and ${X2}.")
trait DivF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Div"
}

object DivF {

  implicit def scalar[R](implicit R: IsReal[R]): DivF[R, R, R] =
    new DivF[R, R, R] {
      def tag = R
      def backward1(dy: R, y: R, x1: R, x2: R) = dy / x2
      def backward2(dy: R, y: R, x1: R, x2: R) = -dy * x1 / R.sqr(x2)
      def forward(x1: R, x2: R) = x1 / x2
    }

}

@implicitNotFound("Cannot apply Div.Elementwise to ${X1} and ${X2}.")
trait EDivF[X1, X2, Y] extends DOp2[X1, X2, Y] {
  def name = "Div.Elementwise"
}

object EDivF {

  implicit def tensor[T[_ <: $$], D, A <: $$](implicit T: IsTypedRealTensor[T, D]): EDivF[T[A], T[A], T[A]] =
    new EDivF[T[A], T[A], T[A]] {
      import T._
      def tag = T.ground[A]
      def forward(x1: T[A], x2: T[A]) = x1 |/| x2
      def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = dy |/| x2
      def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = -dy |*| x1 |/| eSqr(x2)
    }

}

@implicitNotFound("Cannot apply Inv to ${X}.")
trait InvF[X, Y] extends DOp1[X, Y] {
  def name = "Inv"
}

object InvF {

  implicit def scalar[R](implicit R: IsReal[R]): InvF[R, R] =
    new InvF[R, R] {
      def tag = R
      def backward(dy: R, y: R, x: R) = -dy * R.sqr(y)
      def forward(x: R) = R.inv(x)
    }

}

@implicitNotFound("Cannot apply Inv.Elementwise to ${X}.")
trait EInvF[X, Y] extends DOp1[X, Y] {
  def name = "Inv.Elementwise"
}

object EInvF {

  implicit def tensor[T[_ <: $$], A <: $$, R](implicit T: IsTypedRealTensor[T, R]): EInvF[T[A], T[A]] =
    new EInvF[T[A], T[A]] {
      def tag = T.ground[A]
      def backward(dy: T[A], y: T[A], x: T[A]) = -dy |*| T.eSqr(y)
      def forward(x: T[A]) = T.eInv(x)
    }

}
