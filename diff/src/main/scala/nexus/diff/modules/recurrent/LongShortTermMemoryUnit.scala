package nexus.diff.modules.recurrent

import nexus.diff._
import nexus.diff.ops._
import nexus._

/**
 * A recurrent unit used in long short-term memory networks.
 *
 * Reference:
 *  - S Hochreiter, J Schmidhuber (1997): Long short-term memory. Neural Computation. '''9''' (8): 1735-1780.
 *  - F A Gers, J Schmidhuber, F Cummins (2000): Learning to forget: continual prediction with LSTM. Neural Computation '''12''' (10): 2451-2471.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class LongShortTermMemoryUnit[T[_], R, a <: Dim, c <: Dim, h <: Dim]()(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnit[(T[c], T[h]), T[a]]
{
  def apply(ch: Symbolic[(T[c], T[h])], x: Symbolic[T[a]]) = {
    val c = ch |> Tuple2First
    val h = ch |> Tuple2Second

    (c, h) |> AsTuple2
  }
}


object LongShortTermMemoryUnit {

  /**
   * Constructs a long short-term memory unit.
   * @example {{{
   *   LongShortTermMemoryUnit(Input -> 200, Cell -> 100, Hidden -> 100)
   * }}}
   * @param inputAxisAndSize
   * @param cellAxisAndSize
   * @param hiddenAxisAndSize
   * @param T
   * @tparam T
   * @tparam R
   * @tparam a
   * @tparam c
   * @tparam h
   * @return
   */
  def apply[T[_], R, a <: Dim, c <: Dim, h <: Dim](
                                   inputAxisAndSize: (a, Int),
                                   cellAxisAndSize: (c, Int),
                                   hiddenAxisAndSize: (h, Int)
                                   )
                                   (implicit T: IsRealTensorK[T, R]) = {
    ???
  }

}
