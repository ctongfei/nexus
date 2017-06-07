package nexus

import nexus.CPU.MemoryFloat64Storage
import nexus.ops._
import shapeless._
import shapeless.nat._

/**
 * @author Tongfei Chen
 */
object Test extends App {

  class AA
  val AA = new AA

  class AB
  val AB = new AB


  val storage = new CPU.MemoryFloat32Storage(Array(1f, 2f, 3f, 4f, 5f, 6f))
  val a = CPU.Tensor.fromHandle(Float32, storage, AA :: AB :: HNil, Array(2, 3))

  val a1 = a along AA
  val a2 = a along AB

  val a3 = a.sliceAlong(AA, 1)
  val a4 = a.sliceAlong(AB, 2)

  import CPU._

  val b = CPU.Tensor.fromNestedArray(Float64, AA :: AB :: HNil)(
    Array(
      Array(1d, 2d, 3d),
      Array(4d, 5d, 6d)
    )
  )

  val bp = 0

}
