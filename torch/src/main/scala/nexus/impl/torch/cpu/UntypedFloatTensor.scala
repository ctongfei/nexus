package nexus.impl.torch.cpu

import jtorch.cpu._
import nexus._
import nexus.algebra._
import nexus.exception._

import scala.reflect._

sealed abstract class UntypedFloatTensor extends UntypedTensor[Float] {
  import UntypedFloatTensor._
  def stringRepr: String = rank match {
    case 0 =>
      this match {
        case Dim0(x) => x.toString
      }
    case 1 =>
      (0 until shape(0)).map { i => get(offset + i * strides(0)) }.mkString("[", ", \t", "]")
    case _ =>
      (0 until shape(0)).map { i => slice(this, 0, i).stringRepr }.mkString("[", "\n", "]")
  }
}

/**
 * Wraps around Torch native CPU tensor operations.
 * @author Tongfei Chen
 */
object UntypedFloatTensor extends IsUntypedRealTensor[UntypedFloatTensor, Float] {

  val R = nexus.algebra.instances.Float32

  def mutable = true

  case class Dim0(var value: Float) extends UntypedFloatTensor {
    def get(i: Int) =
      if (i == 0) value
      else throw new ArrayIndexOutOfBoundsException

    def set(i: Int, v: Float): Unit =
      if (i == 0) value = v
      else throw new ArrayIndexOutOfBoundsException

    def shape = Array[Int]()

    def shape_=(s: Array[Int]): Unit =
      if (s.length != 0) throw new RankMismatchException

    def strides = Array[Int]()
    def strides_=(s: Array[Int]): Unit =
      throw new UnsupportedOperationException

    def offset = 0
    def offset_=(o: Int): Unit =
      throw new UnsupportedOperationException

    def rank = 0
    def rank_=(n: Int32): Unit =
      throw new UnsupportedOperationException
  }

  case class Dense(th: THFloatTensor) extends UntypedFloatTensor {

    def get(i: Int) = TH.floatArray_getitem(th.getStorage.getData, i)
    def set(i: Int, v: Float): Unit = TH.floatArray_setitem(th.getStorage.getData, i, v)
    def shape = Util.nativeI64ArrayToJvm(th.getSize, th.getNDimension).map(_.toInt)
    def shape_=(s: Array[Int32]): Unit = ???
    def strides = ???
    def strides_=(s: Array[Int32]): Unit = ???
    def offset = ???
    def offset_=(o: Int32): Unit = ???
    def rank = ???
    def rank_=(n: Int32): Unit = ???
    // TODO: garbage collection?
    override def finalize(): Unit = {
      TH.THFloatTensor_free(th)
      super.finalize()
    }
  }

  //TODO: Sparse tensor support through Torch
  //TODO: case class Sparse(th: THSFloatTensor) extends UntypedFloat32Tensor



  implicit val elementTypeClassTag = ClassTag.Float

  def get(x: UntypedFloatTensor, is: Array[Int]) = ???

  def fromFlatArray(array: Array[Float], shape: Array[Int]): UntypedFloatTensor = {
    if (array.length == 1 && shape.length == 0)
      Dim0(array(0))
    else {
      val data = Util.jvmFloatArrayToNative(array)
      val s = TH.THFloatStorage_newWithData(data, array.length)
      val h = TH.THFloatTensor_newWithStorage(
        s,
        0,
        TH.THLongStorage_newWithData(Util.jvmLongArrayToNative(shape.map(_.toLong)), shape.length),
        TH.THLongStorage_newWithData(Util.jvmLongArrayToNative(shape.scanRight(1)(_ * _).tail.map(_.toLong)), shape.length)
      )
      Dense(h)
    }
  }

  def unwrapScalar(x: UntypedFloatTensor) = x match {
    case Dim0(v) => v
    case _ => throw new NotAZeroDimTensorException(x)
  }

  def wrapScalar(x: Float) = Dim0(x)

  def rank(x: UntypedFloatTensor) = x match {
    case Dim0(_) => 0
    case Dense(x) => x.getNDimension
  }

  def shape(x: UntypedFloatTensor) = x match {
    case Dim0(_) => Array[Int]()
    case Dense(x) => Util.nativeI64ArrayToJvm(x.getSize, x.getNDimension).map(_.toInt)
  }

  def zeroBy(x: UntypedFloatTensor) = x match {
    case Dim0(_) => Dim0(0f)
    case Dense(x) =>
      val r = TH.THFloatTensor_newWithTensor(x)
      TH.THFloatTensor_zero(r)
      Dense(r)
  }

  def copy(a: UntypedFloatTensor) = a match {
    case Dim0(v) => Dim0(v)
    case Dense(x) => TH.THFloatTensor_newClone(x)
  }

  override def add(a: UntypedFloatTensor, b: UntypedFloatTensor) = (a, b) match {
    case (Dim0(a), Dim0(b)) => Dim0(a + b)
    case (Dense(a), Dense(b)) =>
      val c = TH.THFloatTensor_newClone(a)
      TH.THFloatTensor_add(c, b, 1f)
      Dense(c)
  }

  def addI(a: UntypedFloatTensor, b: UntypedFloatTensor) = (a, b) match {
    case (a @ Dim0(_), b @ Dim0(_)) => a.value += b.value
    case (Dense(a), Dense(b)) =>
      TH.THFloatTensor_add(a, b, 1f)
  }

  def addS(a: UntypedFloatTensor, b: Float) = a match {
    case Dim0(a) => Dim0(a + b)
    case Dense(a) =>
      val c = TH.THFloatTensor_new()
      TH.THFloatTensor_zerosLike(c, a)
      TH.THFloatTensor_add(c, a, b)
      Dense(c)
  }

  def sub(a: UntypedFloatTensor, b: UntypedFloatTensor) = (a, b) match {
    case (Dim0(a), Dim0(b)) => Dim0(a - b)
    case (Dense(a), Dense(b)) =>
      val c = TH.THFloatTensor_newClone(a)
      TH.THFloatTensor_sub(c, b, 1f)
      Dense(c)
  }

  def neg(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(-x)
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_neg(y, x)
      Dense(y)
  }

  def eMul(a: UntypedFloatTensor, b: UntypedFloatTensor) = (a, b) match {
    case (Dim0(a), Dim0(b)) => Dim0(a * b)
    case (Dense(a), Dense(b)) =>
      val c  = TH.THFloatTensor_newClone(a)
      TH.THFloatTensor_mul(c, b, 1f)
      Dense(c)
  }

  def eDiv(a: UntypedFloatTensor, b: UntypedFloatTensor) = (a, b) match {
    case (Dim0(a), Dim0(b)) => Dim0(a / b)
    case (Dense(a), Dense(b)) =>
      val c = TH.THFloatTensor_newClone(a)
      TH.THFloatTensor_div(a, b, 1f)
      Dense(c)
  }

  def scale(x: UntypedFloatTensor, k: Float) = x match {
    case Dim0(x) => Dim0(x * k)
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_zero(y)
      TH.THFloatTensor_add(y, x, k)
      Dense(y)
  }

  def eInv(x: UntypedFloatTensor) = ???

  def eSqr(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(x * x)
    case x @ Dense(_) => eMul(x, x)
  }

  def eSqrt(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.sqrt(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_sqrt(y, x)
      Dense(y)
  }

  def eLog(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.log(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_log(y, x)
      Dense(y)
  }

  def eExp(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.exp(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_exp(y, x)
      Dense(y)
  }

  def eLog1p(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.log1p(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_log1p(y, x)
      Dense(y)
  }

  def eExpm1(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.expm1(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_expm1(y, x)
      Dense(y)
  }

  def eSin(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.sin(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_sin(y, x)
      Dense(y)
  }

  def eCos(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.cos(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_cos(y, x)
      Dense(y)
  }

  def eTan(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.tan(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_tan(y, x)
      Dense(y)
  }

  def sum(x: UntypedFloatTensor) = x match {
    case Dim0(x) => x
    case Dense(x) =>
      val y = TH.THFloatTensor_sumall(x)
      y.toFloat
  }

  def eSigmoid(x: UntypedFloatTensor) = x match {
    case Dim0(x) => ???
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_sigmoid(y, x)
      Dense(y)
  }

  def eReLU(x: UntypedFloatTensor) = x match {
    case Dim0(x) => ???
    case Dense(x) => ???
  }

  def eAbs(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.abs(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_abs(y, x)
      Dense(y)
  }

  def eSgn(x: UntypedFloatTensor) = x match {
    case Dim0(x) => Dim0(R.sgn(x))
    case Dense(x) =>
      val y = TH.THFloatTensor_newClone(x)
      TH.THFloatTensor_sign(y, x)
      Dense(y)
  }

  def eIsPos(x: UntypedFloatTensor) = ???

  def mmMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = (x, y) match {
    case (Dense(tx), Dense(ty)) =>
      val z = TH.THFloatTensor_newWithSize2d(x.shape(0), y.shape(1))
      TH.THFloatTensor_zero(z)
      TH.THFloatTensor_addmm(z, 1f, z, 1f, tx, ty)
      Dense(z)
    case _ => throw new RankMismatchException
  }

  def mvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = (x, y) match {
    case (Dense(tx), Dense(ty)) =>
      val z = TH.THFloatTensor_newWithSize1d(x.shape(0))
      TH.THFloatTensor_zero(z)
      TH.THFloatTensor_addmv(z, 1f, z, 1f, tx, ty)
      Dense(z)
  }

  def vvMul(x: UntypedFloatTensor, y: UntypedFloatTensor) = (x, y) match {
    case (Dense(tx), Dense(ty)) =>
      val z = TH.THFloatTensor_newWithSize2d(x.shape(0), y.shape(0))
      TH.THFloatTensor_zero(z)
      TH.THFloatTensor_addr(z, 1f, z, 1f, tx, ty)
      Dense(z)
  }

  def dot(x: UntypedFloatTensor, y: UntypedFloatTensor) = (x, y) match {
    case (Dim0(x), Dim0(y)) => x * y
    case (Dense(x), Dense(y)) =>
      TH.THFloatTensor_dot(x, y).toFloat
  }

  def transpose(x: UntypedFloatTensor) = x match {
    case Dim0(_) => throw new NotATwoDimTensorException(x)
    case Dense(x) => Dense(TH.THFloatTensor_newTranspose(x, 1, 0))
  }

  def tMul(x: UntypedFloatTensor, y: UntypedFloatTensor, matchedIndices: Seq[(Int32, Int32)]) = ???
  def map(x: UntypedFloatTensor)(f: Float32 => Float32) = ???
  def map2(x1: UntypedFloatTensor, x2: UntypedFloatTensor)(f: (Float32, Float32) => Float32) = ???
  def map3(x1: UntypedFloatTensor, x2: UntypedFloatTensor, x3: UntypedFloatTensor)(f: (Float32, Float32, Float32) => Float32) = ???

  def expandDim(x: UntypedFloatTensor, i: Int32) = ???

  def slice(x: UntypedFloatTensor, dim: Int, i: Int): UntypedFloatTensor = ???


  def addS(x1: UntypedFloatTensor, x2: Float64) = addS(x1, x2.toFloat)

}
