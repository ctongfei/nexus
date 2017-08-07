package nexus

import nexus.exec._
import nexus.cpu._
import nexus.op._
import nexus.op.activation._
import nexus.optimizer._

/**
 * Vanilla test to see if p converges to 0.
 * @author Tongfei Chen
 */
object Test0 extends App {

  val p = Param(DenseTensor.scalar(3.0f), "p")

  val l = EMul(p, p)

  val sgd = StochasticGradientDescentOptimizer(0.4f)

  for (i <- 0 until 20) {
    println(s"Epoch $i: p = ${p.value()}")
    val (lv, values) = Forward.compute(l, Values())
    val gradients = Backward.compute(l, values)
    sgd.update(gradients)
  }

}
