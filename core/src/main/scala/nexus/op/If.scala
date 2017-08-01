package nexus.op

import nexus._

/**
 * @author Tongfei Chen
 */
object If extends PolyOp3[IfF]

trait IfF[X1, X2, X3, Y] extends Op3[X1, X2, X3, Y] {
  def name = "If"
}


object IfF {
/*
  implicit def IfImpl[X]: IfF[Boolean, X, X, X] = new IfF[Boolean, X, X, X] {
    def forward(x1: Boolean, x2: X, x3: X) = if (x1) x2 else x3
    def backward1(dy: X, y: X, x1: Boolean, x2: X, x3: X) = ???
    def backward2(dy: X, y: X, x1: Boolean, x2: X, x3: X) = if (x1) dy else 0
    def backward3(dy: X, y: X, x1: Boolean, x2: X, x3: X) = if (!x1) dy else 0
  }
*/
}

