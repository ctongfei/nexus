package nexus

import nexus.typelevel._
import shapeless._

import scala.language.higherKinds

/**
 * @author Tongfei Chen
 */
trait TensorOpsMixin {

  implicit class TensorOps[T[A <: HList], D, A <: HList](val a: T[A])(implicit env: Env[T, D]) {

    import env._

    def +(b: T[A]): T[A] = add(a, b)
    def -(b: T[A]): T[A] = sub(a, b)
    def |*|(b: T[A]): T[A] = mul(a, b)
    def |/|(b: T[A]): T[A] = div(a, b)

    def :*(u: D): T[A] = scale(a, u)
    def :*(u: Float): T[A] = scale(a, fromFloat(u))
    def :*(u: Double): T[A] = scale(a, fromDouble(u))
    def :*(u: T[$])(implicit di: DummyImplicit): T[A] = scale(a, getScalar(untype(u)))

    def :/(u: D): T[A] = scale(a, invS(u))
    def :/(u: Float): T[A] = scale(a, fromFloat(1f / u))
    def :/(u: Double): T[A] = scale(a, fromDouble(1d / u))
    def :/(u: T[$])(implicit di: DummyImplicit): T[A] = scale(a, getScalar(untype(u)))

    def ⋅(b: T[A]): T[$] = dot(a, b)

    def ⋈[B <: $$, C <: $$](b: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] = tMul(a, b)

    def unary_- : T[A] = neg(a)


  }

}
