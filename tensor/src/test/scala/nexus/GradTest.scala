package nexus

import _root_.nexus.instances._

/**
 * @author Tongfei Chen
 */
object GradTest extends App {

  case class R2(a: Float, b: Float)

  val R2grad = Grad[R2]

  val bp = 0

}
