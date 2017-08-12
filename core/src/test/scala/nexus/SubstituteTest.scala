package nexus

import nexus.cpu._

/**
 * @author Tongfei Chen
 */
object SubstituteTest extends App {

  val x = Input[DenseTensor[$]]()

  val y = x |*| x

  val sq = x =>> y

  val z = sq(y)

  val bp = 0


}
