package nexus.algebra.syntax

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
trait GradSyntax {

  implicit class GradOps[X](val x: X)(implicit ops: Grad[X]) {

    import ops._

    def +(y: X) = add(x, y)
    def +#(y: Double) = addS(x, y)

    def -(y: X) = sub(x, y)
    def unary_- = neg(x)

    def :*(y: Float) = scale(x, y)
    def :*(y: Double) = scale(x, y)

    def :/(y: Float) = scale(x, 1f / y)
    def :/(y: Double) = scale(x, 1d / y)

    def |*|(y: X) = eMul(x, y)
    def |/|(y: X) = eDiv(x, y)

  }

}
