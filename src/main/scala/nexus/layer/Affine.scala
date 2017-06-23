package nexus.layer

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
object Affine {

  def apply[UT[_], T[D, _] <: UT[D], D, X, Y]
  (x: (X, Int), y: (Y, Int))
  (implicit env: Env[T, D]): Layer[T[D, X :: HNil], T[D, Y :: HNil]] = new Layer[T[D, X :: HNil], T[D, Y :: HNil]] {

    val W = Param()

    def apply(x: Expr[T[D, X :: HNil]]]) = ???
    def parameters = ???
  }

}