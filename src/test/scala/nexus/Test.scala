package nexus

import nexus.cpu._
import nexus.op._
import shapeless._

/**
 * @author Tongfei Chen
 */
object Test extends App {

  class AA
  val AA = new AA

  class AB
  val AB = new AB



  val a = DenseTensor.fromNestedArray(AA :: AB :: HNil)(
    Array(
      Array(1f, 2f, 3f),
      Array(4f, 5f, 6f)
    )
  )

  val negA = Neg.forward(a)(NegF.cpuNegF)
  val sumA = Add.forward(a.sliceAlong(AA, 1), a.sliceAlong(AA, 0))

  val a1 = a along AA
  val a2 = a along AB

  println(a)
  println(a.sliceAlong(AA, 1))
  println(a.sliceAlong(AB, 2))

  val s = a.toString

  val x = Input(a)
  val y = Add(x, x)
  val z = Add(x, y)

  val bp = 0

}
