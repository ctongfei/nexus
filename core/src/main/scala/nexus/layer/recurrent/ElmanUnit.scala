package nexus.layer.recurrent

import nexus._
import nexus.algebra._
import nexus.layer._
import nexus.syntax._
import nexus.op._

/**
 * Elman recurrent unit.
 * @author Tongfei Chen
 */
class ElmanUnit[T[_ <: $$], R, X, S, Y] private(
                                                 val inputLayer: Affine[T, R, X, S],
                                                 val outputLayer: Affine[T, R, S, Y],
                                                 val stateActivation: DOp1[T[S::$], T[S::$]],
                                                 val outputActivation: DOp1[T[Y::$], T[Y::$]],
                                                 val inputAxis: X,
                                                 val stateAxis: S,
                                                 val outputAxis: Y
                                               )
                                               (implicit T: IsRealTensor[T, R])
  extends RecurrentUnitWithOutput[T[S::$], T[X::$], T[Y::$]]
{
  def apply(s: Expr[T[S::$]], x: Expr[T[X::$]]) = {
    val sʹ = stateActivation(
      Concat(inputAxis)(
        s |> Rename(stateAxis -> inputAxis),
        x
      ) |> inputLayer)

    val y = outputActivation(sʹ |> outputLayer)
    (sʹ, y)
  }
}

object ElmanUnit {

  /**
   * Constructs an Elman recurrent unit -- the simplest recurrent unit.
   * @example {{{
   *   ElmanUnit(Input -> 300, State -> 300, Output -> 300, Tanh, Softmax)
   * }}}
   * @param inputAxisAndSize
   * @param stateAxisAndSize
   * @param outputAxisAndSize
   * @param stateActivation
   * @param outputActivation
   * @tparam T
   * @tparam R
   * @tparam X
   * @tparam S
   * @tparam Y
   * @return
   */
  def apply[T[_ <: $$], R, X, S, Y](
                                   inputAxisAndSize: (X, Int),
                                   stateAxisAndSize: (S, Int),
                                   outputAxisAndSize: (Y, Int),
                                   stateActivation: PolyDOp1,
                                   outputActivation: PolyDOp1
                                   )
                                   (implicit
                                    T: IsRealTensor[T, R],
                                    saf: stateActivation.F[T[S::$], T[S::$]],
                                    oaf: outputActivation.F[T[Y::$], T[Y::$]]
                                   ) = {
    val (inputAxis, inputSize) = inputAxisAndSize
    val (stateAxis, stateSize) = stateAxisAndSize
    val (outputAxis, outputSize) = outputAxisAndSize
    val inputLayer = Affine(inputAxis -> (inputSize + stateSize), stateAxis -> stateSize)
    val outputLayer = Affine(stateAxis -> stateSize, outputAxis -> outputSize)
    new ElmanUnit[T, R, X, S, Y](inputLayer, outputLayer, saf, oaf, inputAxis, stateAxis, outputAxis)
  }

}
