package nexus.ops

import nexus._
import shapeless._

/**
 * Renaming an axis in any tensor.
 * @example {{{ RenameAxis(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object RenameAxis extends ParameterizedPolyOp1 {

  implicit def renameF[T[_], E, A, U <: Dim, V <: Dim, B]
  (implicit r: Replace.Aux[A, U, V, B], T: IsTensorK[T, E]) = (uv: (U, V)) =>
    new F[T[A], T[B]] {
      val (u, v) = uv
      import T._
      type Tag[t] = IsTensor[t, E]
      def name = n"Rename[$u -> $v]"
      def tag = T.ground[B]
      def forward(x: T[A]) = T.renameAxis[A, B](x)
      def backward(dy: T[B], y: T[B], x: T[A]) = T.renameAxis[B, A](dy)
    }

}

object ConcatAlong extends ParameterizedPolyOp2 {
  implicit def concatAlongF[T[_], E, A, U <: Dim, N <: Nat]
  (implicit n: IndexOf.Aux[A, U, N], T: IsTensorK[T, E]) = (u: U) =>
    new F[T[A], T[A], T[A]] {
      type Tag[t] = IsTensor[t, E]
        def name = n"Concat[$u]"
        def tag = T.ground[A]
        def forward(x1: T[A], x2: T[A]) = ???
        def backward1(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
        def backward2(dy: T[A], y: T[A], x1: T[A], x2: T[A]) = ???
    }

}

/**
 * @see `tf.expand_dims`, `torch.unsqueeze`
 */
object Unsqueeze extends ParameterizedPolyOp1 {

  implicit def unsqueezeF[T[_], E, A, N <: Nat, U <: Dim, B]
  (implicit ia: InsertAt.Aux[A, N, U, B], T: IsTensorK[T, E]) = (nu: (N, U)) =>
    new F[T[A], T[B]] {
      val (n, u) = nu
      type Tag[t] = IsTensor[t, E]
      def name = n"ExpandDim[$n: $u]"
      def tag = T.ground[B]
      def forward(x: T[A]) = ???
      def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }

}

/**
 * @see `tf.squeeze`; `torch.squeeze`
 */
object Squeeze extends ParameterizedPolyOp1 {

  implicit def squeezeF[T[_], E, A, N <: Nat, U <: Dim, B]
  (implicit ix: IndexOf.Aux[A, U, N], rx: RemoveAt.Aux[A, N, B], T: IsTensorK[T, E]) = (u: U) =>
    new F[T[A], T[B]] {
      type Tag[t] = IsTensor[t, E]
      def name = n"Squeeze[$u]"
      def tag = T.ground[B]
      def forward(x: T[A]) = ???
      def backward(dy: T[B], y: T[B], x: T[A]) = ???
    }

}

/**
 * @example {{{ a |> SwapAxes(Batch, Length) }}}
 * @see `torch.transpose`
 */
object SwapAxes extends ParameterizedPolyOp1

/**
 * @example {{{ a |> MergeAxes((Layer, Embedding) -> Embedding) }}}
 */
object MergeAxes extends ParameterizedPolyOp1 {

  implicit def mergeAxesF[T[_], E, A, U <: Dim, V <: Dim, W <: Dim, B]
  (implicit T: IsTensorK[T, E]) = (uvw: ((U, V), W)) => new F[T[A], T[B]] {
    val ((u, v), w) = uvw
    type Tag[t] = IsTensor[t, E]
    def tag = T.ground[B]
    def name = n"MergeAxes[($u, $v) -> $w]"
    def forward(x: T[A]) = ???
    def backward(dy: T[B], y: T[B], x: T[A]) = ???
  }

}

/**
 * @example {{{ a |> SplitAxis(Embedding -> (Direction, Embedding)) }}}
 */
object SplitAxis extends ParameterizedPolyOp1 {

  implicit def splitAxisF[T[_], E, A, U <: Dim, V <: Dim, W <: Dim, B]
  (implicit T: IsTensorK[T, E]) = (uvw: (U, (V, W))) => new F[T[A], T[B]] {
    private[this] val (u, (v, w)) = uvw
    type Tag[t] = IsTensor[t, E]
    def tag = ???
    def name = n"SplitAxis[$u -> ($v, $w)]"
    def forward(x: T[A]) = ???
    def backward(dy: T[B], y: T[B], x: T[A]) = ???
  }
}

/**
 * Unpacks a tensor along a specific axis, generating a sequence of tensors with one dimension less.
 * @see `tf.unstack`; `torch.unbind`
 * @example {{{
 *    val sentence: FloatTensor[(Length, Embedding)]
 *    sentence |> UnstackAlong(Length)  // type: Seq[FloatTensor[Embedding]]
 * }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object UnstackAlong extends ParameterizedPolyOp1 {

  implicit def unstackAlongF[T[_], E, A, U <: Dim, B]
  (implicit T: IsTensorK[T, E], rx: Remove.Aux[A, U, B]): U => F[T[A], Seq[T[B]]] =
    (axis: U) => new F[T[A], Seq[T[B]]] {
      type Tag[t] = IsTensor[t, E]
      def name = n"UnstackAlong[$axis]"
      def tag = ???
      def forward(x: T[A]) = T.unstackAlong(x, axis)
      def backward(dy: Seq[T[B]], y: Seq[T[B]], x: T[A]) = ???
    }

}

/**
 * Given a sequence of tensors of the same shape, stacks them to a new tensor with one more dimension.
 * @see `tf.stack`; `torch.stack`
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Stack extends ParameterizedPolyOp1 {

  implicit def stackF[T[_], E, A, X <: Dim, B]
  (implicit T: IsTensorK[T, E], ia: InsertAt.Aux[A, _0, X, B]): X => F[Seq[T[A]], T[B]] =
    (x: X) => new F[Seq[T[A]], T[B]] {
      type Tag[t] = IsTensor[t, E]
      def name = n"Stack[$x]"
      def tag = ???
      def forward(x: Seq[T[A]]) = ???
      def backward(dy: T[B], y: T[B], x: Seq[T[A]]) = ???
    }
}
