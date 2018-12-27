package nexus.diff.modules

import nexus.diff._
import nexus._
import nexus.diff.util._

/**
 * 1-dimensional convolution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Convolution1D[T[_], R, W <: Dim, X <: Dim, Y <: Dim] private(
                                                                    val window: Int,
                                                                    val stride: Int,
                                                                    val kernel: Param[T[(Y, X)]]
                                                                  )
                                                                  (implicit T: IsRealTensorK[T, R])
  extends Module1[T[(W, X)], T[(W, Y)]]
{

  def parameters = Set(kernel)

  def apply(x: Symbolic[T[(W, X)]]) = ???
}


object Convolution1D {

  def apply[T[_], R, W <: Dim, X <: Dim, Y <: Dim](
    widthAxis: W,
    inputAxisSize: (X, Int),
    outputAxisSize: (Y, Int),
    window: Int,
    stride: Int = 1,
  )
  (implicit dType: RealDType.Aux[R, T], name: sourcecode.Name): Convolution1D[T, R, W, X, Y] = {
    import dType._
    val (inputAxis, inputSize) = inputAxisSize
    val (outputAxis, outputSize) = outputAxisSize
    val weight = Param(T.newTensor[(Y, X)](Array(outputSize, inputSize)), s"${name.value}.kernel")(T.ground)
    new Convolution1D[T, R, W, X, Y](window, stride, weight)
  }

}
