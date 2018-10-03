package nexus.modules.recurrent

import nexus._
import nexus.ops._

/**
 * A recurrent unit used in long short-term memory networks.
 *
 * Reference:
 *  - S Hochreiter, J Schmidhuber (1997): Long short-term memory. Neural Computation. '''9''' (8): 1735-1780.
 *  - F A Gers, J Schmidhuber, F Cummins (2000): Learning to forget: continual prediction with LSTM. Neural Computation '''12''' (10): 2451-2471.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class LongShortTermMemoryUnit[T[_], R, X <: Dim, C <: Dim, H <: Dim]()(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnit[(T[C], T[H]), T[X]]
{
  def apply(ch: Expr[(T[C], T[H])], x: Expr[T[X]]) = {
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
   * @tparam X
   * @tparam C
   * @tparam H
   * @return
   */
  def apply[T[_], R, X <: Dim, C <: Dim, H <: Dim](
                                   inputAxisAndSize: (X, Int),
                                   cellAxisAndSize: (C, Int),
                                   hiddenAxisAndSize: (H, Int)
                                   )
                                   (implicit T: IsRealTensorK[T, R]) = {
    ???
  }

}
