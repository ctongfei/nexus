package nexus

/**
 * Base trait for tensor dimension labels.
 * All dimension labels should inherit this trait.
 * @author Tongfei Chen
 * @since 0.1.0
 */
// TODO: enforce singleton? something like:
// TODO: trait Dim { self: Singleton => }
trait Dim {

  override def toString = {
    var s = super.toString
    val posDollar = s.lastIndexOf('$')
    s = s.substring(posDollar + 1)  // if posDollar == -1 (not found), then -1 + 1 == 0, still works
    val posAt = s.indexOf('@')
    if (posAt != -1)
      s = s.substring(0, posAt)
    s
  }

}

object Dim {
  //TODO: ValueOf after Scala 2.13
}
