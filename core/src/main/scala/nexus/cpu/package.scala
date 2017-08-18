package nexus

import nexus.impl._

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: TypedMathOps[DenseTensor, Float] = TypedCPUFloat32

}
