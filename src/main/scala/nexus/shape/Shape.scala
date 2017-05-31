package nexus.shape

/**
 * @author Tongfei Chen
 */
private[nexus] object Shape {

  def removeAt(shape: IndexedSeq[Int], index: Int) =
    shape.take(index - 1) ++ shape.drop(index)

}
