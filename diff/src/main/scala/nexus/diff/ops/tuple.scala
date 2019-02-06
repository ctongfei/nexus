package nexus.diff.ops

import nexus._
import nexus.diff._
import nexus.diff.syntax._
import shapeless._

import scala.reflect._

/**
 * @author Tongfei Chen
 */
object Pair extends ParameterizedPolyOp2 { prod =>

  object Tuple extends PolyOp2 {
    implicit def instance[X1: Grad, X2: Grad]: F[X1, X2, (X1, X2)] =
      new F[X1, X2, (X1, X2)] {
        def forward(x1: X1, x2: X2) = (x1, x2)
        def backward1(dy: (X1, X2), y: (X1, X2), x1: X1, x2: X2) = dy._1
        def backward2(dy: (X1, X2), y: (X1, X2), x1: X1, x2: X2) = dy._2
        def name = "Tuple2"
        def tag = Tag of Grad[(X1, X2)]
      }
  }

  object First extends ParameterizedPolyOp1 {
    implicit def instance[Y, X1: Grad, X2: Grad]: Generic.Aux[Y, X1 :: X2 :: HNil] => F[Y, X1] = Y =>
      new F[Y, X1] {
        def forward(y: Y) = Y.to(y).head
        def backward(dx1: X1, x1: X1, y: Y) = Y.from(dx1 :: Grad[X2].zeroBy(Y.to(y).tail.head) :: HNil)
        def name = "First"
        def tag = Tag of Grad[X1]
      }
  }

  object Second extends ParameterizedPolyOp1 {
    implicit def instance[Y, X1: Grad, X2: Grad]:  Generic.Aux[Y, X1 :: X2 :: HNil] => F[Y, X2] = Y =>
      new F[Y, X2] {
        def forward(y: Y) = Y.to(y).tail.head
        def backward(dx2: X2, x2: X2, y: Y) = Y.from(Grad[X1].zeroBy(Y.to(y).head) :: dx2 :: HNil)
        def name = "Second"
        def tag = Tag of Grad[X2]
      }
  }

  implicit def instance[X1: Grad, X2: Grad, Y: ClassTag]: Generic.Aux[Y, X1 :: X2 :: HNil] => F[X1, X2, Y] = implicit Y =>
    new F[X1, X2, Y] {
      def forward(x1: X1, x2: X2) = Y.from(x1 :: x2 :: HNil)
      def backward1(dy: Y, y: Y, x1: X1, x2: X2) = Y.to(dy).head
      def backward2(dy: Y, y: Y, x1: X1, x2: X2) = Y.to(dy).tail.head
      def name = classTag[Y].toString()
      def tag = Tag of Grad.deriveInstance[Y, X1 :: X2 :: HNil]
    }

  def unapply[F[_], Y, H, X1, X2]
  (y: F[Y])(implicit Y: Generic.Aux[Y, X1 :: X2 :: HNil], F: Algebra[F], X1: Grad[X1], X2: Grad[X2]): Option[(F[X1], F[X2])] =
    Some(y |> Pair.First(Y), y |> Pair.Second(Y))

}
