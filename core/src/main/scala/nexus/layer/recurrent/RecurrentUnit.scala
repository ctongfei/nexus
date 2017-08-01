package nexus.layer.recurrent

/**
 * @author Tongfei Chen
 */
trait RecurrentUnit[S, I] {

  def apply(s: S, i: I): S

}
