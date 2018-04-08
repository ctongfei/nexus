package nexus.algebra.util

/**
 * @author Tongfei Chen
 */
object Indices {

  class IndicesIterator(val shape: Array[Int]) extends Iterator[Array[Int]] {
    private[this] val rank = shape.length
    private[this] val curr = Array.fill(rank)(0)
    private[this] var i = -1
    private[this] var d = rank - 1
    private[this] val totalSize = shape.product

    def hasNext = i < totalSize - 1
    def next() = {
      i += 1
      if (i > 0) {
        curr(d) += 1
        if (curr(d) >= shape(d) && d > 0) {
          while (curr(d) >= shape(d) && d > 0) {
            curr(d) = 0
            d -= 1
            curr(d) += 1
          }
          d = rank - 1
        }
      }
      curr
    }
  }

  def indices(shape: Array[Int]): Iterable[Array[Int]] =
    new Iterable[Array[Int]] {
      def iterator = new IndicesIterator(shape)
    }

}
