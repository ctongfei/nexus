package nexus

/**
 * Base trait for tensor dimension labels.
 * All dimension labels should inherit this trait.
 * @author Tongfei Chen
 * @since 0.1.0
 */
// TODO: enforce singleton? something like:
// TODO: trait Dim { self: Singleton => }
trait Dim

object Dim {
  //TODO: ValueOf after Scala 2.13
}
