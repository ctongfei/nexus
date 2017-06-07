package nexus.ops

import nexus._
import shapeless._

object Id extends GenOp1 {
  def forward[T <: TensorLike[T]](x: T) = x
  def backward[T <: TensorLike[T]](dy: T, y: T, x: T) = dy
}

object Neg extends GenOp1 {
  def forward[T <: TensorLike[T]](x: T) = -x
  def backward[T <: TensorLike[T]](dy: T, y: T, x: T) = -dy
}

object Add extends GenOp2 {
  def forward[T <: TensorLike[T]](x1: T, x2: T) = x1 + x2
  def backward1[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T) = dy
  def backward2[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T) = dy
}

object Sub extends GenOp2 {
  def forward[T <: TensorLike[T]](x1: T, x2: T) = x1 - x2
  def backward1[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T) = dy
  def backward2[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T) = -dy
}

object EMul extends GenOp2 {
  def forward[T <: TensorLike[T]](x1: T, x2: T) = x1 |*| x2
  def backward1[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T) = dy |*| x2
  def backward2[T <: TensorLike[T]](dy: T, y: T, x1: T, x2: T) = dy |*| x1
}
