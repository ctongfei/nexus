package nexus

import nexus.cpu._
import nexus.op._
import nexus.optimizer._
import nexus.exec._
import nexus.layer._
import nexus.op.activation._
import nexus.op.loss._

/**
 * @author Tongfei Chen
 */
object LogisticRegressionTest extends App {

  class Batch; val Batch = new Batch
  class In; val In = new In
  class Out; val Out = new Out

  val X = DenseTensor.fromNestedArray(Batch::In::$)(Array(
    Array(3f, 4f),
    Array(5f, 1f),
    Array(-3f, -2f),
    Array(1f, 3f),
    Array(0f, -1f)
  ))

  val Y = DenseTensor.fromNestedArray(Batch::Out::$)(Array(
    1, 1, 0, 1, 0
  ).map(i => if (i == 0) Array(1f, 0f) else Array(0f, 1f)))

  val xs = X along Batch
  val ys = Y along Batch

  val x = Input[DenseTensor[Float, In::$]]()
  val y = Input[DenseTensor[Float, Out::$]]()

  val Layer = Affine(In -> 2, Out -> 2)

  val output = x |> Layer |> Softmax
  val loss = LogLoss(output, y)

  val sgd = StochasticGradientDescentOptimizer(0.1f)

  for (i <- 0 until 100) {
    for ((xv, yv) <- xs zip ys) {
      val (lossValue, values) = Forward.compute(loss)(x <<- xv, y <<- yv)
      val grads = Backward.compute(loss, values)
      println(s"Iteration $i: loss = ${lossValue()}")
      sgd.update(grads)
    }
  }
  val bp = 0
}

