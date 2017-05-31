import shapeless.HNil

/**
 * @author Tongfei Chen
 */
package object nexus {

  implicit class Tensor0Ops[D <: DType](val t: Tensor[D, HNil]) {
    def get[R](implicit R: DType.Ev[R, D]): R = t()
  }

}
