package nexus

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: Env[Tensor, Float] = new CPUFloat32

}
