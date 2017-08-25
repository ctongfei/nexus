package nexus.algebra

import nexus._

/**
 * Typeclass that describes the algebraic structures on tensors with arbitrary element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait TypedTensorOps[T[_ <: $$], D] extends TensorAxisTyping[T] { self =>

  def H: UntypedTensorOps[H, D]

  def newTensor[A <: $$](axes: A, shape: Array[Int]): T[A]

  def wrapScalar(x: D): T[$] = typeWith(H.wrapScalar(x), $)

  def unwrapScalar(x: T[$]): D = H.unwrapScalar(untype(x))

  def map[A <: $$](x: T[A])(f: D => D): T[A] =
    typeWith(H.map(untype(x))(f), typeOf(x))

  def map2[A <: $$](x1: T[A], x2: T[A])(f: (D, D) => D): T[A] =
    typeWith(H.map2(untype(x1), untype(x2))(f), typeOf(x1))

  def map3[A <: $$](x1: T[A], x2: T[A], x3: T[A])(f: (D, D, D) => D): T[A] =
    typeWith(H.map3(untype(x1), untype(x2), untype(x3))(f), typeOf(x1))

}
