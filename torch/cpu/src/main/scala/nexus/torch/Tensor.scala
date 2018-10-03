package nexus.torch

/**
 * @author Tongfei Chen
 */
class Tensor[E, A] private[torch](val ptr: Long) extends NativeObject
// TODO: SIP-35 opaque type: zero-cost abstraction
