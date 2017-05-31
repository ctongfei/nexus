package nexus

import shapeless.HList

/**
 * @author Tongfei Chen
 */
trait MutableTensor[D <: DType, S <: HList] extends Tensor[D, S] {

}
