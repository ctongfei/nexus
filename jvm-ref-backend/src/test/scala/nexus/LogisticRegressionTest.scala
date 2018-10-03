package nexus

import nexus.ops._
import nexus.optimizer._
import nexus.execution._
import nexus.jvm._
import nexus.modules._

/**
 * @author Tongfei Chen
 */
object LogisticRegressionTest extends App {

  class Batch extends Dim; val Batch = new Batch
  class In extends Dim;    val In = new In
  class Out extends Dim;   val Out = new Out

  val X = FloatTensor.fromNestedArray(Batch, In)(Array(
    Array(3f, 4f),
    Array(5f, 1f),
    Array(-3f, -2f),
    Array(1f, 3f),
    Array(0f, -1f)
  ))

  val Y = FloatTensor.fromNestedArray(Batch, Out)(Array(
    1, 1, 0, 1, 0
  ).map(i => if (i == 0) Array(1f, 0f) else Array(0f, 1f)))

  val xs = X unstackAlong Batch
  val ys = Y unstackAlong Batch

  val x = Input[FloatTensor[In]]()
  val y = Input[FloatTensor[Out]]()

  val Layer = Affine(In -> 2, Out -> 2)

  val z = x |> Softmax

  val output = x |> Layer |> Softmax
  val loss = CrossEntropy(y, output)

  val sgd = new AdamOptimizer(0.01)

  for (i <- 0 until 100) {
    for ((xv, yv) <- xs zip ys) {
      implicit val forward = SimpleForward.given(x := xv, y := yv)
      val lossValue = loss.value
      val grads = Backward.compute(loss)
      println(s"Iteration $i: loss = $lossValue")
      sgd.update(grads)
    }
  }
  val bp = 0
}

