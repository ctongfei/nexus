package nexus.ops

import nexus._
import nexus.algebra._
import nexus.exception._

/**
 * Boolean negation.
 * @author Tongfei Chen
 */
object Not extends PolyOp1 {

  implicit def notF[B](implicit B: IsBool[B]): F[B, B] =
    new F[B, B] {
      type Tag[b] = IsBool[b]
      def name = "Not"
      def tag = B
      override def differentiable = false
      def forward(x: B) = B.not(x)
      def backward(dy: B, y: B, x: B) = throw new OperatorNotDifferentiableException(this, 1)
    }

}

/**
 * Boolean conjunction.
 */
object And extends PolyOp2 {

  implicit def andF[B](implicit B: IsBool[B]): F[B, B, B] =
    new F[B, B, B] {
      type Tag[b] = IsBool[b]
      def name = "And"
      def tag = B
      override def differentiable = false
      def forward(x1: B, x2: B) = B.and(x1, x2)
      def backward1(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 2)
    }

}

/**
 * Boolean disjunction.
 */
object Or extends PolyOp2 {

  implicit def orF[B](implicit B: IsBool[B]): F[B, B, B] =
    new F[B, B, B] {
      type Tag[b] = IsBool[b]
      def name = "Or"
      def tag = B
      override def differentiable = false
      def forward(x1: B, x2: B) = B.or(x1, x2)
      def backward1(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 2)
    }

}

/**
 * Boolean exclusive disjunction.
 */
object Xor extends PolyOp2 {

  implicit def xorF[B](implicit B: IsBool[B]): F[B, B, B] =
    new F[B, B, B] {
      type Tag[b] = IsBool[b]
      def name = "Xor"
      def tag = B
      override def differentiable = false
      def forward(x1: B, x2: B) = B.xor(x1, x2)
      def backward1(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 1)
      def backward2(dy: B, y: B, x1: B, x2: B) = throw new OperatorNotDifferentiableException(this, 2)
    }

}
