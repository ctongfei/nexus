package nexus.exec

import nexus._

/**
 * User-friendly syntax for creating a forward computation context.
 * @author Tongfei Chen
 */
object given {

  def apply(as: Assignment*)(op: Forward => Any) = {
    implicit val comp = Forward.given(as: _*)
    op(comp)
  }

}
