package nexus.op

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Sine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Sin extends TypeInvariantPolyDOp1[IsReal] {

  def name = "Sin"
  def forward[R](x: R)(implicit R: IsReal[R]) = R.sin(x)
  def backward[R](dy: R, y: R, x: R)(implicit R: IsReal[R]) = dy * R.cos(x)

  /**
   * Elementwise sine on a tensor.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Sin.Elementwise"
    def forward[T[_ <: $$], R, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, R]) = T.eSin(x)
    def backward[T[_ <: $$], R, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, R]) = dy |*| T.eCos(x)
  }
}


/**
 * Cosine on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Cos extends TypeInvariantPolyDOp1[IsReal] {
  def name = "Cos"
  def forward[X](x: X)(implicit X: IsReal[X]) = X.cos(x)
  def backward[X](dy: X, y: X, x: X)(implicit X: IsReal[X]) = -dy * X.sin(x)
  /**
   * Elementwise cosine on a tensor.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Cos.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eCos(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = -dy |*| T.eSin(x)
  }
}

/**
 * Tangent on a scalar.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Tan extends TypeInvariantPolyDOp1[IsReal] {
  def name = "Tan"
  def forward[X](x: X)(implicit X: IsReal[X]) = X.tan(x)
  def backward[X](dy: X, y: X, x: X)(implicit X: IsReal[X]) = dy * (X.sqr(y) + X.one)

  /**
   * Elementwise tangent on a tensor.
   */
  object Elementwise extends TypeInvariantTensorPolyDOp1[IsTypedRealTensor] {
    def name = "Tan.Elementwise"
    def forward[T[_ <: $$], E, A <: $$](x: T[A])(implicit T: IsTypedRealTensor[T, E]) = T.eTan(x)
    def backward[T[_ <: $$], E, A <: $$](dy: T[A], y: T[A], x: T[A])(implicit T: IsTypedRealTensor[T, E]) = dy |*| T.addS(T.eSqr(y), T.R.one)
  }
}
