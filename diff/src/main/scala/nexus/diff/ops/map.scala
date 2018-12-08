package nexus.diff.ops

import nexus.diff._
import nexus.tensor._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Elementwise extends ParameterizedPolyOp1 {

  implicit def mapF[T[_], R, a](implicit T: IsRealTensorK[T, R]) = (f: Op1[R, R]) =>
    new F[T[a], T[a]] {
      import T._
      def name = s"Map[${f.name}]"
      def tag = Tag.realTensor[T, R, a]
      override def differentiable = f.differentiable
      def forward(x: T[a]) = map(x)(f.forward)
      def backward(dy: T[a], y: T[a], x: T[a]) = map3(dy, y, x)(f.backward)
    }

}
