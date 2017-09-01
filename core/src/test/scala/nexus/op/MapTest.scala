package nexus.op

import nexus._
import nexus.impl.cpu._
import nexus.exec._
import nexus.impl.cpu.DenseTensor
import nexus.op._

/**
 * @author Tongfei Chen
 */
object MapTest extends App {

  object f extends DOp1[Float, Float] {
    def gradOps = ???
    def name = "Sin"
    def forward(x: Float) = math.sin(x).toFloat
    def backward(dy: Float, y: Float, x: Float) = dy * math.cos(x).toFloat
  }

  val a = Input[DenseTensor[$]]()

  val b = a |> Map(f)

}
