package nexus.torch

/**
 * Represents a C struct that is accessed by a pointer through JNI.
 * @author Tongfei Chen
 */
trait NativeObject {

  /** C pointer to the native object. */
  def ptr: Long

  /** Frees the memory of this object. */
  def free(): Unit

  override final def finalize(): Unit = free()

}
