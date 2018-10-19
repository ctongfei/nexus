package nexus.modules

import nexus._
import nexus.tensor._
import nexus.util._

/**
 * 1-dimensional convolution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Convolution1D[T[_], R, l <: Dim, x <: Dim, y <: Dim] private(
                                                                    val window: Int,
                                                                    val stride: Int,
                                                                    val weight: Param[T[(y, x)]]
                                                                  )
                                                                  (implicit T: IsRealTensorK[T, R])
  extends Module1[T[(l, x)], T[(l, y)]]
{

  def parameters = Set(weight)

  def apply(x: Symbolic[T[(l, x)]]) = ???
}


object Convolution1D {

  def apply[T[_], R, l <: Dim, x <: Dim, y <: Dim](
    widthAxis: l,
    inputAxisSize: (x, Int),
    outputAxisSize: (y, Int),
    window: Int,
    stride: Int = 1,
    name: String = ExprName.nextId("Convolution1D")
  )
  (implicit T: IsRealTensorK[T, R]): Convolution1D[T, R, l, x, y] = {
    val (inputAxis, inputSize) = inputAxisSize
    val (outputAxis, outputSize) = outputAxisSize
    val key = ExprName.nextId("Convolution1D")
    val weight = Param(T.newTensor[(y, x)](Array(outputSize, inputSize)), s"$key.weight")
    new Convolution1D[T, R, l, x, y](window, stride, weight)
  }

}
