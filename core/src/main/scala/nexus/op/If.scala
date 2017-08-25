package nexus.op

import nexus._
import nexus.exception._
import nexus.algebra._

/**
 * @author Tongfei Chen
 */
object If extends PolyDOp3[IfF]

trait IfF[C, T, F, R] extends DOp3[C, T, F, R] {
  def name = "If"
}

object IfF {

  implicit def differentiable[X](implicit XG: GradOps[X]): IfF[Boolean, X, X, X] = new IfF[Boolean, X, X, X] {
    override def gradOps = XG
    def forward(c: Boolean, t: X, f: X) = if (c) t else f
    def backward1(dy: X, y: X, c: Boolean, t: X, f: X) = throw new OperatorNotDifferentiableException(name, 1)
    def backward2(dy: X, y: X, c: Boolean, t: X, f: X) = if (c) dy else XG.zeroBy(t)
    def backward3(dy: X, y: X, c: Boolean, t: X, f: X) = if (!c) dy else XG.zeroBy(f)
  }

}
