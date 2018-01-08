package nexus.algebra.syntax

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
trait RealSyntax {

  implicit class RealOps[X](val x: X)(implicit X: IsReal[X]) {

    import X._

    def *(y: X) = mul(x, y)
    def /(y: X) = div(x, y)

    def *(y: Double) = mul(x, fromDouble(y))
    def /(y: Double) = div(x, fromDouble(y))

    def *(y: Float) = mul(x, fromFloat(y))
    def /(y: Float) = div(x, fromFloat(y))
    
  }

}