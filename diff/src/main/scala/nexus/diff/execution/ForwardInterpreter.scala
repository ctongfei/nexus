package nexus.diff.execution

import cats._
import nexus.diff._

/**
 * Any interpreter that interprets a computation graph (i.e., performs forward computation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait ForwardInterpreter extends (Symbolic ~> Id) {

  /** Cached values in this computation instance. */
  def values: BoxMap[Symbolic, Id]

}

trait ForwardInterpreterFactory[F <: ForwardInterpreter] {

  implicit def factory: ForwardInterpreterFactory[F] = this

  /**
   * Creates a forward interpreter given the list of assignments.
   * @param inputs List of assignments
   * @return A new forward interpreter instance
   */
  def given(inputs: Assignment*): F

}
