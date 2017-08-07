package nexus.op

import nexus._

/**
 * Identity function for any object.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Id extends PolyOp1[IdF]

trait IdF[X, Y] extends Op1[X, Y] {
  def name = "Id"
}

object IdF {

  implicit def any[X]: IdF[X, X] = new IdF[X, X] {
    def forward(x: X) = x
    def backward(dy: X, y: X, x: X) = dy
  }

}
