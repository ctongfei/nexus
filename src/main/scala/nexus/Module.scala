package nexus

/**
 * @author Tongfei Chen
 */
trait Module[X, Y] {

  def parameters: Map[String, Param[_]]

}
