package nexus

import nexus.functions._

/**
 * Contains common math operations on tensors.
 * Importing this should supplant [[scala.math]].
 * @author Tongfei Chen
 * @since 0.1.0
 */
object math extends ScalarFunctions
  with TensorFunctions
  with MatrixFunctions
{

  implicit class SliceOps(val a: Int) extends AnyVal {
    def ~(b: Int) = Slice.Bounded(a, b)
  }

}
