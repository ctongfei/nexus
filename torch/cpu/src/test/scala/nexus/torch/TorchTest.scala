package nexus.torch

import nexus._
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


  val storage = Storage.ofSize[Float](4)
  storage(0) = 1f
  storage(1) = 2f
  storage(2) = 3f
  storage(3) = 4f
  println(storage)
  val ptr = torchJNI.THFloatTensor_newWithStorage1d(storage.ptr, 0, storage.length, 1)
  val tensor = new FloatTensor(ptr)
  println(tensor)
  torchJNI.THFloatTensor_resize2d(tensor.ptr, 2, 2)
  println(tensor)
  println(tensor.tensorRank)
  println(tensor.shape)
  println(tensor.stride)

  class A extends Dim; val A = new A
  class B extends Dim; val B = new B

  val x = FloatTensor.fromNestedArray(A, B)(
    Array(
      Array(1f, 2f),
      Array(3f, 4f)
    )
  )

  println(x)

  val y = x + x


  val bp = 0
}
