package nexus.diff.ops

import nexus.diff._
import nexus._


object Map extends ParameterizedPolyOp1 {

  implicit def mapF[X, Y]: Func1[X, Y] => F[Seq[X], Seq[Y]] = f =>
    new F[Seq[X], Seq[Y]] {
      def forward(x: Seq[X]) = ???
      def backward(dy: Seq[Y], y: Seq[Y], x: Seq[X]) = ???
      def name = s"Map[$f]"
      def tag = ???
    }

  /**
   * Applies an arbitrary differentiable function to all elements in a specific tensor.
   * @note This operation might be slow! Use with caution.
   * @author Tongfei Chen
   * @since 0.1.0
   */
  object Elementwise extends ParameterizedPolyOp1 {

    implicit def mapElementwiseF[T[_], R, I](implicit T: IsRealTensorK[T, R]) = (f: Op1[R, R]) =>
      new F[T[I], T[I]] {
        import T._
        def name = s"Map[${f.name}]"
        def tag = Tag.realTensor[T, R, I]
        override def differentiable = f.differentiable
        def forward(x: T[I]) = map(x)(f.forward)
        def backward(dy: T[I], y: T[I], x: T[I]) = map3(dy, y, x)(f.backward)
      }

  }

}