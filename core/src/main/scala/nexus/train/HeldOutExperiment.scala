package nexus.train

import nexus._
import nexus.algebra._
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
  implicit val R: IsReal[R] = T.R

  type TrainingInput

  type TrainingOutput

  type ValidationInput

  type ValidationOutput

  val trainingSet: Iterable[(TrainingInput, TrainingOutput)]

  val validationSet: Iterable[(ValidationInput, ValidationOutput)]

  val trainingLoss: Func2[TrainingInput, TrainingOutput, R]

  val validationLoss: Func2[ValidationInput, ValidationOutput, R]


  def trainEpoch() = {

    for ((xv, yv) <- trainingSet) {
      val x = Input[TrainingInput]()
      val y = Input[TrainingOutput]()
      val l = trainingLoss(x, y)

      implicit val forward = Forward.given(x := xv, y := yv)
      val lv = l.value
      val gradients = Backward.compute(l)

    }

  }

}
