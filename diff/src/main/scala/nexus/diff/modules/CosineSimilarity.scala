package nexus.diff.modules

import nexus.diff._
import nexus.diff.ops._
import nexus._
import nexus.diff.syntax._

/**
 * Computes the cosine similarity between two vectors.
 * @author Tongfei Chen
 */
object CosineSimilarity extends PolyModule2 {
  implicit def cosineSimilarityF[T[_], R, I](implicit T: IsRealTensorK[T, R]): P[T[I], T[I], R] =
    new P[T[I], T[I], R] {
      def apply[F[_] : Algebra](x1: F[T[I]], x2: F[T[I]]) = Div(Dot(x1, x2), Mul(L2Norm(x1), L2Norm(x2)))
      def parameters = Set()
    }
}
