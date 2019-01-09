package nexus.torch

/**
 * @author Tongfei Chen
 */
// TODO: SIP-35 opaque type: zero-cost abstraction:
// TODO: opaque type Tensor[E, A] = Long
abstract class Tensor[E, A] private[torch](val ptr: Long) extends NativeObject { tensor =>
  def storage: Storage[E]
}
