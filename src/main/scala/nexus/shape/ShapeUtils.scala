package nexus.shape

/**
 * @author Tongfei Chen
 */
private[nexus] object ShapeUtils {

  def removeAt(shape: Array[Int], index: Int) =
    shape.take(index) ++ shape.drop(index + 1)

}
