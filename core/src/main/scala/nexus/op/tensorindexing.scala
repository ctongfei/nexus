package nexus.op

import nexus._
import nexus.func._

/**
 * Wraps a scalar to a 0-dim tensor.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
object WrapScalar extends PolyDOp1[WrapScalarF]


/**
 * Unwraps a 0-dim tensor to a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object UnwrapScalar extends PolyDOp1[UnwrapScalarF]


/**
 * Transforms each
 * @author Tongfei Chen
 */
case class OneHot[U](parameter: (U, Int)) extends ParaPolyOp1[(U, Int), OneHotF]
