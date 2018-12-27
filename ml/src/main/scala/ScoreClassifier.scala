/**
 * @author Tongfei Chen
 */
trait ScoreClassifier[X, T[_], Y, R] {

  def numClasses: Int

  def posterior(x: X): T[Y]

  def score(x: X, y: Int): R

  def classify(x: X) = ???

}

