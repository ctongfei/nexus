package nexus

/**
 * Torch bindings for Nexus.
 * @author Tongfei Chen
 */
package object torch {

  implicit val impFloatTensorIsRealTensorK: IsRealTensorK[FloatTensor, Float] = FloatTensorIsRealTensorK

}
