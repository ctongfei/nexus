package nexus.ml

/**
 * @author Tongfei Chen
 */
class Step[M, R] {

  def model: M
  def loss: R

}
