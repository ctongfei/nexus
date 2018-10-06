package nexus.modules.recurrent

import nexus._
import nexus.modules._
import nexus.ops._
import nexus.tensor._
import nexus.util._

/**
 * Elman recurrent unit -- the simplest case of a recurrent unit.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ElmanUnit[T[_], R, a <: Dim, s <: Dim] private(
  val inputLayer: Affine[T, R, a, s],
  val stateActivation: Func1[T[s], T[s]],
  val inputAxis: a,
  val stateAxis: s
)
(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnit[T[s], T[a]]
{

  def apply(s: Expr[T[s]], x: Expr[T[a]]) =
    ((s |> RenameAxis(stateAxis -> inputAxis)), x) |> ConcatAlong(inputAxis) |> inputLayer |> stateActivation

}


object ElmanUnit {

  /**
   * Constructs an Elman recurrent unit -- the simplest recurrent unit.
   * @example {{{
   *   ElmanUnit(Input -> 200, State -> 300, activation = Tanh)
   * }}}
   * @param inputAxisAndSize
   * @param stateAxisAndSize
   * @param activation
   * @return
   */
  def apply[T[_], R, a <: Dim, s <: Dim](
                                         inputAxisAndSize: (a, Int),
                                         stateAxisAndSize: (s, Int),
                                         activation: PolyFunc1 = Tanh,
                                         name: String = ExprName.nextId("ElmanUnit")
                                   )
                                   (implicit
                                    T: IsRealTensorK[T, R],
                                    saf: activation.F[T[s], T[s]]
                                   ) = {
    val (inputAxis, inputSize) = inputAxisAndSize
    val (stateAxis, stateSize) = stateAxisAndSize
    val inputLayer = Affine(
      inputAxis -> (inputSize + stateSize),
      stateAxis -> stateSize,
      name = s"$name.input"
    )
    new ElmanUnit[T, R, a, s](
      inputLayer,
      activation.ground(saf),
      inputAxis,
      stateAxis
    )
  }

}
