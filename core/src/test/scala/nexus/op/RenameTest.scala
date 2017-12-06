package nexus.op

import nexus._
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
  val y = L2Norm(x)

  val bp = 0

}
