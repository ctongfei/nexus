package nexus.ops

import nexus.diff._
import nexus.diff.execution._
import nexus.diff.ops._
import nexus.diff.optimizers._
import nexus.instances._

/**
 * @author Tongfei Chen
 */
object TupleTest extends App {

  class Axis; val Axis = new Axis

  val ba = Const(false)
  val bb = Const(true)



  val a = Param(2f, "a")
  val b = Param(3f, "b")

  val aa = (a, b) |> Add
  val ab = (a, b) |> AsTuple2
  val a2 = ab |> Tuple2First |> Sqr
  val b2 = ab |> Tuple2Second |> Sqr
  val sum = Add(a, b)

  val x = Input[Seq[Float]]()

  val sgd = new GradientDescentOptimizer(0.3)

  for (i <- 0 until 100) {
    println(s"Iteration $i: a = ${a.value}; b = ${b.value}")
    implicit val forward = SimpleForward.given()
    val lv = sum.value
    val gradients = Backward.compute(sum)
    sgd.update(gradients)
  }


}
