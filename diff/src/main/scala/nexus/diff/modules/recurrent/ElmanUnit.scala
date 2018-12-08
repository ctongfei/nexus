package nexus.diff.modules.recurrent

import nexus.diff._
import nexus.diff.modules._
import nexus.diff.ops._
import nexus.tensor._
import nexus.diff.util._

/**
 * Elman recurrent unit -- the simplest case of a recurrent unit.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ElmanUnit[T[_], R, X <: Dim, S <: Dim] private(
  val inputLayer: Affine[T, R, X, S],
  val stateActivation: Func1[T[S], T[S]],
  val inputAxis: X,
  val stateAxis: S
)
(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnit[T[S], T[X]]
{

  def apply(s: Symbolic[T[S]], x: Symbolic[T[X]]) =
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
  def apply[T[_], R, X <: Dim, S <: Dim](
                                         inputAxisAndSize: (X, Int),
                                         stateAxisAndSize: (S, Int),
                                         activation: PolyFunc1 = Tanh,
                                   )
                                   (implicit
                                    dt: RealDType.Aux[R, T],
                                    saf: activation.F[T[S], T[S]],
                                    name: sourcecode.Name
                                   ) = {
    val (inputAxis, inputSize) = inputAxisAndSize
    val (stateAxis, stateSize) = stateAxisAndSize
    val inputLayer = Affine(
      inputAxis -> (inputSize + stateSize),
      stateAxis -> stateSize,
    )(dt, name = sourcecode.Name(s"${name.value}.transition"))
    new ElmanUnit[T, R, X, S](
      inputLayer,
      activation.ground(saf),
      inputAxis,
      stateAxis
    )
  }

}
