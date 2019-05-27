package nexus.diff

import cats._

/**
 * Typeclass witnessing a structure `S[_]` can be unrolled under the computation box `F[_]`.
 * For example, a traced sequence `Traced[Seq[A]]` can be unrolled to `Seq[Traced[A]]`.
 * @author Tongfei Chen
 */
trait Unroll[S[_], F[_]] {

  def unroll[X](ds: F[S[X]]): S[F[X]]

}

object Unroll {

  implicit def UnrollAnyId[S[_]]: Unroll[S, Id] = new Unroll[S, Id] {
    def unroll[X](fsx: S[X]): S[X] = fsx
  }

  implicit object UnrollIndexedSeqTraced extends Unroll[IndexedSeq, Traced] {
    def unroll[X](fsx: Traced[IndexedSeq[X]]) = {
      IndexedSeq.tabulate(fsx.value.length)(i => ???)
    }
  }

}
