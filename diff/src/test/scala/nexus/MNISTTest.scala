package nexus

import java.nio._
/*
import nexus._
import nexus.execution._
import nexus.jvm._
import nexus.modules._
import nexus.ops._
import nexus.optimizer._
import poly.io._

/**
 * @author Tongfei Chen
 */

case class MNISTSample(image: Array[Byte], label: Byte)

object MNISTReader {

  def getBigEndian(x: Array[Byte]) =
    ByteBuffer.wrap(x).order(ByteOrder.BIG_ENDIAN).getInt()

  def read(imageFileName: String, labelFileName: String): Iterator[MNISTSample] = {

    val imageFile = Local.File(imageFileName).inputStream
    val labelFile = Local.File(labelFileName).inputStream

    val imageFileMagicNumber = new Array[Byte](4)
    imageFile.read(imageFileMagicNumber)
    assert(
      ByteBuffer.wrap(imageFileMagicNumber).order(ByteOrder.BIG_ENDIAN).getInt() == 0x00000803,
      "Image file magic number test failed."
    )

    val labelFileMagicNumber = new Array[Byte](4)
    labelFile.read(labelFileMagicNumber)
    assert(
      ByteBuffer.wrap(labelFileMagicNumber).order(ByteOrder.BIG_ENDIAN).getInt() == 0x00000801,
      "Label file magic number test failed."
    )

    val imageFileSize = new Array[Byte](4)
    imageFile.read(imageFileSize)
    val labelFileSize = new Array[Byte](4)
    labelFile.read(labelFileSize)

    val n = ByteBuffer.wrap(imageFileSize).order(ByteOrder.BIG_ENDIAN).getInt()
    assert(
      n == ByteBuffer.wrap(labelFileSize).order(ByteOrder.BIG_ENDIAN).getInt(),
      "Image file size and label file size do not match."
    )

    (imageFile.grouped(784) zip labelFile) map { case (i, l) =>
      MNISTSample(i.toArray, l)
    }

  }

  def main(args: Array[String]) = {
    val samples = read(
      imageFileName = "/Users/tongfei/my/data/mnist/train-images-idx3-ubyte",
      labelFileName = "/Users/tongfei/my/data/mnist/train-labels-idx1-ubyte"
    )

    class In extends Dim; val In = new In
    class Hidden extends Dim; val Hidden = new Hidden
    class Out extends Dim; val Out = new Out

    val x = Input[FloatTensor[In]]()
    val y = Input[FloatTensor[Out]]()

    val Layer1 = Affine(In -> 784, Out -> 100)
    val Layer2 = Affine(Hidden -> 100, Out -> 10)

    val ŷ = x |> Layer1 |> Softmax
    val loss = CrossEntropy(y, ŷ)

    /** Declare an optimizer. */
    val opt = new AdamOptimizer(0.01)

    /** Start running! */
    for (epoch <- 0 until 1000) {
      var averageLoss = 0f

      // For each sample
      for (sample <- samples) {

        given (
          x := FloatTensor.fromFlatArray[In](sample.image.map(_.toFloat), Array(784)),
          y := FloatTensor.fromFlatArray[Out](Array.tabulate(10)(l => if (l == sample.label.toInt) 1f else 0f), Array(10))
        ) { implicit computation =>

          val lossValue = loss.value
          averageLoss += lossValue
          opt.step(loss)

        }

      }

      println(s"Epoch $epoch: loss = ${averageLoss / 60000.0}")
    }


  }

}

*/
