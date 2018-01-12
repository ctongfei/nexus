package nexus.algebra.syntax

import nexus.algebra._
import nexus.algebra.typelevel._

/**
 * @author Tongfei Chen
 */
trait RealTensorSyntax {

  implicit class RealTensorOps[T[_], R, A](val a: T[A])(implicit T: IsRealTensorK[T, R]) {

    import T._

    def +(b: T[A]): T[A] = add(a, b)
    def -(b: T[A]): T[A] = sub(a, b)

    def unary_- : T[A] = neg(a)

    def |*|(b: T[A]): T[A] = eMul(a, b)
    def |/|(b: T[A]): T[A] = eDiv(a, b)

    def :*(u: R): T[A] = scale(a, u)
    def :*(u: Float): T[A] = scale(a, R.fromFloat(u))
    def :*(u: Double): T[A] = scale(a, R.fromDouble(u))
    def :*(u: T[Unit])(implicit di: DummyImplicit): T[A] = scale(a, unwrapScalar(u))

    def :/(u: R): T[A] = scale(a, R.reciprocal(u))
    def :/(u: Float): T[A] = scale(a, R.fromFloat(1f / u))
    def :/(u: Double): T[A] = scale(a, R.fromDouble(1d / u))
    def :/(u: T[Unit])(implicit di: DummyImplicit): T[A] = scale(a, unwrapScalar(u))

    def ⋅(b: T[A]): R = dot(a, b)

    def ⋈[B, C](b: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] = contract(a, b)

  }
}
