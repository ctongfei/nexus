package nexus

import nexus.cpu._
import nexus.op._
import nexus.op.activation._
import nexus.op.loss._
import shapeless._

/**
 * @author Tongfei Chen
 */
object Test extends App {

  class Batch; val Batch = new Batch
  class In; val In = new In
  class Hidden; val Hidden = new Hidden
  class Out; val Out = new Out

  val X = DenseTensor.fromNestedArray(Batch :: In :: HNil)(Array(
    Array(0.0f, 0.9f),
    Array(0.1f, 0.1f),
    Array(0.2f, 0.2f),
    Array(0.8f, 0.9f),
    Array(1.1f, 0.2f),
    Array(0.9f, 0.1f),
    Array(0.3f, 0.8f),
    Array(0.7f, 0.7f)
  ))

  val Y = DenseTensor.fromNestedArray(Batch :: HNil)(Array(
    1f, 0f, 0f, 0f, 1f, 1f, 1f, 0f)
  )

  val xs = X along Batch
  val ys = Y along Batch

  val inputDims = 2
  val hiddenDims = 10
  val outputDim = 1

  val W1 = Param(DenseTensor.fill(0f, Hidden :: In :: HNil, Array(hiddenDims, inputDims)), name = "W1")
  val b1 = Param(DenseTensor.fill(0f, Hidden :: HNil, Array(hiddenDims)), name = "b1")
  val W2 = Param(DenseTensor.fill(0f, Out :: Hidden :: HNil, Array(outputDim, hiddenDims)), name = "W2")
  val b2 = Param(DenseTensor.fill(0f, Out :: HNil, Array(outputDim)), name = "b2")

  for ((xv, yv) <- xs zip ys) {
    val x = Input(xv)
    val y = Input(yv)

    val t = Sigmoid(Add(MVMul(W1, x), b1))
    val yp = Sigmoid(Add(MVMul(W2, t), b2))
    val loss = CrossEntropyLoss(yp, y)
  }





  val bp = 0

}
