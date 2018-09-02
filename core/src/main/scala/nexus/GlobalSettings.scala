package nexus

/**
 * @author Tongfei Chen
 */
object GlobalSettings {

  private[nexus] var seed: Long = System.nanoTime()

  def setSeed(newSeed: Long) = {
    seed = newSeed
    random = new java.util.Random(seed)
  }

  private[nexus] var random = new java.util.Random(seed)

}
