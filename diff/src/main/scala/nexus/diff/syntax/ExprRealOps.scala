package nexus.diff.syntax

import nexus.diff._
import nexus.diff.ops._
import nexus._

/**
 * @author Tongfei Chen
 */
trait BoxRealOpsMixin {

  implicit class BoxRealOps[D[_], R](val x: D[R])(implicit F: Algebra[D], R: IsReal[R]) {

    def +(y: D[R]): D[R] = Add(x, y)
    def unary_- : D[R] = Neg(x)
    def -(y: D[R]): D[R] = Sub(x, y)
    def *(y: D[R]): D[R] = Mul(x, y)
    def /(y: D[R]): D[R] = Div(x, y)

  }

}
