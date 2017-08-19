package nexus.op.loss

import nexus._
import nexus.impl._

object LogLoss extends PolyDOp2[LogLossF]

@implicitNotFound("Cannot apply LogLoss to ${Ŷ} and ${Y}.")
trait LogLossF[Y, Ŷ, L] extends DOp2[Y, Ŷ, L] {
  def name = "LogLoss"
}

object LogLossF {

  implicit def vector[T[_ <: $$], D, A](implicit ops: TypedMathOps[T, D]): LogLossF[Int, T[A::$], T[$]] =
    new LogLossF[Int, T[A::$], T[$]] {
      import ops._
      def gradOps = ops.ground[$]
      def backward1(dy: T[$], y: T[$], x1: Int, x2: T[::[A, $]]) = throw new OperatorNotDifferentiableException(name, 1)
      def backward2(dy: T[$], y: T[$], x1: Int, x2: T[::[A, $]]) = ???
      def forward(x1: Int, x2: T[::[A, $]]) = ???
    }
  
}
