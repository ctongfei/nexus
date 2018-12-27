package nexus.diff.execution

import cats._
import nexus.diff._
import nexus._

/**
 * @author Tongfei Chen
 */
trait Backpropagation[F <: ForwardInterpreter] {

  def compute[R](e: Symbolic[R])(implicit R: IsReal[R], forward: F): SymbolicMap[Id]

}
