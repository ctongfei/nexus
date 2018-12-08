package nexus.diff.ops

import nexus.diff._
import nexus.tensor._
import nexus.tensor.typelevel._
import shapeless._

/**
 * Renaming an axis in any tensor.
 * @example {{{ RenameAxis(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object RenameAxis extends ParameterizedPolyOp1 {

  implicit def renameF[T[_], E, a, u <: Dim, v <: Dim, b]
  (implicit r: Replace.Aux[a, u, v, b], T: IsTensorK[T, E]) = (uv: (u, v)) =>
    new F[T[a], T[b]] {
      val (u, v) = uv
      def name = n"Rename[$u -> $v]"
      def tag = Tag.tensor[T, E, b]
      def forward(x: T[a]) = T.renameAxis[a, b](x)
      def backward(dy: T[b], y: T[b], x: T[a]) = T.renameAxis[b, a](dy)
    }

}

object ConcatAlong extends ParameterizedPolyOp2 {
  implicit def concatAlongF[T[_], E, a, u <: Dim, n <: Nat]
  (implicit n: IndexOf.Aux[a, u, n], T: IsTensorK[T, E]) = (u: u) =>
    new F[T[a], T[a], T[a]] {
        def name = n"Concat[$u]"
        def tag = Tag.tensor[T, E, a]
        def forward(x1: T[a], x2: T[a]) = ???
        def backward1(dy: T[a], y: T[a], x1: T[a], x2: T[a]) = ???
        def backward2(dy: T[a], y: T[a], x1: T[a], x2: T[a]) = ???
    }

}

/**
 * @see `tf.expand_dims`, `torch.unsqueeze`
 */
object Unsqueeze extends ParameterizedPolyOp1 {

  implicit def unsqueezeF[T[_], E, a, n <: Nat, u <: Dim, b]
  (implicit ia: InsertAt.Aux[a, n, u, b], T: IsTensorK[T, E]) = (nu: (n, u)) =>
    new F[T[a], T[b]] {
      val (n, u) = nu
      def name = n"ExpandDim[$n: $u]"
      def tag = Tag.tensor[T, E, b]
      def forward(x: T[a]) = ???
      def backward(dy: T[b], y: T[b], x: T[a]) = ???
    }

}

/**
 * @see `tf.squeeze`; `torch.squeeze`
 */
object Squeeze extends ParameterizedPolyOp1 {

  implicit def squeezeF[T[_], E, a, n <: Nat, u <: Dim, b]
  (implicit ix: IndexOf.Aux[a, u, n], rx: RemoveAt.Aux[a, n, b], T: IsTensorK[T, E]) = (u: u) =>
    new F[T[a], T[b]] {
      def name = n"Squeeze[$u]"
      def tag = Tag.tensor[T, E, b]
      def forward(x: T[a]) = ???
      def backward(dy: T[b], y: T[b], x: T[a]) = ???
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

  implicit def mergeAxesF[T[_], E, a, u <: Dim, v <: Dim, w <: Dim, b]
  (implicit T: IsTensorK[T, E]) = (uvw: ((u, v), w)) => new F[T[a], T[b]] {
    val ((u, v), w) = uvw
    def tag = Tag.tensor[T, E, b]
    def name = n"MergeAxes[($u, $v) -> $w]"
    def forward(x: T[a]) = ???
    def backward(dy: T[b], y: T[b], x: T[a]) = ???
  }

}

/**
 * @example {{{ a |> SplitAxis(Embedding -> (Direction, Embedding)) }}}
 */
object SplitAxis extends ParameterizedPolyOp1 {

  implicit def splitAxisF[T[_], E, a, u <: Dim, v <: Dim, w <: Dim, b]
  (implicit T: IsTensorK[T, E]) = (uvw: (u, (v, w))) => new F[T[a], T[b]] {
    private[this] val (u, (v, w)) = uvw
    def tag = Tag.tensor[T, E, b]
    def name = n"SplitAxis[$u -> ($v, $w)]"
    def forward(x: T[a]) = ???
    def backward(dy: T[b], y: T[b], x: T[a]) = ???
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

  implicit def stackF[T[_], E, a, u <: Dim, b]
  (implicit T: IsTensorK[T, E], ia: InsertAt.Aux[a, _0, u, b]): u => F[Seq[T[a]], T[b]] =
    (x: u) => new F[Seq[T[a]], T[b]] {
      def name = n"Stack[$x]"
      def tag = ???
      def forward(x: Seq[T[a]]) = ???
      def backward(dy: T[b], y: T[b], x: Seq[T[a]]) = ???
    }
}
