package nexus.op

import nexus._
import nexus.func._

case class CastTo[T](parameter: T) extends ParaPolyDOp1[T, CastToF]
