import shapeless._

/**
 * Nexus: Typesafe tensors / deep learning for Scala.
 * @author Tongfei Chen
 * @since 0.1.0
 */
package object nexus {

  // Some alias for HList / HNil: Think `$` as the end of a regex
  private[nexus] val  $: HNil = HNil // explicit type annotation to avoid some implicit search bugs

  type Id[A] = A
  def Id[A](a: A): Id[A] = a

  val ? = Slice.?

  private[nexus] var seed: Long = System.nanoTime()

  def setSeed(newSeed: Long): Unit = synchronized {
    seed = newSeed
    randomSource = new java.util.Random(seed)
  }

  private[nexus] var randomSource = new java.util.Random(seed)

}
