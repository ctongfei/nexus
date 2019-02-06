package nexus.prob

import nexus._
import nexus.diff._

/**
 * @author Tongfei Chen
 */
abstract class Bernoulli[Z, R](p: R) extends ContinuousDistribution[Z, R]

object Bernoulli {

  def apply[D[_]: Algebra, Z: IsInt, R: IsReal](p: D[R]) = new Bernoulli[D[Z], D[R]](p) {
    def pdf(x: D[Z]) = ???
    def logPdf(x: D[Z]) = ???
    def sample = ???
  }

}
