package nexus.op

import nexus._
import nexus.algebra._

/**
 * Selects the index of the largest element in the given vector.
 * @author Tongfei Chen
 */
object ArgMax extends PolyOp1[ArgMaxF]

trait ArgMaxF[X, Y] extends Op1[X, Y] {
  def name = "ArgMax"
}

object ArgMaxF {

  implicit def vector[T[_ <: $$], A, R](implicit T: TypedRealTensorOps[T, R]): ArgMaxF[T[A::$], Int] =
    new ArgMaxF[T[A::$], Int] {
      def forward(x: T[A::$]): Int = ???
    }

}
