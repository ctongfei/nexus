package nexus.jvm

import nexus._
import nexus.syntax._
import nexus.typelevel._
import nexus.util._
import shapeless._

import scala.reflect._
import scala.util._

abstract class JvmIsRealTensorK[R, T[a] <: Tensor[R, a]]
(implicit
  val R: IsReal[R],
  val elementClassTag: ClassTag[R]
) extends IsRealTensorK[T, R] {

  def newTensor[A](handle: Array[R], shape: Array[Int], stride: Array[Int], offset: Int): T[A]

  def scalar(e: R): T[Unit] = newTensor(
    handle = Array(e),
    shape = Array(),
    stride = Array(),
    offset = 0
  )

  def fromFlatArray[A](arr: Array[R], sh: Array[Int]): T[A] = newTensor(
    handle = arr,
    shape = sh,
    stride = sh.scanRight(1)(_*_).tail,
    offset = 0
  )

  def fromFlatArray[A](axes: A, sh: Array[Int], arr: Array[R]): T[A] =
    fromFlatArray(arr, sh)

  def fromNestedArray[Arr, N <: Nat, A](axes: A)(arr: Arr)(implicit nest: Nest.Aux[Arr, R, N], nn: Len.Aux[A, N]) =
    fromFlatArray[A](nest.flatten(arr), nest.shape(arr))

  def newTensor[A](shape: Array[Int]): T[A] =
    fromFlatArray(Array.ofDim[R](shape.product), shape)

  def newGaussianTensor[A](μ: Double, σ2: Double, shape: Array[Int]) = {
    val r = new Random()
    val σ = java.lang.Math.sqrt(σ2)
    fromFlatArray(
      Array.fill(ShapeUtils.product(shape))(R.fromDouble((r.nextGaussian() - μ) * σ)),
      shape
    )
  }

  def zeroBy[A](x: T[A]) = newTensor(x.shape)

  def add[A](x: T[A], y: T[A]) = map2(x, y)(R.add)
  def addInplace[A](x: T[A], y: T[A]) = { map2Inplace(x, y)(R.add); x }
  def addScalar[A](x: T[A], u: R) = map(x)(R.add(_, u))
  def neg[A](x: T[A]) = map(x)(R.neg)
  def sub[A](x: T[A], y: T[A]) = map2(x, y)(R.sub)
  def subScalar[A](x: T[A], u: R) = addScalar(x, R.neg(u))
  def mul[A](x: T[A], y: T[A]) = map2(x, y)(R.mul)
  def div[A](x: T[A], y: T[A]) = map2(x, y)(R.div)
  def scale[A](x: T[A], u: R) = map(x)(R.mul(_, u))
  def inv[A](x: T[A]) = map(x)(R.inv)
  def sqr[A](x: T[A]) = map(x)(R.sqr)
  def sqrt[A](x: T[A]) = map(x)(R.sqrt)
  def log[A](x: T[A]) = map(x)(R.log)
  def exp[A](x: T[A]) = map(x)(R.exp)
  def log1p[A](x: T[A]) = map(x)(R.log1p)
  def expm1[A](x: T[A]) = map(x)(R.expm1)
  def sin[A](x: T[A]) = map(x)(R.sin)
  def cos[A](x: T[A]) = map(x)(R.cos)
  def tan[A](x: T[A]) = map(x)(R.tan)
  def logistic[A](x: T[A]) = inv(addScalar(exp(neg(x)), R.fromDouble(1))) // TODO: optimize
  def relu[A](x: T[A]) = ???
  def abs[A](x: T[A]) = map(x)(R.abs)
  def sgn[A](x: T[A]) = map(x)(R.sgn)
  def pos[A](x: T[A]) = ???


  def arcsin[I](x: T[I]) = map(x)(R.arcsin)
  def arccos[I](x: T[I]) = map(x)(R.arccos)
  def arctan[I](x: T[I]) = map(x)(R.arctan)

  def rank[I](x: T[I]) = x.shape.length
  def sizeOfDim[I](x: T[I], dim: Int) = x.shape(dim)
  def sum[A](x: T[A]) = x.handle.fold(R.zero)(R.add) // TODO: wrong
  def product[A](x: T[A]) = ???

  def transpose[A, B](x: T[(A, B)]): T[(B, A)] =
    newTensor(
     handle = x.handle,
     shape = Array(x.shape(1), x.shape(0)),
     stride = Array(x.stride(1), x.stride(0)),
     offset = 0
    )

  def matMul[A, B, C](x: T[(A, B)], y: T[(B, C)]) = ???

  def mvMul[A, B](x: T[(A, B)], y: T[B]): T[A] = {
    val z = Array.fill(x.shape(0))(R.zero)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until x.shape(1))
        z(i) += x(i, j) * y(j)
    fromFlatArray[A](z, Array(x.shape(0)))
  }

  def vvMul[A, B](x: T[A], y: T[B]): T[(A, B)] = {
    val z = Array.fill(x.shape(0) * y.shape(0))(R.zero)
    for (i <- 0 until x.shape(0))
      for (j <- 0 until y.shape(0))
        z(i * y.shape(0) + j) = x(i) * y(j)
    fromFlatArray[(A, B)](z, Array(x.shape(0), y.shape(0)))
  }

  def dot[A](x: T[A], y: T[A]) = ???
  def contract[A, B, C](x: T[A], y: T[B])(implicit sd: SymDiff.Aux[A, B, C]) = ???

  def get[A](x: T[A], is: Seq[Int]) = x.apply(is: _*)
  def set[A](x: T[A], is: Seq[Int], v: R): Unit = x.update(is: _*)(v)

  def newTensor[A](shape: Seq[Int]) = fromFlatArray[A](Array.ofDim[R](shape.product), shape.toArray)

  def fromFlatArray[A](array: Array[R], shape: Seq[Int]) = fromFlatArray[A](array, shape.toArray)

  def wrapScalar(x: R): T[Unit] = newTensor(
    handle = Array(x),
    shape = Array(),
    stride = Array(),
    offset = 0
  )

  def unwrapScalar(x: T[Unit]) = x.handle(x.offset)

  //TODO: map functions below should be macros
  def map[A](x: T[A])(f: R => R): T[A] = {
    if (x.rank == 0) return wrapScalar(f(x())).asInstanceOf[T[A]] // cast is safe
    val y = Array.ofDim[R](x.size)
    var yi = 0
    var xi = x.offset
    var d = x.rank - 1
    val indices = Array.fill(x.rank)(0)
    while (yi < x.size) {
      y(yi) = f(x.handle(xi))
      xi += x.stride(d)
      indices(d) += 1
      if (indices(d) >= x.shape(d) && d > 0) {
        while (indices(d) >= x.shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          xi += x.stride(d) - (x.stride(d + 1) * x.shape(d + 1))
        }
        d = x.rank - 1
      }
      yi += 1
    }
    fromFlatArray[A](y, x.shape)
  }

  def map2[A](x: T[A], y: T[A])(f: (R, R) => R): T[A] = {
    if (x.rank == 0 && y.rank == 0) return wrapScalar(f(x(), y())).asInstanceOf[T[A]] // cast is safe
    val z = Array.ofDim[R](x.size)
    var zi = 0
    var xi = x.offset
    var yi = y.offset
    var d = x.rank - 1
    val indices = Array.fill(x.rank)(0)
    while (zi < x.size) {
      z(zi) = f(x.handle(xi), y.handle(yi))
      xi += x.stride(d)
      yi += y.stride(d)
      indices(d) += 1
      if (indices(d) >= x.shape(d) && d > 0) {
        while (indices(d) >= x.shape(d) && d > 0) {
          indices(d) = 0
          d -= 1
          indices(d) += 1
          xi += x.stride(d) - (x.stride(d + 1) * x.shape(d + 1))
          yi += y.stride(d) - (y.stride(d + 1) * y.shape(d + 1))
        }
        d = x.rank - 1
      }
      zi += 1
    }
    fromFlatArray[A](z, x.shape)
  }


  def map2Inplace[A](x: T[A], y: T[A])(f: (R, R) => R): Unit = {
    if (x.rank == 0 && y.rank == 0) {
      x.handle(x.offset) = f(x.handle(x.offset), y.handle(y.offset))
    }
    else {
      var zi = 0
      var xi = x.offset
      var yi = y.offset
      var d = x.rank - 1
      val indices = Array.fill(x.rank)(0)
      while (zi < x.size) {
        x.handle(xi) = f(x.handle(xi), y.handle(yi))
        xi += x.stride(d)
        yi += y.stride(d)
        indices(d) += 1
        if (indices(d) >= x.shape(d) && d > 0) {
          while (indices(d) >= x.shape(d) && d > 0) {
            indices(d) = 0
            d -= 1
            indices(d) += 1
            xi += x.stride(d) - (x.stride(d + 1) * x.shape(d + 1))
            yi += y.stride(d) - (y.stride(d + 1) * y.shape(d + 1))
          }
          d = x.rank - 1
        }
        zi += 1
      }
    }
  }
  
  def map3[A](x: T[A], y: T[A], x3: T[A])(f: (R, R, R) => R) = ???

  def sliceAlong[A, U, B](x: T[A], axis: U, i: Int)(implicit rx: Remove.Aux[A, U, B]) = newTensor(
    handle = x.handle,
    shape = ShapeUtils.removeAt(x.shape, rx.index),
    stride = ShapeUtils.removeAt(x.stride, rx.index),
    offset = x.offset + x.stride(rx.index) * i
  )

  def unstackAlong[A, U, B](x: T[A], axis: U)(implicit rx: Remove.Aux[A, U, B]) =
    (0 until x.shape(rx.index)) map { i => sliceAlong(x, axis, i) }

  def expandDim[A, I <: Nat, X <: Dim, B](x: T[A])(implicit ix: InsertAt.Aux[A, I, X, B]) = ???
  def renameAxis[A, B](x: T[A]): T[B] = x.asInstanceOf[T[B]]
}
