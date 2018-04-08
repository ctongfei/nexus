package nexus.algebra.syntax

import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.algebra.util._
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

  implicit class RealOps[X](x: X)(implicit X: IsReal[X]) {

    def *(y: X): X = macro op2
    def /(y: X): X = macro op2

    def *(y: Float): X = macro op2Float
    def /(y: Float): X = macro op2Float

    def *(y: Double): X = macro op2Double
    def /(y: Double): X = macro op2Double

  }

  implicit class TensorOps[T[_], E, A](x: T[A])(implicit T: IsTensorK[T, E]) {

    def shape: Array[Int] = macro op1

  }

  implicit class RealTensorOps[T[_], R, A](x: T[A])(implicit T: IsRealTensorK[T, R]) {

    /** Addition of two tensors with the same axes and shape. */
    def +(y: T[A]): T[A] = macro op2
    /** Subtraction of two tensors with the same axes and shape. */
    def -(y: T[A]): T[A] = macro op2
    /** Negation of a tensor. */
    def unary_- : T[A] = macro op1
    /** Elementwise multiplication (Hadamard product) of two tensors with the same axes and shape. */
    def |*|(y: T[A]): T[A] = macro op2
    /** Elementwise division of two tensors with the same axes and shape. */
    def |/|(y: T[A]): T[A] = macro op2
    /** Scales a tensor by a scalar. */
    def :*(y: R): T[A] = macro op2
    /** Scales a tensor by a scalar. */
    def :*(y: Float): T[A] = macro op2TRFloat
    /** Scales a tensor by a scalar. */
    def :*(y: Double): T[A] = macro op2TRDouble

    def :/(y: R): T[A] = T.scale(x, T.R.reciprocal(y))
    def :/(y: Float): T[A] = T.scale(x, T.R.fromFloat(1f / y))
    def :/(y: Double): T[A] = T.scale(x, T.R.fromDouble(1d / y))

    /** Dot product (inner product) of two tensors of the same shape and size. */
    def dot(y: T[A]): R = macro op2
    def ⋅(y: T[A]): R = T.dot(x, y)

    /** Natural contraction of two tensors. */
    def ⋈[B, C](y: T[B])(implicit sd: SymDiff.Aux[A, B, C]): T[C] = T.contract(x, y)


  }


}
