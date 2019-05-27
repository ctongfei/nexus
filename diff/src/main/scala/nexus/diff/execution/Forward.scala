package nexus.diff.execution

import cats._
import nexus.diff._
import nexus.diff.collection._

/**
 * Any interpreter that interprets a computation graph (i.e., performs forward computation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Forward[F[_]] extends (F ~> Id) {

  /** Cached values in this computation instance. */
  def values: BoxMap[F, Id]

  def backward: Backward[F]

}
