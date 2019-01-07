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

  val s = Storage.fromJvm(Array(1f, 2f, 3f))
  val sa = s.data
  val t = sa.toArray
  val ss = s.toArray

  s.view foreach println


  class A extends Dim; val A = new A
  class B extends Dim; val B = new B
  class C extends Dim; val C = new C

  val z = FloatTensor.newTensor(A -> 2, B -> 3)

  val zT = FloatTensorIsRealTensorK.transpose(z)

  val x = FloatTensorIsRealTensorK.fromNestedArray(A, B)(
    Array(
      Array(1f, 2f),
      Array(3f, 4f)
    )
  )

  println(x)

  val y = x + x


  val bp = 0
}
