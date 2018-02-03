package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import shapeless._

/**
 * Unpacks a tensor along a specific axis, generating a sequence of tensors with one dimension less.
 * @see `tf.unstack`; `torch.unbind`
 * @example {{{
 *    val sentence: FloatTensor[(Length, Embedding)]
 *    sentence |> UnstackAlong(Length) // Seq[FloatTensor[Embedding]]
 * }}}
 * @author Tongfei Chen
 * @since 0.1.0
 */
object UnstackAlong extends ParameterizedPolyOp1 {

  implicit def unstackAlongF[T[_], E, A, X: Label, N <: Nat, B]
  (implicit T: IsTensorK[T, E], ix: IndexOf.Aux[A, X, N], r: RemoveAt.Aux[A, N, B]): X => F[T[A], Seq[T[B]]] =
    (x: X) => new F[T[A], Seq[T[B]]] {
      def name = s"UnstackAlong[${typeName(x)}]"
      def tag(tx: Type[T[A]]) = ???
      def forward(x: T[A]) = ???
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

  implicit def stackF[T[_], E, A, X: Label, B]
  (implicit T: IsTensorK[T, E], ia: InsertAt.Aux[A, _0, X, B]): X => F[Seq[T[A]], T[B]] =
    (x: X) => new F[Seq[T[A]], T[B]] {
      def name = s"Stack[${typeName(x)}]"
      def tag(tx: Type[Seq[T[A]]]) = ???
      def forward(x: Seq[T[A]]) = ???
      def backward(dy: T[B], y: T[B], x: Seq[T[A]]) = ???
    }
}
