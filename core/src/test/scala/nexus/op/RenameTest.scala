package nexus.op

import nexus._
import nexus.execution._
import nexus.impl.jvm._
class A;
class B;
class C;
/**
 * @author Tongfei Chen
 */
object RenameTest extends App {

   val A = new A
   val B = new B
   val C = new C

  val a = Input[FloatTensor[(A, B)]]()
  val b = a |> Rename(B -> C)

  val x = FloatTensor.fromFlatArray[A](Array(3f, 4f), Array(2))
  val y = FloatTensor.fromFlatArray[A](Array(0f, 0f), Array(2))

  val xi = Input[FloatTensor[A]]()
  val yi = Input[FloatTensor[A]]()

  val zi = (xi |> Scale.By(3f), yi) |> L2Distance

  val (z, _) = SimpleForward.compute(zi)(xi := x, yi := y)

  val bp = 0

}
