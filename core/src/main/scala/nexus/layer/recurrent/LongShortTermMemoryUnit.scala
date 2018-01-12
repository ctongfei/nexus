package nexus.layer.recurrent

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.op._

/**
 * @author Tongfei Chen
 */
class LongShortTermMemoryUnit[T[_], R, X: Label, C: Label, H: Label]()(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnit[(T[C], T[H]), T[X]]
{
  def apply(ch: Expr[(T[C], T[H])], x: Expr[T[X]]) = {
    val c = ch |> Tuple2First
    val h = ch |> Tuple2Second

    (c, h) |> Tuple2
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
  def apply[T[_], R, X: Label, C: Label, H: Label](
                                   inputAxisAndSize: (X, Int),
                                   cellAxisAndSize: (C, Int),
                                   hiddenAxisAndSize: (H, Int)
                                   )
                                   (implicit T: IsRealTensorK[T, R]) = {
    ???
  }

}
