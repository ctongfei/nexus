package nexus.torch

/**
 * @author Tongfei Chen
 */
abstract class Tensor[E, A] private[torch](val ptr: Long) extends NativeObject {
  def storage: Storage[E]
}
// TODO: SIP-35 opaque type: zero-cost abstraction:
// TODO: opaque type Tensor[E, A] = Long
