package nexus.prob

/**
 * @author Tongfei Chen
 */
trait HasPdf[A, R] {

  def pdf(x: A): R

  def logPdf(x: A): R

}
