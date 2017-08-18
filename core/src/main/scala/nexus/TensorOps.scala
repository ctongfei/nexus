package nexus

import nexus.impl._
import nexus.typelevel._
import shapeless._

import scala.language.higherKinds

/**
 * @author Tongfei Chen
 */
trait TensorOpsMixin {

  implicit class TensorOps[T[_ <: $$], D, A <: $$](val a: T[A])(implicit env: TypedMathOps[T, D]) {

    import env._

    def +(b: T[A]): T[A] = add(a, b)
    def -(b: T[A]): T[A] = sub(a, b)
    def |*|(b: T[A]): T[A] = eMul(a, b)
    def |/|(b: T[A]): T[A] = eDiv(a, b)

    def :*(u: D): T[A] = scale(a, u)
    def :*(u: Float): T[A] = scale(a, fromFloat(u))
    def :*(u: Double): T[A] = scale(a, fromDouble(u))
    def :*(u: T[$])(implicit di: DummyImplicit): T[A] = scale(a, getScalar(u))

    def :/(u: D): T[A] = scale(a, D.reciprocal(u))
    def :/(u: Float): T[A] = scale(a, fromFloat(1f / u))
    def :/(u: Double): T[A] = scale(a, fromDouble(1d / u))
    def :/(u: T[$])(implicit di: DummyImplicit): T[A] = scale(a, getScalar(u))

    def ⋅(b: T[A]): T[$] = dot(a, b)

    def ⋈[B <: $$, C <: $$](b: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] = tMul(a, b)

    def unary_- : T[A] = neg(a)


  }

}
