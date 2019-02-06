package nexus.ml

import cats.Id
import nexus._
import nexus.diff._

/**
 * @author Tongfei Chen
 */
trait NeuralModel[X, Y] extends (X => Y) {

  type R

  def module: Module1[X, Y]

  def apply(x: X): Y = module.apply[Id](x)

}
