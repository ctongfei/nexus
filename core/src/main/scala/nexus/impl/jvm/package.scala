package nexus.impl

import nexus.algebra._
import shapeless._

/**
 * @author Tongfei Chen
 */
package object jvm {

  implicit val cpuFloat32: IsRealTensorK[FloatTensor, Float] = CPUFloat32

  type DenseVector[A] = FloatTensor[A :: HNil]
  type DenseMatrix[A, B] = FloatTensor[A :: B :: HNil]

}
