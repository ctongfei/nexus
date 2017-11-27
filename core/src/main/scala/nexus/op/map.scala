package nexus.op

import nexus._
import nexus.func._
/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Map[X](f: DOp1[X, X]) extends ParaPolyDOp1[DOp1[X, X], MapF] {
  def parameter = f
}

object Map {

  //def apply[F[X, Y] <: DOp1[X, Y], G[X, Y] <: DOp1[X, Y]](o: PolyDOp1[F])(implicit e: Elementwise[F, G]) = e.elementwiseOp


  def apply[X](f: DOp1[X, X]) = new Map(f)


}