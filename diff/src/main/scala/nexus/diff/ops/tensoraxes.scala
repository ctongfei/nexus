package nexus.diff.ops

import nexus.diff._
import nexus._
import nexus.typelevel._
import shapeless._

/**
 * Renaming an axis in any tensor.
 * @example {{{ RenameAxis(U -> V) }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object RenameAxis extends ParameterizedPolyOp1 {

  implicit def renameF[T[_], E, U, I <: Dim, J <: Dim, V]
  (implicit r: Replace.Aux[U, I, J, V], T: IsTensorK[T, E]) = (ij: (I, J)) =>
    new F[T[U], T[V]] {
      val (i, j) = ij
      def name = n"Rename[$i -> $j]"
      def tag = Tag.tensor[T, E, V]
      def forward(x: T[U]) = T.renameAxis[U, V](x)
      def backward(dy: T[V], y: T[V], x: T[U]) = T.renameAxis[V, U](dy)
    }

}

object ConcatAlong extends ParameterizedPolyOp2 {
  implicit def concatAlongF[T[_], E, U, I <: Dim, N <: Nat]
  (implicit n: IndexOf.Aux[U, I, N], T: IsTensorK[T, E]) = (u: I) =>
    new F[T[U], T[U], T[U]] {
        def name = n"Concat[$u]"
        def tag = Tag.tensor[T, E, U]
        def forward(x1: T[U], x2: T[U]) = ???
        def backward1(dy: T[U], y: T[U], x1: T[U], x2: T[U]) = ???
        def backward2(dy: T[U], y: T[U], x1: T[U], x2: T[U]) = ???
    }

}

/**
 * @see `tf.expand_dims`, `torch.unsqueeze`
 */
object Unsqueeze extends ParameterizedPolyOp1 {

  implicit def unsqueezeF[T[_], E, U, N <: Nat, I <: Dim, V]
  (implicit ia: InsertAt.Aux[U, N, I, V], T: IsTensorK[T, E]) = (ni: (N, I)) =>
    new F[T[U], T[V]] {
      val (n, i) = ni
      def name = n"Unsqueeze[$n: $i]"
      def tag = Tag.tensor[T, E, V]
      def forward(x: T[U]) = ???
      def backward(dy: T[V], y: T[V], x: T[U]) = ???
    }

}

/**
 * @see `tf.squeeze`; `torch.squeeze`
 */
object Squeeze extends ParameterizedPolyOp1 {

  implicit def squeezeF[T[_], E, U, N <: Nat, I <: Dim, V]
  (implicit ix: IndexOf.Aux[U, I, N], rx: RemoveAt.Aux[U, N, V], T: IsTensorK[T, E]) = (i: I) =>
    new F[T[U], T[V]] {
      def name = n"Squeeze[$i]"
      def tag = Tag.tensor[T, E, V]
      def forward(x: T[U]) = ???
      def backward(dy: T[V], y: T[V], x: T[U]) = ???
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
