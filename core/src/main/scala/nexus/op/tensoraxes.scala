package nexus.op

import nexus._
import nexus.func._

/**
 * Renaming an axis in any tensor.
 *
 * @example {{{ Rename(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Rename[U, V](parameter: (U, V)) extends ParaPolyDOp1[(U, V), RenameF]

