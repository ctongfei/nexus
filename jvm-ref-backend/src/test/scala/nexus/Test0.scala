package nexus

import nexus.diff._
import nexus.diff.execution._
import nexus.jvm._
import nexus.diff.ops._
import nexus.tensor.instances._
import nexus.diff.syntax._
import nexus.diff.optimizers._

/**
 * Vanilla test to see if p converges to 0.
 * @author Tongfei Chen
 */
class a
class b
class c

object Test0 extends App {

  val a = new a
  val b = new b
  val c = new c

  val p = Param(3.0f, name = "p")

  val q = Param(FloatTensor.fromFlatArray((), Array(), Array(1f)), name = "q")

  val z = q |> Dropout(0.3)

  val t = p |> Exp
  val tt = q |> Exp

  val l = p |> Sqr

  val sgd = new GradientDescentOptimizer(0.1)

  for (i <- 0 until 1000) {
    implicit val forward = SimpleForward.given()
    println(s"$i: p = ${p.value}; \t Value = ${l.value}")
    val gradients = Backward.compute(l)
    sgd.update(gradients)
  }

}
