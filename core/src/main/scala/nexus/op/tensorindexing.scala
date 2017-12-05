package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import scala.annotation._

/**
 * Wraps a scalar to a 0-dim tensor.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object WrapScalar extends PolyDOp1 {
  @implicitNotFound("Cannot apply WrapScalar to ${X}.")
  trait DOp[X, Y] extends DOp1[X, Y] {
  def name = "WrapScalar"
}

  object DOp {

    implicit def scalar[T[_ <: $$], R](implicit T: IsTypedRealTensor[T, R]): DOp[R, T[$]] =
      new DOp[R, T[$]] {
        def tag = T.ground[$]
        def forward(x: R) = T.wrapScalar(x)
        def backward(dy: T[$], y: T[$], x: R) = T.unwrapScalar(dy)
      }

  }
}

/**
 * Unwraps a 0-dim tensor to a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object UnwrapScalar extends PolyDOp1 {

  @implicitNotFound("Cannot apply UnwrapScalar to ${X}.")
  trait DOp[X, Y] extends DOp1[X, Y] {
    def name = "UnwrapScalar"
  }

  object DOp {

    implicit def scalar[T[_ <: $$], R](implicit T: IsTypedRealTensor[T, R]): DOp[T[$], R] =
      new DOp[T[$], R] {
        def tag = T.R
        def forward(x: T[$]) = T.unwrapScalar(x)
        def backward(dy: R, y: R, x: T[$]) = T.wrapScalar(dy)
      }

  }

}


/**
 * Transforms each
 * @author Tongfei Chen
 */
case class OneHot[U](parameter: (U, Int)) extends ParaPolyOp1[(U, Int)]

/**
 * Slices a tensor along a specific axis.
 * @example {{{ SliceAlong(Width -> 3) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class SliceAlong[U](parameter: (U, Int)) extends ParaPolyDOp1[(U, Int)]
