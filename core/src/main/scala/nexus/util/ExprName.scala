package nexus.util

import scala.collection._

/**
 * @author Tongfei Chen
 */
object ExprName {

  @volatile private var inputId: Int = 0
  @volatile private var constId: Int = 0
  private val next = mutable.HashMap[String, Int]()

  def nextInput = nextId("x")

  def nextConst = nextId("c")

  def nextId(key: String) = synchronized {
    if (next contains key) {
      next(key) += 1
      val i = next(key)
      s"$key$i"
    }
    else {
      next += key -> 0
      val i = 0
      s"$key$i"
    }
  }

}

