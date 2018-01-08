package nexus.op

import nexus._
import nexus.exec._
import nexus.impl.jvm._

/**
 * @author Tongfei Chen
 */
object RenameTest extends App {

  class A; val A = new A
  class B; val B = new B
  class C; val C = new C

  val a = Input[FloatTensor[A::B::$]]()

  val b = a |> Rename(B -> C)


  val x = FloatTensor.fromFlatArray[A::$](Array(3f, 4f), Array(2))
  val y = FloatTensor.fromFlatArray[A::$](Array(0f, 0f), Array(2))

  val xi = Input[FloatTensor[A::$]]()
  val yi = Input[FloatTensor[A::$]]()
  val zi = L2Distance(Scale(Const(3.0f), xi), yi)

  val (z, _) = Forward.compute(zi)(xi <<- x, yi <<- y)

  val bp = 0

}
