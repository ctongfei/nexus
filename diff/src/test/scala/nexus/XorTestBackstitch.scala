package nexus

import nexus.diff.execution._
import nexus.diff.modules._
import nexus.diff.ops._
import nexus.diff.optimizers._

// Define names of axes (an empty class and an object with the same name)
/*
/**
 * Neural network 101: XOR network.
 * @author Tongfei Chen
 */
object XorTestBackstitch extends App {

  val Batch = new Batch
  val In = new In
  val Hidden = new Hidden
  val Out = new Out

  /* Prepare the data. */
  val X = FloatTensor.fromNestedArray(Batch, In)(Array(
    Array(0f, 0f),
    Array(1f, 0f),
    Array(0f, 1f),
    Array(1f, 1f)
  ))

  val Y = FloatTensor.fromNestedArray(Batch, Out)(
    Array(0, 1, 1, 0).map(i => if (i == 0) Array(1f, 0f) else Array(0f, 1f))
  )

  val xs = X along Batch
  val ys = Y along Batch

  val x = Input[FloatTensor[In]]()
  val y = Input[FloatTensor[Out]]()

  val Layer1 = Affine(In -> 2, Hidden -> 2)
  val Layer2 = Affine(Hidden -> 2, Out -> 2)

  val ŷ = x |> Layer1 |> Sigmoid |> Layer2 |> Softmax

  val loss = (y, ŷ) |> CrossEntropy

  /** Declare an optimizer. */
  val opt = new BackstitchOptimizer(0.1, 0.1, 4)

  /** Start running! */
  for (epoch <- 0 until 2000) {
    var averageLoss = 0f

    // For each sample
    for ((xv, yv) <- xs zip ys) {



      val (lossValue, values) =  Forward .compute(loss)(x <<- xv, y <<- yv) // feed
      val gradients           =  Backward.compute(loss, values)
      averageLoss += lossValue
      opt.updateStep1(gradients)

      val (lossValue2, values2) =  Forward .compute(loss)(x <<- xv, y <<- yv) // feed
      val gradients2           =  Backward.compute(loss, values)
      averageLoss += lossValue2
      opt.updateStep2(gradients2)

    }

    println(s"Epoch $epoch: loss = ${averageLoss / 4.0}")
  }

  println(Layer1.weight.value)
  println(Layer1.bias.value)

  println(Layer2.weight.value)
  println(Layer2.bias.value)

}
*/