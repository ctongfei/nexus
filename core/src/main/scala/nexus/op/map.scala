package nexus.op

import nexus._
import nexus.algebra._
import scala.annotation._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Elementwise extends ParameterizedPolyOp1 {

  implicit def mapF[T[_], R, A](implicit T: IsRealTensorK[T, R]) = (f: Op1[R, R]) =>
    new F[T[A], T[A]] {
      import T._
      def name = s"Map[${f.name}]"
      def tag(tx: Type[T[A]]) = tx
      override def differentiable = f.differentiable
      def forward(x: T[A]) = map(x)(f.forward)
      def backward(dy: T[A], y: T[A], x: T[A]) = map3(dy, y, x)(f.backward)
    }

}
