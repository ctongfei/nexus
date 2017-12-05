package nexus.impl

import nexus.algebra._
import shapeless._

/**
 * @author Tongfei Chen
 */
package object jvm {

  implicit val cpuFloat32: IsTypedRealTensor[DenseTensor, Float] = TypedCPUFloat32

  type DenseVector[A] = DenseTensor[A :: HNil]
  type DenseMatrix[A, B] = DenseTensor[A :: B :: HNil]

}
