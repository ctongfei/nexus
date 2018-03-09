package nexus.impl.torch.cpu

import jtorch.cpu._
import nexus.algebra._

/**
 * @author Tongfei Chen
 */
object UntypedFloat32Tensor extends IsUntypedRealTensor[THFloatTensor, Float] {

  val R = nexus.algebra.instances.Float32

  implicit class THFloatTensorOps(val a: THFloatTensor) extends AnyVal {
    def get(i: Int) = TH.floatArray_getitem(a.getStorage.getData, i)
    def set(i: Int, v: Float) = TH.floatArray_setitem(a.getStorage.getData, i, v)
    def shape = nativeI64ArrayToJvm(a.getSize, a.getNDimension).map(_.toInt)
    def shape_=(s: Array[Int]) = a.setSize(jvmLongArrayToNative(s.map(_.toLong)))
    def strides = nativeI64ArrayToJvm(a.getStride, a.getNDimension).map(_.toInt)
    def strides_=(s: Array[Int]) = a.setStride(jvmLongArrayToNative(s.map(_.toLong)))
    def offset = a.getStorageOffset
    def offset_=(o: Int) = a.setStorageOffset(o)
    def rank = a.getNDimension
    def rank_=(n: Int) = a.setNDimension(n)
    def copy: THFloatTensor = TH.THFloatTensor_newClone(a)
  }

  def fromFlatArray(array: Array[Float], shape: Array[Int]) = {
    val data = jvmFloatArrayToNative(array)
    val s = TH.THFloatStorage_newWithData(data, array.length)
    val h = TH.THFloatTensor_newWithStorage(
      s,
      0,
      TH.THLongStorage_newWithData(jvmLongArrayToNative(shape.map(_.toLong)), shape.length),
      TH.THLongStorage_newWithData(jvmLongArrayToNative(shape.scanRight(1)(_ * _).tail.map(_.toLong)), shape.length)
    )
    h
  }

  def unwrapScalar(x: THFloatTensor) = ???

  def wrapScalar(x: Float) = ???

  def zeroBy(x: THFloatTensor) =
    TH.THFloatTensor_newWithTensor(x)

  def copy(a: THFloatTensor) =
    TH.THFloatTensor_newClone(a)

  def add(a: THFloatTensor, b: THFloatTensor) = {
    val c = copy(a)
    TH.THFloatTensor_cadd(c, a, 1f, b)
    c
  }

  def addI(x1: THFloatTensor, x2: THFloatTensor) = ???
  def addS(x1: THFloatTensor, x2: Float) = ???

  def sub(x1: THFloatTensor, x2: THFloatTensor) = {
    val c = copy(x1)
    TH.THFloatTensor_csub(c, x1, 1f, x2)
    c
  }

  def neg(x: THFloatTensor) = {
    val c = zeroBy(x)
    TH.THFloatTensor_neg(c, x)
    c
  }

  def eMul(x1: THFloatTensor, x2: THFloatTensor) = {
    val c = copy(x1)
    TH.THFloatTensor_mul(c, x2, 1f)
    c
  }

  def eDiv(x1: THFloatTensor, x2: THFloatTensor) = ???
  def scale(x: THFloatTensor, k: Float) = ???
  def eInv(x: THFloatTensor) = ???
  def eSqr(x: THFloatTensor) = ???
  def eSqrt(x: THFloatTensor) = ???
  def log(x: THFloatTensor) = ???
  def exp(x: THFloatTensor) = ???
  def log1p(x: THFloatTensor) = ???
  def expm1(x: THFloatTensor) = ???
  def sin(x: THFloatTensor) = ???
  def cos(x: THFloatTensor) = ???
  def tan(x: THFloatTensor) = ???
  def sum(x: THFloatTensor) = ???
  def sigmoid(x: THFloatTensor) = ???
  def reLU(x: THFloatTensor) = ???
  def isPos(x: THFloatTensor) = ???
  def transpose(x: THFloatTensor) = ???
  def mmMul(x: THFloatTensor, y: THFloatTensor) = ???
  def mvMul(x: THFloatTensor, y: THFloatTensor) = ???
  def vvMul(x: THFloatTensor, y: THFloatTensor) = ???
  def dot(x: THFloatTensor, y: THFloatTensor) = ???
  def tMul(x: THFloatTensor, y: THFloatTensor, matchedIndices: Seq[(Int, Int)]) = ???
  def mutable = ???
  def addS(x1: THFloatTensor, x2: Double) = ???
  def map(x: THFloatTensor)(f: (Float) => Float) = ???
  def map2(x1: THFloatTensor, x2: THFloatTensor)(f: (Float, Float) => Float) = ???
  def map3(x1: THFloatTensor, x2: THFloatTensor, x3: THFloatTensor)(f: (Float, Float, Float) => Float) = ???
  def abs(x: THFloatTensor) = ???
  def sgn(x: THFloatTensor) = ???


  def expandDim(x: THFloatTensor, i: Int) = ???
}
