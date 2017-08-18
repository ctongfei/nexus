package nexus.impl

import nexus._
import nexus.typelevel._
import nexus.util._

/**
 * Environment for running typed tensors.
 * @author Tongfei Chen
 */
trait TypedMathOps[T[_ <: $$], D] extends Typing[T] { self =>

  def H: MathOps[H, D]
  def D: Field[D]

  def newTensor[A <: $$](axes: A, shape: Array[Int]): T[A]

  def newZeroBy[A <: $$](x: T[A]): T[A]

  def newGaussianTensor[A <: $$](μ: Double, σ2: Double, axes: A, shape: Array[Int]): T[A]

  def scalar(x: D) = typeWith(H.scalar(x), $)
  def getScalar(x: T[_]) = H.getScalar(untype(x))

  def fromDouble(d: Double): D
  def fromFloat(f: Float): D

  def add[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.add(untype(x1), untype(x2)), typeOf(x1))

  def addI[A <: $$](x1: T[A], x2: T[A]) =
    H.addI(untype(x1), untype(x2))

  def addS[A <: $$](x: T[A], u: D) =
    typeWith(H.addS(untype(x), u), typeOf(x))

  def neg[A <: $$](x: T[A]) =
    typeWith(H.neg(untype(x)), typeOf(x))

  def sub[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.sub(untype(x1), untype(x2)), typeOf(x1))

  def subS[A <: $$](x: T[A], u: D) =
    typeWith(H.subS(untype(x), u), typeOf(x))

  def eMul[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.eMul(untype(x1), untype(x2)), typeOf(x1))

  def eDiv[A <: $$](x1: T[A], x2: T[A]): T[A] =
    typeWith(H.eDiv(untype(x1), untype(x2)), typeOf(x1))
  
  def scale[A <: $$](x: T[A], u: D): T[A] = 
    typeWith(H.scale(untype(x), u), typeOf(x))
  
  def inv[A <: $$](x: T[A]): T[A] = 
    typeWith(H.inv(untype(x)), typeOf(x))

  def sqr[A <: $$](x: T[A]): T[A] = 
    typeWith(H.sqr(untype(x)), typeOf(x))

  def sqrt[A <: $$](x: T[A]): T[A] = 
    typeWith(H.sqrt(untype(x)), typeOf(x))

  def transpose[A, B](x: T[A::B::$]): T[B::A::$] = 
    typeWith(H.transpose(untype(x)), AxesUtils.swap(typeOf(x)))

  def log[A <: $$](x: T[A]): T[A] = 
    typeWith(H.log(untype(x)), typeOf(x))

  def exp[A <: $$](x: T[A]): T[A] = 
    typeWith(H.exp(untype(x)), typeOf(x))

  def sin[A <: $$](x: T[A]): T[A] = typeWith(H.sin(untype(x)), typeOf(x))

  def cos[A <: $$](x: T[A]): T[A] = typeWith(H.cos(untype(x)), typeOf(x))

  def tan[A <: $$](x: T[A]): T[A] = typeWith(H.tan(untype(x)), typeOf(x))

  def sigmoid[A <: $$](x: T[A]): T[A] = typeWith(H.sigmoid(untype(x)), typeOf(x))

  def relu[A <: $$](x: T[A]): T[A] = typeWith(H.reLU(untype(x)), typeOf(x))

  def pos[A <: $$](x: T[A]): T[A] = typeWith(H.isPos(untype(x)), typeOf(x))

  def sum[A <: $$](x: T[A]): T[$] = typeWith(H.sum(untype(x)), $)

  def mmMul[A, B, C](x: T[A::B::$], y: T[B::C::$]): T[A::C::$] =
    typeWith(H.mmMul(untype(x), untype(y)), AxesUtils.mmMul(typeOf(x), typeOf(y)))

  def mvMul[A, B](x: T[A::B::$], y: T[B::$]): T[A::$] =
    typeWith(H.mvMul(untype(x), untype(y)), typeOf(x).head::$)

  def vvMul[A, B](x: T[A::$], y: T[B::$]): T[A::B::$] = typeWith(H.vvMul(untype(x), untype(y)), typeOf(x).head::typeOf(y).head::$)

  def dot[A <: $$](x: T[A], y: T[A]): T[$] = typeWith(H.dot(untype(x), untype(y)), $)

  def tMul[A <: $$, B <: $$, C <: $$](x: T[A], y: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] =
    typeWith(H.tMul(untype(x), untype(y), sd.matchedIndices), sd(typeOf(x), typeOf(y)))


  def map[A <: $$](x: T[A])(f: D => D): T[A] =
    typeWith(H.map(untype(x))(f), typeOf(x))

  def map2[A <: $$](x1: T[A], x2: T[A])(f: (D, D) => D): T[A] =
    typeWith(H.map2(untype(x1), untype(x2))(f), typeOf(x1))

  def map3[A <: $$](x1: T[A], x2: T[A], x3: T[A])(f: (D, D, D) => D): T[A] =
    typeWith(H.map3(untype(x1), untype(x2), untype(x3))(f), typeOf(x1))


  def ground[A <: $$]: GradOps[T[A]] = new GradOps[T[A]] {

    def zeroBy(x: T[A]) = self.newZeroBy(x)
    def add(x1: T[A], x2: T[A]) = self.add(x1, x2)
    def addI(x1: T[A], x2: T[A]) = self.addI(x1, x2)
    def sub(x1: T[A], x2: T[A]) = self.sub(x1, x2)
    def neg(x: T[A]) = self.neg(x)
    def eMul(x1: T[A], x2: T[A]) = self.eMul(x1, x2)
    def eDiv(x1: T[A], x2: T[A]) = self.eDiv(x1, x2)
    def scale(x: T[A], k: Double) = self.scale(x, fromDouble(k))
  }

}
