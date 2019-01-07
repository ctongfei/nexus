import shapeless._

/**
 * Nexus: Typesafe tensors / deep learning for Scala.
 * @author Tongfei Chen
 * @since 0.1.0
 */
package object nexus {

  // Some alias for HList / HNil: Think `$` as the end of a regex
  private[nexus] val  $: HNil = HNil // explicit type annotation to avoid some implicit search bugs

  sealed class ?
  val ? : ? = new ?


}
