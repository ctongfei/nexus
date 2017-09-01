package nexus.op

import nexus._
import nexus.algebra._

/**
 * @author Tongfei Chen
 */
object Id extends PolyFOp1[IdF, IdNF]

trait IdF[X, Y] extends DOp1[X, Y] with IdNF[X, Y]

object IdF {
  implicit def any[X](implicit X: GradOps[X]): IdF[X, X] = new IdF[X, X] {
    def gradOps = X
    def backward(dy: X, y: X, x: X) = dy
    def forward(x: X) = x
  }
}

trait IdNF[X, Y] extends Op1[X, Y] {
  def name = "Id"
}

object IdNF {
  implicit def any[X]: IdNF[X, X] = new IdNF[X, X] {
    def forward(x: X) = x
  }
}
