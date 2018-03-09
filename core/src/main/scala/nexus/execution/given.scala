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
   */
  def apply(as: Assignment*) = new {

    def apply(op: SimpleForward => Any) = {
      implicit val comp = SimpleForward.given(as: _*)
      op(comp)
    }

    def using[F <: (Expr ~> Id)](factory: ForwardInterpreterFactory[F])(op: F => Any) = {
      implicit val comp = factory.given(as: _*)
      op(comp)
    }

  }


}
