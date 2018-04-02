package nexus.impl

import nexus.algebra._
import shapeless._

/**
 * @author Andrey Romanov
 */
package object nd4j {


  implicit val nd4jFloat: IsRealTensorK[FloatTensor, Float] = ND4JTensor

  type DenseVector[A] = FloatTensor[A :: HNil]
  type DenseMatrix[A, B] = FloatTensor[A :: B :: HNil]
}
