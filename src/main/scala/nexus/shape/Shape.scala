package nexus.shape

/**
 * @author Tongfei Chen
 */
private[nexus] object Shape {

  def removeAt(shape: Seq[Int], index: Int) =
    shape.take(index) ++ shape.drop(index + 1)

}
