package nexus

import shapeless._

/**
 * @author Tongfei Chen
 */
trait Op1[X, Y] {
  def forward(x: X): Y
  def backward(dy: Y, y: Y, x: X): X
}

trait Op2[X1, X2, Y] {
  def forward(x1: X1, x2: X2): Y
  def backward1(dy: Y, y: Y, x1: X1, x2: X2): X1
  def backward2(dy: Y, y: Y, x1: X1, x2: X2): X2
}

trait GenOp1 { self =>

  def forward[T <: TensorLike[T]](x: T): T
  def backward[T <: TensorLike[T]](dy: T, y: T, x: T): T

  def ground[T <: TensorLike[T]]: Op1[T, T] = new Op1[T, T] {
    def forward(x: T) = self.forward[T](x)
    def backward(dy: T, y: T, x: T) = self.backward[T](dy, y, x)
  }
}

trait GenOp2 { self =>

  def apply[T <: TensorLike[T]](x1: Expr[T], x2: Expr[T]): Expr[T] = Apply2[T, T, T](ground[T], x1, x2)
  def apply[T <: TensorLike[T]](x1: T, x2: T): T = forward[T](x1, x2)

  def forward[T <: TensorLike[T]](x1: T, x2: T): T
  def backward1[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T): T
  def backward2[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T): T

  def ground[T <: TensorLike[T]]: Op2[T, T, T] = new Op2[T, T, T] {
    def forward(x1: T, x2: T) = self.forward[T](x1, x2)
    def backward1(dy: T, y: T, x1: T, x2: T) = self.backward1[T](dy, y, x1, x2)
    def backward2(dy: T, y: T, x1: T, x2: T) = self.backward2[T](dy, y, x1, x2)
  }
}
