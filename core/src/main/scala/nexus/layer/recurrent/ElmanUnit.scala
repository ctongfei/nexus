package nexus.layer.recurrent

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.layer._
import nexus.op._
import nexus.util._

/**
 * Elman recurrent unit -- the simplest case of a recurrent unit.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class ElmanUnit[T[_], R, X: Label, S: Label] private(
  val inputLayer: Affine[T, R, X, S],
  val stateActivation: Func1[T[S], T[S]],
  val inputAxis: X,
  val stateAxis: S
)
(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnit[T[S], T[X]]
{

  def apply(s: Expr[T[S]], x: Expr[T[X]]) =
    ((s |> Rename(stateAxis -> inputAxis)), x) |> Concat(inputAxis) |> inputLayer |> stateActivation

}

object ElmanUnit {

  /**
   * Constructs an Elman recurrent unit -- the simplest recurrent unit.
   * @example {{{
   *   ElmanUnit(Input -> 200, State -> 300, Tanh)
   * }}}
   * @param inputAxisAndSize
   * @param stateAxisAndSize
   * @param stateActivation
   * @return
   */
  def apply[T[_], R, X: Label, S: Label, Y: Label](
                                   inputAxisAndSize: (X, Int),
                                   stateAxisAndSize: (S, Int),
                                   stateActivation: PolyFunc1 = Tanh,
                                   name: String = ExprName.nextId("ElmanUnit")
                                   )
                                   (implicit
                                    T: IsRealTensorK[T, R],
                                    saf: stateActivation.F[T[S], T[S]]
                                   ) = {
    val (inputAxis, inputSize) = inputAxisAndSize
    val (stateAxis, stateSize) = stateAxisAndSize
    val inputLayer = Affine(
      inputAxis -> (inputSize + stateSize),
      stateAxis -> stateSize,
      name = s"$name.Input"
    )
    new ElmanUnit[T, R, X, S](
      inputLayer,
      stateActivation.ground(saf),
      inputAxis,
      stateAxis
    )
  }

}
