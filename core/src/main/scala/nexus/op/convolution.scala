package nexus.op

import nexus._
import nexus.algebra._

/**
 * 1-D convolution.
 * @author Tongfei Chen
 */
case class Convolution1DConfig(windowSize: Int, stride: Int)

object Convolution1D extends ParameterizedPolyOp1 {

  def apply(windowSize: Int, stride: Int): PolyFunc1 =
    apply(Convolution1DConfig(windowSize, stride))

  implicit def convolution1dF[T[_], R, L, X, Y](implicit T: IsRealTensorK[T, R]) = (cc: Convolution1DConfig) =>
    new F[T[(L, X)], T[(L, Y)]] {
        def name = s"Convolution1D[windowSize = ${cc.windowSize}, stride = ${cc.stride}]"
        def tag(tx: Type[T[(L, X)]]) = T.ground[(L, Y)] // TODO: shape
        def forward(x: T[(L, X)]) = ???
        def backward(dy: T[(L, Y)], y: T[(L, Y)], x: T[(L, X)]) = ???
    }

}

case class Convolution2DConfig(windowSize: (Int, Int), strides: (Int, Int))

object Convolution2D extends ParameterizedPolyOp1 {

  def apply(windowSize: (Int, Int), strides: (Int, Int)): PolyFunc1 =
    apply(Convolution2DConfig(windowSize, strides))

  implicit def convolution2dF[T[_], R, W, H, X, Y](implicit T: IsRealTensorK[T, R]) = (cc: Convolution2DConfig) =>
    new F[T[(W, H, X)], T[(W, H, Y)]] {
        def name = s"Convolution2D[windowSize = ${cc.windowSize}, strides = ${cc.strides}"
        def tag(tx: Type[T[(W, H, X)]]) = T.ground[(W, H, Y)] // TODO: shape
        def forward(x: T[(W, H, X)]) = ???
        def backward(dy: T[(W, H, Y)], y: T[(W, H, Y)], x: T[(W, H, X)]) = ???
    }

}

