package nexus.ml

import nexus._
import nexus.diff._

trait Model[X, Y] {
  def predict: Module1[X, Y]
}

trait Classifier[X, T[_], Out <: Dim] extends Model[X, T[Out]] {
  def predict: Module1[X, T[Out]]
}

