package nexus.impl.nd4j

import nexus.algebra._
import shapeless._

/**
 * @author Andrey Romanov
 */
package object nd4j {


  implicit val nd4jFloat: IsRealTensorK[FloatTensor, Float] = ND4JBackendTensor

  type DenseVector[A] = FloatTensor[A :: HNil]
  type DenseMatrix[A, B] = FloatTensor[A :: B :: HNil]
}
