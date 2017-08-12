package nexus.op

import nexus._
import nexus.cpu._
import nexus.exec._
import nexus.op._

/**
 * @author Tongfei Chen
 */
object MapTest extends App {

  object f extends Op1[Float, Float] {
    def name = "Sin"
    def forward(x: Float) = math.sin(x).toFloat
    def backward(dy: Float, y: Float, x: Float) = dy * math.cos(x).toFloat
  }

  val a = Input[DenseTensor[$]]()

  val b = a |> EMap(f)

}
