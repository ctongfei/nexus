package nexus

import nexus.algebra.instances._
import nexus.exec._
import nexus.op._
import nexus.syntax._
import nexus.optimizer._

/**
 * Vanilla test to see if p converges to 0.
 * @author Tongfei Chen
 */
object Test0 extends App {

  val p = Param(3.0f, name = "p")

  val l = Mul(p, p)

  val sgd = new GradientDescentOptimizer(0.2)

  for (i <- 0 until 100) {
    println(s"Iteration $i: p = ${p.value}")
    val (lv, values) = Forward.compute(l)()
    val gradients = Backward.compute(l, values)
    sgd.update(gradients)
  }

}
