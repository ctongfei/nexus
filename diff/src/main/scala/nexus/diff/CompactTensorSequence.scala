package nexus.diff

import shapeless.HList

import scala.language.higherKinds

/**
 * Logically a Seq[...[Seq[T[...]]...], physically a Tensor.
 * @tparam T Tensor type
 * @tparam SA Sequence axes
 * @tparam TA Tensor axes
 * @author Tongfei Chen
 */
trait CompactTensorSequence[T[_], SA, TA] extends {

  type SH
  type ST <: HList

}

object CompactTensorSequence {


}