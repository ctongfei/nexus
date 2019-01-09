package nexus.torch

import nexus._
import nexus.tensormath._
import nexus.syntax._
import nexus.torch._
import nexus.torch.jni._

/**
 * @author Tongfei Chen
 */
object TorchTest extends App {

  class A extends Dim; val A = new A
  class B extends Dim; val B = new B
  class C extends Dim; val C = new C

  val x = FloatTensor.fromNestedArray(A, B)(
    Array(
      Array(1f, 2f),
      Array(3f, 4f)
    )
  )

  println(x)

  val y = FloatTensor.fromNestedArray(B)(Array(-1f, -1f))
  val z = mvMul(x, y)

  val u = matMul(x, transpose(x))

  val bp = 0
}
