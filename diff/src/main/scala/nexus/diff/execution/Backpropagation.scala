package nexus.diff.execution

import nexus.diff._
import nexus._

/**
 * @author Tongfei Chen
 */
trait Backpropagation[F <: ForwardInterpreter] {

  def compute[R](e: Symbolic[R])(implicit R: IsReal[R], forward: F): BoxMap[Symbolic, Id]

}
