package nexus

import shapeless._

/**
 * @author Tongfei Chen
 */
object Test extends App {

  class AA extends Axis("A")
  val AA = new AA

  class AB extends Axis("B")
  val AB = new AB


  val storage = new CPU.MemoryFloat32Storage(Array(1f, 2f, 3f, 4f, 5f, 6f))
  val a = CPU.Tensor.fromStorage(storage, AA :: AB :: HNil, Array(2, 3))

  val a1 = a along AA
  val a2 = a along AB

  val a3 = a.sliceAlong(AA, 1)
  val a4 = a.sliceAlong(AB, 2)

  val bp = 0

}
