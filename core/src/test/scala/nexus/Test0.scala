package nexus

import nexus.exec._
import nexus.impl.jvm.DenseTensor
import nexus.op._
import nexus.syntax._
import nexus.optimizer._

/**
 * Vanilla test to see if p converges to 0.
 * @author Tongfei Chen
 */
object Test0 extends App {

  val p = Param(3.0f, name = "p")

  val q = Param(DenseTensor.fill(0f, ()::$, Array(1)), name = "q")

  val t = Exp(p)

  val l = Abs(p)

  val sgd = new BackstitchOptimizer(0.1)

  for (i <- 0 until 100) {
    println(s"Iteration $i: p = ${p.value}")
    val (lv, values) = Forward.compute(l)()
    val gradients = Backward.compute(l, values)
    sgd.update(gradients)
  }

}
