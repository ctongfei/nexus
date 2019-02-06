package nexus

import nexus._
import nexus.syntax._
import nexus.diff._
import nexus.diff.execution._
import nexus.jvm._
import nexus.jvm.setFloat32AsDefault._
import nexus.diff.modules._
import nexus.diff.ops._
import nexus.diff.optimizers._
import nexus.diff.syntax._

// Define names of axes (an empty class and an object with the same name)
class Batch extends Dim
class In extends Dim
class Hidden extends Dim
class Out extends Dim

/**
 * Neural network 101: XOR network.
 * @author Tongfei Chen
 */
object XorTest extends App {

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

  val xs = X unstackAlong Batch
  val ys = Y unstackAlong Batch


  val Layer1 = Affine(In -> 2, Hidden -> 2)
  val Layer2 = Affine(Hidden -> 2, Out -> 2)



  /** Declare an optimizer. */
  val opt = new AdamOptimizer(0.1)

  /** Start running! */
  for (epoch <- 0 until 1000) {
    var averageLoss = 0f

    // For each sample
    for ((xv, yv) <- xs zip ys) {
      val x = Traced.Algebra.input(xv, "")
      val y = Traced.Algebra.input(yv, "")
      val ŷ = x |> Layer1 |> Sigmoid |> Layer2 |> Softmax
      val loss = CrossEntropy(y, ŷ)

      val lossValue = loss.value

      averageLoss += lossValue
      opt.step(loss)

      }

    }

    println(s"Epoch $epoch: loss = ${averageLoss / 4.0}")
  }


}
