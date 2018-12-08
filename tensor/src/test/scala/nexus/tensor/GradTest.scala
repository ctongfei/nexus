package nexus.tensor

import nexus.tensor.instances._
/**
 * @author Tongfei Chen
 */
object GradTest extends App {

  case class R2(a: Float, b: Float)

  val R2grad = Grad[R2]

  val bp = 0

}
