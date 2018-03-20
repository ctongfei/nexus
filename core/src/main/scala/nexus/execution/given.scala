package nexus.execution

import cats._
import nexus._

/**
 * User-friendly syntax for creating a forward computation context.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object given {

  /**
   * User-friendly syntax for creating a forward computation context.
   * @param as A list of assignments
   */
  def apply(as: Assignment*) = new {

    def apply(op: SimpleForward => Any) = {
      implicit val comp = SimpleForward.given(as: _*)
      op(comp)
    }

    def using[F <: ForwardInterpreter](factory: ForwardInterpreterFactory[F])(op: F => Any) = {
      implicit val comp = factory.given(as: _*)
      op(comp)
    }

  }

}
