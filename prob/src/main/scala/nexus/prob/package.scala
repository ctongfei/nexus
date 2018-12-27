package nexus

/**
 * `nexus.prob` enables typed probabilistic programming, both on the value level and on the expression level.
 * This means you can build symbolic probabilistic programs that supports differentiation.
 * @author Tongfei Chen
 */
package object prob {

  private[nexus] var seed: Long = System.nanoTime()

  def setSeed(newSeed: Long): Unit = synchronized {
    seed = newSeed
    randomSource = new java.util.Random(seed)
  }

  private[nexus] var randomSource = new java.util.Random(seed)

}
