package nexus.diff.execution

import nexus.diff._
import nexus._
import nexus.diff.collection._

/**
 * @author Tongfei Chen
 */
trait Backward[F[_]] {

  def compute[R](loss: F[R])(implicit R: IsReal[R]): BoxMap[F, Id]

}
