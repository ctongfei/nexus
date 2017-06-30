package nexus

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: Env[DenseTensor, Float] = new CPUFloat32

}
