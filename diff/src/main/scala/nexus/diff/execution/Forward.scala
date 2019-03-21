package nexus.diff.execution

import cats._
import nexus.diff._
import nexus.diff.collection._

/**
 * Any interpreter that interprets a computation graph (i.e., performs forward computation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Forward[D[_]] extends (D ~> Id) {

  /** Cached values in this computation instance. */
  def values: BoxMap[D, Id]

  def backward: Backward[D]

}
