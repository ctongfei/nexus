package nexus.train

import nexus._
import nexus.algebra.IsRealTensorK
import nexus.exec._

/**
 * High-level scheme for training models.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
abstract class HeldOutExperiment {

  type T[_]
  type R

  implicit val T: IsRealTensorK[T, R]

  type TrainingInput

  type TrainingOutput

  type ValidationInput

  type ValidationOutput

  def trainingSet: Iterable[(TrainingInput, TrainingOutput)]

  def validationSet: Iterable[(ValidationInput, ValidationOutput)]

  def trainingLoss: Func2[TrainingInput, TrainingOutput, R]

  def validationLoss: Func2[ValidationInput, ValidationOutput, R]

  def trainingMetric: Func1[TrainingOutput, R]

  def validationMetric: Func1[ValidationOutput, R]


  def trainEpoch() = {

    for ((xv, yv) <- trainingSet) {
      val x = Input[TrainingInput]()
      val y = Input[TrainingOutput]()
      val l = trainingLoss(x, y)
      val (lv, values) = Forward.compute(l)(x <<- xv, y <<- yv)
      val gradients = Backward.compute(l, values)

    }

  }

}
