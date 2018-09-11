package nexus.ops

import nexus._
import nexus.algebra._

/**
 * Packs two values into a pair ([[Tuple2]]).
 * @author Tongfei Chen
 * @since 0.1.0
 */
object AsTuple2 extends PolyOp2 {
  implicit def instance[X1, X2]: F[X1, X2, (X1, X2)] = new F[X1, X2, (X1, X2)] {
    def name = "AsTuple2"
    def tag = ???
    def forward(x1: X1, x2: X2) = (x1, x2)
    def backward1(dy: (X1, X2), y: (X1, X2), x1: X1, x2: X2) = dy._1
    def backward2(dy: (X1, X2), y: (X1, X2), x1: X1, x2: X2) = dy._2
  }
}

/**
 * Extracts the first element of a pair.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Tuple2First extends PolyOp1 {
  implicit def instance[X1: Grad, X2: Grad]: F[(X1, X2), X1] = new F[(X1, X2), X1] {
    def name = "Tuple2First"
    def tag = ???
    def forward(x: (X1, X2)) = x._1
    def backward(dy: X1, y: X1, x: (X1, X2)) = (dy, Grad[X2].zeroBy(x._2))
  }
}

/**
 * Extracts the second element of a pair.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object Tuple2Second extends PolyOp1 {
  implicit def instance[X1: Grad, X2: Grad]: F[(X1, X2), X2] = new F[(X1, X2), X2] {
    def name = "Tuple2Second"
    def tag = ???
    def forward(x: (X1, X2)) = x._2
    def backward(dy: X2, y: X2, x: (X1, X2)) = (Grad[X1].zeroBy(x._1), dy)
  }
}

/*
/**
 * Packs three values into a triple ([[Tuple3]]).
 */
object Tuple3 extends PolyOp3 {
  implicit def instance[X1: Grad, X2: Grad, X3: Grad]: F[X1, X2, X3, (X1, X2, X3)] = new F[X1, X2, X3, (X1, X2, X3)] {
    def name = "Tuple3"
    def tag(tx1: Type[X1], tx2: Type[X2], tx3: Type[X3]) = Grad[(X1, X2, X3)]
    def differentiable = true
    def forward(x1: X1, x2: X2, x3: X3) = (x1, x2, x3)
    def backward1(dy: (X1, X2, X3), y: (X1, X2, X3), x1: X1, x2: X2, x3: X3) = dy._1
    def backward2(dy: (X1, X2, X3), y: (X1, X2, X3), x1: X1, x2: X2, x3: X3) = dy._2
    def backward3(dy: (X1, X2, X3), y: (X1, X2, X3), x1: X1, x2: X2, x3: X3) = dy._3
  }
}

object Tuple3First extends PolyOp1 {
  implicit def instance[X1: Grad, X2: Grad, X3: Grad]: F[(X1, X2, X3), X1] = new F[(X1, X2, X3), X1] {
    def name = "Tuple3First"

    def forward(x: (X1, X2, X3)) = x._1
    def backward(dy: X1, y: X1, x: (X1, X2, X3)) = (dy, Grad[X2].zeroBy(x._2), Grad[X3].zeroBy(x._3))
  }
}

object Tuple3Second extends PolyOp1 {
  implicit def instance[X1: Grad, X2: Grad, X3: Grad]: F[(X1, X2, X3), X2] = new F[(X1, X2, X3), X2] {
    def name = "Tuple3Second"
    def tag = Grad[X2]
    def forward(x: (X1, X2, X3)) = x._2
    def backward(dy: X2, y: X2, x: (X1, X2, X3)) = (Grad[X1].zeroBy(x._1), dy, Grad[X3].zeroBy(x._3))
  }
}

object Tuple3Third extends PolyOp1 {
  implicit def instance[X1: Grad, X2: Grad, X3: Grad]: F[(X1, X2, X3), X3] = new F[(X1, X2, X3), X3] {
    def name = "Tuple3Third"
    def tag = Grad[X3]
    def forward(x: (X1, X2, X3)) = x._3
    def backward(dy: X3, y: X3, x: (X1, X2, X3)) = (Grad[X1].zeroBy(x._1), Grad[X2].zeroBy(x._2), dy)
  }
}
*/
