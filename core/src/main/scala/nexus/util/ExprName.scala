package nexus.util

/**
 * @author Tongfei Chen
 */
object ExprName {

  @volatile var inputId: Int = 0
  @volatile var constId: Int = 0

  def nextInput = synchronized {
    val s = s"x$inputId"
    inputId += 1
    s
  }

  def nextConst = synchronized {
    val s = s"c$constId"
    constId += 1
    s
  }
}
