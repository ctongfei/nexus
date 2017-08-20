package nexus

import nexus.impl._

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: TypedRealTensorOps[DenseTensor, Float] = TypedCPUFloat32

}
