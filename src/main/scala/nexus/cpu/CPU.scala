package nexus.cpu

import nexus._
import shapeless._

/**
 * @author Tongfei Chen
 */
class CPUFloat32 extends Env[UntypedDenseTensor, Float] {

  def map(x: UntypedDenseTensor[Float])(f: Float => Float): UntypedDenseTensor[Float] = {
    import x._
    val y = new Array[Float](size)
    var yi = 0
    var xi = offset
    var d = rank - 1
    val indices = Array.fill(rank)(0)
    while (yi < size) {
      y(yi) = f(handle(xi))
      xi += stride(d)
      indices(d) += 1
      if (indices(d) >= shape(d) && d > 0) {
        while (indices(d) >= shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          xi += stride(d) - (stride(d + 1) * shape(d + 1))
        }
        d = rank - 1
      }
      yi += 1
    }
    new UntypedDenseTensor.Contiguous[Float](y, shape)
  }

  def zipWith(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float])(f: (Float, Float) => Float): UntypedDenseTensor[Float] = {
    val y = new Array[Float](x1.size)
    var yi = 0
    var x1i = x1.offset
    var x2i = x2.offset
    var d = x1.rank - 1
    val indices = Array.fill(x1.rank)(0)
    while (yi < x1.size) {
      y(yi) = f(x1.handle(x1i), x2.handle(x2i))
      x1i += x1.stride(d)
      x2i += x2.stride(d)
      indices(d) += 1
      if (indices(d) >= x1.shape(d) && d > 0) {
        while (indices(d) >= x1.shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          x1i += x1.stride(d) - (x1.stride(d + 1) * x1.shape(d + 1))
          x2i += x2.stride(d) - (x2.stride(d + 1) * x2.shape(d + 1))
        }
        d = x1.rank - 1
      }
      yi += 1
    }
    new UntypedDenseTensor.Contiguous[Float](y, x1.shape)
  }

  def neg(x: UntypedDenseTensor[Float]) = map(x)(-_)
  def add(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_+_)
  def sub(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_-_)
  def eMul(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_*_)
  def eDiv(x1: UntypedDenseTensor[Float], x2: UntypedDenseTensor[Float]) = zipWith(x1, x2)(_/_)
}
