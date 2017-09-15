package nexus.algebra.instances

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
object Int32 extends IsInt[Int] {
  def add(x: Int, y: Int) = x + y
  def neg(x: Int) = -x
  def sub(x: Int, y: Int) = x - y
  def mul(x: Int, y: Int) = x * y
  def zero = 0
  def one = 1
}

object Int64 extends IsInt[Long] {
  def add(x: Long, y: Long) = x + y
  def neg(x: Long) = -x
  def sub(x: Long, y: Long) = x - y
  def mul(x: Long, y: Long) = x * y
  def zero = 0l
  def one = 1l
}
