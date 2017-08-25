package nexus.algebra

import nexus._
import nexus.algebra.typelevel.util._
import nexus.algebra.typelevel._

/**
 * Typeclass that describes the algebraic structures on tensors whose elements are real numbers.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TypedRealTensorOps[T[_ <: $$], R] extends TypedTensorOps[T, R] with TensorAxisTyping[T] { self =>

  def H: UntypedRealTensorOps[H, R]
  def R: RealOps[R]

  def zeroBy[A <: $$](x: T[A]): T[A]

  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, axes: A, shape: Array[Int]): T[A]

  def add[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.add(untype(x1), untype(x2)), typeOf(x1))

  def addI[A <: $$](x1: T[A], x2: T[A]) =
    H.addI(untype(x1), untype(x2))

  def addS[A <: $$](x: T[A], u: R) =
    typeWith(H.addS(untype(x), u), typeOf(x))

  def neg[A <: $$](x: T[A]) =
    typeWith(H.neg(untype(x)), typeOf(x))

  def sub[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.sub(untype(x1), untype(x2)), typeOf(x1))

  def subS[A <: $$](x: T[A], u: R) =
    typeWith(H.subS(untype(x), u), typeOf(x))

  def eMul[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.eMul(untype(x1), untype(x2)), typeOf(x1))

  def eDiv[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.eDiv(untype(x1), untype(x2)), typeOf(x1))

  def scale[A <: $$](x: T[A], u: R): T[A] =
    typeWith(H.scale(untype(x), u), typeOf(x))

  def inv[A <: $$](x: T[A]): T[A] =
    typeWith(H.inv(untype(x)), typeOf(x))

  def sqr[A <: $$](x: T[A]): T[A] =
    typeWith(H.sqr(untype(x)), typeOf(x))

  def sqrt[A <: $$](x: T[A]): T[A] =
    typeWith(H.sqrt(untype(x)), typeOf(x))

  def transpose[A, B](x: T[A::B::$]): T[B::A::$] =
    typeWith(H.transpose(untype(x)), AxesUtils.swap(typeOf(x)))

  def log[A <: $$](x: T[A]): T[A] = typeWith(H.log(untype(x)), typeOf(x))
  def exp[A <: $$](x: T[A]): T[A] = typeWith(H.exp(untype(x)), typeOf(x))
  def log1p[A <: $$](x: T[A]): T[A] = typeWith(H.log1p(untype(x)), typeOf(x))
  def expm1[A <: $$](x: T[A]): T[A] = typeWith(H.expm1(untype(x)), typeOf(x))


  def sin[A <: $$](x: T[A]): T[A] = typeWith(H.sin(untype(x)), typeOf(x))

  def cos[A <: $$](x: T[A]): T[A] = typeWith(H.cos(untype(x)), typeOf(x))

  def tan[A <: $$](x: T[A]): T[A] = typeWith(H.tan(untype(x)), typeOf(x))

  def sigmoid[A <: $$](x: T[A]): T[A] = typeWith(H.sigmoid(untype(x)), typeOf(x))

  def relu[A <: $$](x: T[A]): T[A] = typeWith(H.reLU(untype(x)), typeOf(x))

  def pos[A <: $$](x: T[A]): T[A] = typeWith(H.isPos(untype(x)), typeOf(x))

  def sum[A <: $$](x: T[A]): R = H.sum(untype(x))

  def mmMul[A, B, C](x: T[A::B::$], y: T[B::C::$]): T[A::C::$] =
    typeWith(H.mmMul(untype(x), untype(y)), AxesUtils.mmMul(typeOf(x), typeOf(y)))

  def mvMul[A, B](x: T[A::B::$], y: T[B::$]): T[A::$] =
    typeWith(H.mvMul(untype(x), untype(y)), typeOf(x).head::$)

  def vvMul[A, B](x: T[A::$], y: T[B::$]): T[A::B::$] = typeWith(H.vvMul(untype(x), untype(y)), typeOf(x).head::typeOf(y).head::$)

  def dot[A <: $$](x: T[A], y: T[A]): R = H.dot(untype(x), untype(y))

  def tMul[A <: $$, B <: $$, C <: $$](x: T[A], y: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] =
    typeWith(H.tMul(untype(x), untype(y), sd.matchedIndices), sd(typeOf(x), typeOf(y)))


  def ground[A <: $$]: GradOps[T[A]] = new GradOps[T[A]] {
    def mutable = true
    def zeroBy(x: T[A]) = self.zeroBy(x)
    def add(x1: T[A], x2: T[A]) = self.add(x1, x2)
    def addI(x1: T[A], x2: T[A]) = self.addI(x1, x2)

    def addS(x1: T[A], x2: Double) = self.addS(x1, R.fromDouble(x2))
    def sub(x1: T[A], x2: T[A]) = self.sub(x1, x2)
    def neg(x: T[A]) = self.neg(x)
    def eMul(x1: T[A], x2: T[A]) = self.eMul(x1, x2)
    def eDiv(x1: T[A], x2: T[A]) = self.eDiv(x1, x2)
    def scale(x: T[A], k: Double) = self.scale(x, R.fromDouble(k))
    def sqrt(x: T[A]) = self.sqrt(x)
  }

}
