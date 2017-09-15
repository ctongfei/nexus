package nexus.impl

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: IsTypedRealTensor[DenseTensor, Float] = TypedCPUFloat32

}
