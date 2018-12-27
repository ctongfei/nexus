package nexus

/**
 * Represents a native device (Native CPU / CUDA GPU / etc.) that is outside
 * of the control of JVM.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Device {

  /** Supertype of all objects allocated on this device. */
  type Obj

  /** Set of objects that has memory allocated on this device. */
  def allocatedObjects: Set[Obj]

  /** Total number of bytes of memory used on this device. */
  def totalMemoryUsage: Long

  /** Immediately performs garbage collection on this device. */
  def gc(): Unit

}
