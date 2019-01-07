package nexus.util

/**
 * @author Tongfei Chen
 */
private[nexus] object ShapeUtils {

  def contiguousStrides(shape: Array[Int]) =
    shape.tail.scanRight(1)(_*_)

  def contiguousStrides(shape: Array[Long]) =
    shape.tail.scanRight(1l)(_*_)

  def removeAt(shape: Array[Int], index: Int) =
    shape.take(index) ++ shape.drop(index + 1)

  def removeAt(shape: Array[Long], index: Int) =
    shape.take(index) ++ shape.drop(index + 1)

  def insertAt(shape: Array[Int], index: Int, value: Int) =
    shape.take(index) ++ (value +: shape.drop(index))

  def insertAt(shape: Array[Long], index: Int, value: Long) =
    shape.take(index) ++ (value +: shape.drop(index))

  def product(shape: Array[Int]) = {
    var n = 1
    var k = 0
    while (k < shape.length) {
      n *= shape(k)
      k += 1
    }
    n
  }

  def product(shape: Array[Long]) = {
    var n = 1l
    var k = 0
    while (k < shape.length) {
      n *= shape(k)
      k += 1
    }
    n
  }

  def product(shape: Seq[Int]) = {
    var n = 1l
    var k = 0
    while (k < shape.length) {
      n *= shape(k)
      k += 1
    }
    n
  }

}
