package nexus.diff

import cats._

/**
 * Typeclass witnessing a structure `S[_]` can be unrolled under the computation box `D[_]`.
 * For example, a traced sequence `Traced[Seq[A]]` can be unrolled to `Seq[Traced[A]]`.
 * @author Tongfei Chen
 */
trait Unroll[S[_], D[_]] {

  def unroll[X](ds: D[S[X]]): S[D[X]]

}

object Unroll {

  implicit def UnrollAnyId[S[_]]: Unroll[S, Id] = new Unroll[S, Id] {
    def unroll[X](ds: S[X]): S[X] = ds
  }

  implicit object UnrollIndexedSeqTraced extends Unroll[IndexedSeq, Traced] {
    def unroll[X](ds: Traced[IndexedSeq[X]]) = {
      IndexedSeq.tabulate(ds.value.length)(i => ???)
    }
  }

}
