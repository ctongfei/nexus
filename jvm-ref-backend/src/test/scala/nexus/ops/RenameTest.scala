package nexus.ops


import nexus._
import nexus.algebra._
import nexus.execution._
import nexus.jvm._
import nexus.modules._

class A extends Dim
class B extends Dim
class C extends Dim

/**
 * @author Tongfei Chen
 */
object RenameTest extends App {

   val A = new A
   val B = new B
   val C = new C

  val a = Input[FloatTensor[(A, B)]]()
  val b = a |> RenameAxis(B -> C)

  val x = FloatTensor.fromFlatArray[A](Array(3f, 4f), Array(2))
  val y = FloatTensor.fromFlatArray[A](Array(0f, 0f), Array(2))

  val xi = Input[FloatTensor[A]]()
  val yi = Input[FloatTensor[A]]()


  val bp = 0

}
