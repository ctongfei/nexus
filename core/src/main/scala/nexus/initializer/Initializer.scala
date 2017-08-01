package nexus.initializer

/**
 * @author Tongfei Chen
 */
trait Initializer[X] {
  def apply(): X
}

trait GenInitializer[I[X] <: Initializer[X]] {
  def apply[X](implicit init: I[X]): X = init()
}
