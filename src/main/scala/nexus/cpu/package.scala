package nexus

/**
 * @author Tongfei Chen
 */
package object cpu {

  implicit val cpuFloat32: Env[UntypedDenseTensor, Float] = new CPUFloat32

}
