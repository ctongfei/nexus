package nexus.util

/**
 * @author Tongfei Chen
 */
object StringUtils {

  private val maxPrefixLength = 20
  private val maxSuffixLength = 5

  /**
   * Shows a summary to a numeric array.
   */
  def arraySummary[A](
                       a: IndexedSeq[A],
                       toStr: A => String = (x: A) => x.toString,
                       delimiter: String = ", ",
                       ellipsis: String = " ... ",
                       prefix: String = "(",
                       suffix: String = ")"
                     ): String = {

    val len = a.length

    val content = if (len <= maxPrefixLength + maxSuffixLength)
      a.map(toStr).mkString(delimiter)
    else {
      val arrayPrefix = a.take(maxPrefixLength)
      val arraySuffix = a.takeRight(maxSuffixLength)
      arrayPrefix.map(toStr).mkString(delimiter) + ellipsis + arraySuffix.map(toStr).mkString(delimiter)
    }

    s"$prefix$content$suffix"
  }

}
