package nexus

import nexus.autodiff._
import nexus.cpu._
import nexus.layer._
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

  val X = DenseTensor.fromNestedArray(Batch :: In :: $)(Array(
    Array(0.0f, 0.9f),
    Array(0.1f, 0.1f),
    Array(0.2f, 0.2f),
    Array(0.8f, 0.9f),
    Array(1.1f, 0.2f),
    Array(0.9f, 0.1f),
    Array(0.3f, 0.8f),
    Array(0.7f, 0.7f)
  ))

  val Y = DenseTensor.fromNestedArray(Batch :: $)(Array(
    1f, 0f, 0f, 0f, 1f, 1f, 1f, 0f)
  )

  val xs = X along Batch
  val ys = Y along Batch


  val x = Input[DenseTensor[Float, In::$]]()
  val y = Input[DenseTensor[Float, Out::$]]()


  val Layer1 = Affine(In -> 2, Hidden -> 10)
  val Layer2 = Affine(Hidden -> 10, Out -> 2)

  val output = x |> Layer1 |> Sigmoid |> Layer2 |> Sigmoid
  val loss = CrossEntropyLoss(output, y)

  for ((xv, yv) <- xs zip ys) {
    val (lossValue, values) = Forward.compute(loss, ValueStore(x -> xv, y -> yv))
    val gradients = Backward.compute(loss, values)

  }


  val bp = 0

}
