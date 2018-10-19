package nexus.tensor.syntax

import nexus.tensor._
import nexus.tensor.typelevel._
import nexus.tensor.util._
import scala.language.experimental.macros

/**
 * Syntactic sugars (operator additions)
 * @author Tongfei Chen
 */
trait Priority1Implicits {

  import OpsInlining._

  implicit class GradOps[X](x: X)(implicit X: Grad[X]) {

    def +(y: X): X = macro op2
    def -(y: X): X = macro op2
    def +#(y: Double) = X.addS(x, y)
    def unary_- : X = macro op1
    def :*(y: Float): X = macro op2
    def :*(y: Double): X = macro op2
    def :/(y: Float) = X.scale(x, 1f / y)
    def :/(y: Double) = X.scale(x, 1d / y)
    def |*|(y: X): X = macro op2
    def |/|(y: X): X = macro op2

  }

  implicit class RealOps[R](x: R)(implicit R: IsReal[R]) {

    def *(y: R): R = macro op2
    def /(y: R): R = macro op2

    def *(y: Float): R = macro op2Float
    def /(y: Float): R = macro op2Float

    def *(y: Double): R = macro op2Double
    def /(y: Double): R = macro op2Double

  }

  implicit class TensorOps[T[_], E, A](x: T[A])(implicit T: IsTensorK[T, E]) {

    def apply(indices: Int*): E = T.get(x, indices)
    def shape: Seq[Int] = macro op1

    def unstackAlong[U, B](axis: U)(implicit rx: Remove.Aux[A, U, B]): Seq[T[B]] = T.unstackAlong(x, axis)

  }

  implicit class RealTensorOps[T[_], R, a](x: T[a])(implicit T: IsRealTensorK[T, R]) {

    /** Addition of two tensors with the same axes and shape. */
    def +(y: T[a]): T[a] = macro op2
    /** Subtraction of two tensors with the same axes and shape. */
    def -(y: T[a]): T[a] = macro op2
    /** Negation of a tensor. */
    def unary_- : T[a] = macro op1
    /** Elementwise multiplication (Hadamard product) of two tensors with the same axes and shape. */
    def |*|(y: T[a]): T[a] = macro op2
    /** Elementwise division of two tensors with the same axes and shape. */
    def |/|(y: T[a]): T[a] = macro op2
    /** Scales a tensor by a scalar. */
    def :*(y: R): T[a] = macro op2
    /** Scales a tensor by a scalar. */
    def :*(y: Float): T[a] = macro op2TRFloat
    /** Scales a tensor by a scalar. */
    def :*(y: Double): T[a] = macro op2TRDouble

    def :/(y: R): T[a] = T.scale(x, T.R.inv(y))
    def :/(y: Float): T[a] = T.scale(x, T.R.fromFloat(1f / y))
    def :/(y: Double): T[a] = T.scale(x, T.R.fromDouble(1d / y))

    /** Dot product (inner product) of two tensors of the same shape and size. */
    def dot(y: T[a]): R = macro op2
    def ⋅(y: T[a]): R = T.dot(x, y)

    /** Natural contraction of two tensors. */
    def ⋈[b, c](y: T[b])(implicit sd: SymDiff.Aux[a, b, c]): T[c] = T.contract(x, y)


  }


}
