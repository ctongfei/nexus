package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import scala.annotation._

/**
 * Renaming an axis in any tensor.
 *
 * @example {{{ Rename(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Rename[U, V](parameter: (U, V)) extends ParaPolyDOp1[(U, V)] {

  type F[P, X, Y] = Rename.POp[P, X, Y]

}

object Rename {

  @implicitNotFound("Cannot apply Rename[${P}] to ${X}.")
  trait POp[P, X, Y] extends (P => DOp1[X, Y])

  object POp {

    implicit def tensor[T[_ <: $$], R, A <: $$, U, V, B <: $$]
    (implicit r: Replace.Aux[A, U, V, B], T: IsTypedRealTensor[T, R]) =
      new POp[(U, V), T[A], T[B]] {
        def apply(p: (U, V)) = new DOp1[T[A], T[B]] {
          val (u, v) = p
          import T._
          def tag = T.ground[B]
          def name = s"Rename[${objTypeName(u)} -> ${objTypeName(v)}]"
          def forward(x: T[A]) = typeWith(untype(x), r(typeOf(x), v))
          def backward(dy: T[B], y: T[B], x: T[A]) = typeWith(untype(dy), r.inverse(typeOf(dy), u))
        }
      }

    // TODO: non-differentiable case

  }

}

