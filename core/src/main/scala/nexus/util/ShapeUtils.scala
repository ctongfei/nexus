package nexus.util

/**
 * @author Tongfei Chen
 */
private[nexus] object ShapeUtils {

  def removeAt(shape: Array[Int], index: Int) =
    shape.take(index) ++ shape.drop(index + 1)

  def insertAt(shape: Array[Int], index: Int, value: Int) =
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

}
