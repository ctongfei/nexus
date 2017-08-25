package nexus

import nexus.algebra._

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: TypedRealTensorOps[DenseTensor, Float] = TypedCPUFloat32

}
