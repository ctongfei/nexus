package nexus

/**
 * Typelevel trait that gets the suitable tensor type for a specific element type.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait DType[E] {
  /** Tensor type. */
  type Tensor[_]
  /** Typeclass instance for the derived tensor type. */
  val T: IsTensorK[Tensor, E]
}

trait BoolDType[B] extends DType[B] {
  val B: IsBool[B]
  val T: IsBoolTensorK[Tensor, B]
}

object BoolDType {
  type Aux[B, T0[_]] = BoolDType[B] { type Tensor[A] = T0[A] }
}

trait IntDType[Z] extends DType[Z] {
  val Z: IsInt[Z]
  val T: IsIntTensorK[Tensor, Z]
}

object IntDType {
  type Aux[Z, T0[_]] = IntDType[Z] { type Tensor[A] = T0[A] }
}

trait RealDType[R] extends DType[R] {
  val R: IsReal[R]
  val T: IsRealTensorK[Tensor, R]
}

object RealDType {
  type Aux[R, T0[_]] = RealDType[R] { type Tensor[A] = T0[A] }
}
