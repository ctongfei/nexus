package nexus.op

import nexus._

/**
 * Identity function for any expression, but stops gradient backpropagation.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object StopGrad extends PolyOp1[StopGradF]

trait StopGradF[X, Y] extends Op1[X, Y] {
  def name = "StopGrad"
}

object StopGradF {

  implicit def any[X]: StopGradF[X, X] = new StopGradF[X, X] {
    def forward(x: X) = x
    def backward(dy: X, y: X, x: X) = dy
    override def differentiableWrtX = false // stops gradient propagation!
  }

}
