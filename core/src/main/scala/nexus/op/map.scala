package nexus.op

import nexus._
import nexus.func._
/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Map[D](f: DOp1[D, D]) extends ParaPolyDOp1[DOp1[D, D], MapF] {
  def parameter = f
}

object Map {


}