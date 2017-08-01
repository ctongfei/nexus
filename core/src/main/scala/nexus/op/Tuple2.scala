package nexus.op

import nexus._

/**
 * @author Tongfei Chen
 */
object Tuple2 extends PolyOp2[Tuple2F]

trait Tuple2F[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "Tuple2"
}

object Tuple2F {

  implicit def any2[X1, X2] = new Tuple2F[X1, X2, (X1, X2)] {
    def forward(x1: X1, x2: X2) = (x1, x2)
    def backward1(dy: (X1, X2), y: (X1, X2), x1: X1, x2: X2) = dy._1
    def backward2(dy: (X1, X2), y: (X1, X2), x1: X1, x2: X2) = dy._2
  }

}
