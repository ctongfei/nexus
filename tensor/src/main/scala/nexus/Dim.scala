package nexus

/**
 * Base trait for tensor dimension labels.
 * All dimension labels should inherit this trait.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Dim {

  override def toString = {
    var s = super.toString
    val pos_$ = s.lastIndexOf('$')
    s = s.substring(pos_$ + 1)  // if posDollar == -1 (not found), then -1 + 1 == 0, still works
    val pos_@ = s.indexOf('@')
    if (pos_@ != -1)
      s = s.substring(0, pos_@)
    s
  }

}

object Dim {

  // TODO: Scala 2.13 literal types
  abstract class Sized[N <: Int](val n: N)

}
