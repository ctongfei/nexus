package nexus.op.base

import nexus.algebra._
import nexus.{$, $$, ::, DOp1, DOp2, PolyDOp1, PolyDOp2}

import scala.annotation._

/**
 * @author Tongfei Chen
 */
abstract class VaVaPolyDOp1 extends PolyDOp1 { self =>

  def name: String

  def forward[T[_ <: $$], R, A](x: T[A::$])(implicit T: IsTypedRealTensor[T, R]): T[A::$]

  def backward[T[_ <: $$], R, A](dy: T[A::$], y: T[A::$], x: T[A::$])(implicit T: IsTypedRealTensor[T, R]): T[A::$]

  @implicitNotFound("Cannot apply this operator to ${X}.")
  trait F[X, Y] extends DOp1[X, Y]

  object F {
    /**
     * Synthesizes an operator for tensor type [[T]] with element type [[E]] and axes [[A]].
     */
    implicit def synthesize[T[_ <: $$], E, A](implicit T: IsTypedRealTensor[T, E]) = new OpImpl[T, E, A]
  }

  class OpImpl[T[_ <: $$], E, A](implicit T: IsTypedRealTensor[T, E]) extends F[T[A::$], T[A::$]] {
    def name = self.name
    def tag = T.ground[A::$]
    def forward(x: T[A::$]) = self.forward(x)
    def backward(dy: T[A::$], y: T[A::$], x: T[A::$]) = self.backward(dy, y, x)
  }

}

abstract class VaVaSPolyDOp2 extends PolyDOp2 { self =>

  def name: String

  def forward[T[_ <: $$], R, A](x1: T[A::$], x2: T[A::$])(implicit T: IsTypedRealTensor[T, R]): R

  def backward1[T[_ <: $$], R, A](dy: R, y: R, x1: T[A::$], x2: T[A::$])(implicit T: IsTypedRealTensor[T, R]): T[A::$]
  def backward2[T[_ <: $$], R, A](dy: R, y: R, x1: T[A::$], x2: T[A::$])(implicit T: IsTypedRealTensor[T, R]): T[A::$]

  @implicitNotFound("Cannot apply this operator to ${X1} and ${X2}.")
  trait F[X1, X2, Y] extends DOp2[X1, X2, Y]

  object F {
    implicit def synthesize[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]) = new OpImpl[T, R, A]
  }

  class OpImpl[T[_ <: $$], R, A](implicit T: IsTypedRealTensor[T, R]) extends F[T[A::$], T[A::$], R] {
    def name = self.name
    def tag = T.R
    def forward(x1: T[A::$], x2: T[A::$]) = self.forward(x1, x2)
    def backward1(dy: R, y: R, x1: T[A::$], x2: T[A::$]) = self.backward1(dy, y, x1, x2)
    def backward2(dy: R, y: R, x1: T[A::$], x2: T[A::$]) = self.backward2(dy, y, x1, x2)
  }
}

abstract class TaSPolyDOp1 extends PolyDOp1 { self =>

  def name: String
  def forward[T[_ <: $$], R, As <: $$](x: T[As])(implicit T: IsTypedRealTensor[T, R]): R
  def backward[T[_ <: $$], R, As <: $$](dy: R, y: R, x: T[As])(implicit T: IsTypedRealTensor[T, R]): T[As]

  @implicitNotFound("Cannot apply this operator to ${X}.")
  trait F[X, Y] extends DOp1[X, Y]

  object F {
    implicit def synthesize[T[_ <: $$], R, As <: $$](implicit T: IsTypedRealTensor[T, R]): F[T[As], R] = new F[T[As], R] {
      def name = self.name
      def tag = T.R
      def forward(x: T[As]) = self.forward(x)
      def backward(dy: R, y: R, x: T[As]) = self.backward(dy, y, x)
    }
  }

}

abstract class TaTaSPolyDOp2 extends PolyDOp2 { self =>

  def name: String

  def forward[T[_ <: $$], R, As <: $$](x1: T[As], x2: T[As])(implicit T: IsTypedRealTensor[T, R]): R

  def backward1[T[_ <: $$], R, As <: $$](dy: R, y: R, x1: T[As], x2: T[As])(implicit T: IsTypedRealTensor[T, R]): T[As]
  def backward2[T[_ <: $$], R, As <: $$](dy: R, y: R, x1: T[As], x2: T[As])(implicit T: IsTypedRealTensor[T, R]): T[As]

  @implicitNotFound("Cannot apply this operator to ${X1} and ${X2}.")
  trait F[X1, X2, Y] extends DOp2[X1, X2, Y]

  object F {
    implicit def synthesize[T[_ <: $$], R, As <: $$](implicit T: IsTypedRealTensor[T, R]) = new OpImpl[T, R, As]
  }

  class OpImpl[T[_ <: $$], R, As <: $$](implicit T: IsTypedRealTensor[T, R]) extends F[T[As], T[As], R] {
    def name = self.name
    def tag = T.R
    def forward(x1: T[As], x2: T[As]) = self.forward(x1, x2)
    def backward1(dy: R, y: R, x1: T[As], x2: T[As]) = self.backward1(dy, y, x1, x2)
    def backward2(dy: R, y: R, x1: T[As], x2: T[As]) = self.backward2(dy, y, x1, x2)
  }
}
