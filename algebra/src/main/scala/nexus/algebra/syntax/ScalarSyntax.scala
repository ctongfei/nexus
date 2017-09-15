package nexus.algebra.syntax

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
trait ScalarSyntax {

  implicit class ScalarOps[X](val x: X)(implicit X: IsReal[X]) {

    import X._

    def *(y: X) = mul(x, y)
    def /(y: X) = div(x, y)


  }

}