package nexus.diff.syntax

import nexus.diff._
import nexus.diff.ops._
import nexus._

/**
 * @author Tongfei Chen
 */
trait BoxRealTensorMixin {

  implicit class BoxRealTensorOps[D[_], T[_], R, U]
    (val x: D[T[U]])(implicit D: Algebra[D], T: IsRealTensorK[T, R])
  {

    def +(y: D[T[U]]): D[T[U]] = Add(x, y)
    def -(y: D[T[U]]): D[T[U]] = Sub(x, y)
    def *(y: D[T[U]]): D[T[U]] = Mul(x, y)
    def ⊙(y: D[T[U]]): D[T[U]] = Mul(x, y)
    def /(y: D[T[U]]): D[T[U]] = Div(x, y)

  }

  implicit class BoxRealMatrixOps[D[_], T[_], R, I <: Dim, J <: Dim]
    (val x: D[T[(I, J)]])(implicit D: Algebra[D], T: IsRealTensorK[T, R])
  {

    def <*>[K <: Dim](y: D[T[(J, K)]]): D[T[(I, K)]] = MatMul(x, y)

  }

}
