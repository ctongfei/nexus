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

  val a = Input[DenseTensor[A::B::$]]()

  val b = a |> Rename(B -> C)


  val x = DenseTensor.fromFlatArray(Array(3f, 4f), A::$, Array(2))
  val y = DenseTensor.fromFlatArray(Array(0f, 0f), A::$, Array(2))

  val xi = Input[DenseTensor[A::$]]()
  val yi = Input[DenseTensor[A::$]]()
  val zi = L2Distance(Scale(Const(3.0f), xi), yi)

  val (z, _) = Forward.compute(zi)(xi <<- x, yi <<- y)

  val bp = 0

}
