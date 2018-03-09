package nexus

import nexus.execution._
import nexus.impl.jvm._
import nexus.layer.recurrent._
import nexus.op._
import nexus.syntax._
import nexus.optimizer._

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

  val q = Param(FloatTensor.fill(0f, (), Array(1)), name = "q")

  val z = q |> Dropout(0.3)

  val t = Exp(p)

  val l = p |> Sqr |> Add.Curried1(1f)


  val sgd = new GradientDescentOptimizer(0.01)

  for (i <- 0 until 1000) {
    implicit val forward = SimpleForward.given()
    println(s"$i: p = ${p.value}; Value = ${l.value}")
    val gradients = Backward.compute(l)
    sgd.update(gradients)
  }

}
