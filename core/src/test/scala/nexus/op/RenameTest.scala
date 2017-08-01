package nexus.op

import nexus._
import nexus.cpu._
import nexus.op.activation._

/**
 * @author Tongfei Chen
 */
object RenameTest extends App {

  class A; val A = new A
  class B; val B = new B
  class C; val C = new C

  val a = Input[DenseTensor[Float, A::B::$]]()

  val b = a |> Rename(B -> C)

  val bp = 0

}
