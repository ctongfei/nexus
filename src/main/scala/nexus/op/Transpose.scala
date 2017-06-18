package nexus.op

import nexus._
import shapeless._

/**
 * Transposition of a matrix (2-D tensor).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Transpose extends GenOp1[TransposeF]

trait TransposeF[X, Y] extends Op1[X, Y] {
  def name = "Transpose"
}

object TransposeF {

}
