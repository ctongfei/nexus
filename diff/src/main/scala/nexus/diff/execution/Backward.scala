package nexus.diff.execution

import nexus.diff._
import nexus._
import nexus.diff.collection._

/**
 * @author Tongfei Chen
 */
trait Backward[D[_]] {

  def compute[R](loss: D[R])(implicit R: IsReal[R]): BoxMap[D, Id]

}
