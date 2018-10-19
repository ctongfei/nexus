package nexus.execution

import cats._
import nexus._
import nexus.tensor._

/**
 * @author Tongfei Chen
 */
trait Backpropagation[F <: ForwardInterpreter] {

  def compute[R](e: Symbolic[R])(implicit R: IsReal[R], forward: F): SymbolicMap[Id]

}
