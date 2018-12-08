package nexus.diff.execution

import cats._
import nexus.diff._

/**
 * User-friendly syntax for creating a forward computation context.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object given {

  /**
   * User-friendly syntax for creating a forward computation context.
   * @param assignments A list of assignments
   */
  def apply(assignments: Assignment*) = new {

    def apply[R](op: SimpleForward => R): R = {
      implicit val comp = SimpleForward.given(assignments: _*)
      op(comp)
    }

    def using[F <: ForwardInterpreter, R](factory: ForwardInterpreterFactory[F])(op: F => R): R = {
      implicit val comp = factory.given(assignments: _*)
      op(comp)
    }

  }

}
