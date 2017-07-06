package nexus

import nexus.exec._
import nexus.cpu._
import nexus.op._
import nexus.op.activation._
import nexus.optimizer._

/**
 * Vanilla test to see if p converges to 0.
 * @author Tongfei Chen
 */
object Test0 extends App {

  class A; val A = new A

  val a = Vector(A)(Array(1f, 4f))
  val sa = Softmax.forward(a)
  val da = Softmax.backward(Vector(A)(Array(1f, 1f)), sa, a)

  val bp = 0

  val p = Param(DenseTensor.scalar(3.0f), "p")

  val l = EMul(p, p)

  val sgd = StochasticGradientDescent(0.2f)

  for (i <- 0 until 20) {
    println(s"Epoch $i: p = ${p.value()}")
    val (lv, values) = Forward.compute(l, Values())
    val gradients = Backward.compute(l, values)
    sgd.update(gradients)
  }

}
