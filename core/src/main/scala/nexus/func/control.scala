package nexus.func

import nexus._
import nexus.algebra._
import nexus.exception._

@implicitNotFound("Cannot apply If to ${C}, ${T} and ${F}.")
trait IfF[C, T, F, R] extends DOp3[C, T, F, R] {
  def name = "If"
}

object IfF {

  implicit def differentiable[X](implicit X: Grad[X]): IfF[Boolean, X, X, X] = new IfF[Boolean, X, X, X] {
    override def tag = X
    def forward(c: Boolean, t: X, f: X) = if (c) t else f
    def backward1(dy: X, y: X, c: Boolean, t: X, f: X) = throw new OperatorNotDifferentiableException(name, 1)
    def backward2(dy: X, y: X, c: Boolean, t: X, f: X) = if (c) dy else X.zeroBy(t)
    def backward3(dy: X, y: X, c: Boolean, t: X, f: X) = if (!c) dy else X.zeroBy(f)
  }

}
