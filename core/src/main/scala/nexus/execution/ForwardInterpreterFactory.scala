package nexus.execution

import cats._
import nexus._

/**
 * Any interpreter that interprets a computation graph (i.e., performs forward computation).
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait ForwardInterpreter extends (Expr ~> Id) {

  /** Cached values in this computation instance. */
  def values: ExprMap[Id]

}


trait ForwardInterpreterFactory[F] {

  implicit def factory: ForwardInterpreterFactory[F] = this

  def given(inputs: Assignment*): F

}
