package nexus.syntax

import nexus._
import nexus.typelevel._
import nexus.util._
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
    def +#(y: Double) = X.addScalar(x, y)
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

  implicit class TensorOps[T[_], E, U](x: T[U])(implicit T: IsTensorK[T, E]) {

    def apply[I, V](i: I)(implicit ix: Indexing.Aux[U, I, V]): T[V] = T.index(x, i)

    def shape: Seq[Int] = macro op1

    def unstackAlong[I, V](axis: I)(implicit rx: Remove.Aux[U, I, V]): Seq[T[V]] = T.unstackAlong(x, axis)

  }

  implicit class RealTensorOps[T[_], R, U](x: T[U])(implicit T: IsRealTensorK[T, R]) {

    /** Addition of two tensors with the same axes and shape. */
    def +(y: T[U]): T[U] = macro op2

    def @+@[V, W](y: T[V]) = ???

    /** Subtraction of two tensors with the same axes and shape. */
    def -(y: T[U]): T[U] = macro op2
    /** Negation of a tensor. */
    def unary_- : T[U] = macro op1
    /** Elementwise multiplication (Hadamard product) of two tensors with the same axes and shape. */
    def |*|(y: T[U]): T[U] = macro op2
    /** Elementwise division of two tensors with the same axes and shape. */
    def |/|(y: T[U]): T[U] = macro op2
    /** Scales a tensor by a scalar. */
    def :*(y: R): T[U] = macro op2
    /** Scales a tensor by a scalar. */
    def :*(y: Float): T[U] = macro op2TRFloat
    /** Scales a tensor by a scalar. */
    def :*(y: Double): T[U] = macro op2TRDouble

    def :/(y: R): T[U] = T.scale(x, T.R.recip(y))
    def :/(y: Float): T[U] = T.scale(x, T.R.fromFloat(1f / y))
    def :/(y: Double): T[U] = T.scale(x, T.R.fromDouble(1d / y))

    /** Dot product (inner product) of two tensors of the same shape and size. */
    def dot(y: T[U]): R = macro op2

    def ⋅(y: T[U]): R = T.dot(x, y)

    /** Natural contraction of two tensors. */
    def ⋈[V, W](y: T[V])(implicit sd: SymDiff.Aux[U, V, W]): T[W] = T.contract(x, y)


  }


}
