package nexus.exception

/**
 * @author Tongfei Chen
 */
class IncrementingImmutableGradientException
extends Exception("Gradient for this type is immutable; cannot increment its value.")
