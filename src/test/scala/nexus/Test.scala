package nexus

import shapeless._
import nexus.storage._

/**
 * @author Tongfei Chen
 */
object Test extends App {

  class DA
  val DA = new DA
  class DB
  val DB = new DB


  val storage = new MemoryFloat32Storage(Array(1f, 2f, 3f, 4f, 5f, 6f))
  val a = DenseTensor.ofStorage(storage, DA :: DB :: HNil, Array(2, 3))

  val a1 = a along DA
  val a2 = a along DB

  val a3 = a.sliceAlong(DA, 1)
  val a4 = a.sliceAlong(DB, 2)

  val bp = 0

}
