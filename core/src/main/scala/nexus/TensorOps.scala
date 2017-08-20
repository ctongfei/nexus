package nexus

import nexus.impl._
import nexus.typelevel._
import shapeless._

import scala.language.higherKinds

/**
 * @author Tongfei Chen
 */
trait TensorOpsMixin {

  implicit class TensorOps[T[_ <: $$], D, A <: $$](val a: T[A])(implicit ops: TypedRealTensorOps[T, D]) {

    import ops._

    def +(b: T[A]): T[A] = add(a, b)
    def -(b: T[A]): T[A] = sub(a, b)
    def |*|(b: T[A]): T[A] = eMul(a, b)
    def |/|(b: T[A]): T[A] = eDiv(a, b)

    def :*(u: D): T[A] = scale(a, u)
    def :*(u: Float): T[A] = scale(a, D.fromFloat(u))
    def :*(u: Double): T[A] = scale(a, D.fromDouble(u))
    def :*(u: T[$])(implicit di: DummyImplicit): T[A] = scale(a, unwrapScalar(u))

    def :/(u: D): T[A] = scale(a, D.reciprocal(u))
    def :/(u: Float): T[A] = scale(a, D.fromFloat(1f / u))
    def :/(u: Double): T[A] = scale(a, D.fromDouble(1d / u))
    def :/(u: T[$])(implicit di: DummyImplicit): T[A] = scale(a, unwrapScalar(u))

    def ⋅(b: T[A]): T[$] = dot(a, b)

    def ⋈[B <: $$, C <: $$](b: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] = tMul(a, b)

    def unary_- : T[A] = neg(a)


  }

}
