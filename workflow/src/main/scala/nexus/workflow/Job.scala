package nexus.workflow

import nexus.jvm.Tensor
import shapeless.ops.hlist.Union

/**
 * @author Tongfei Chen
 */
class Job private[workflow](val parents: Seq[Job], val script: String, val jobRunner: JobRunner = Job.defaultRunner) {
  def out = ???
  def withJobRunner(jobRunner: JobRunner) = new Job(this.parents, this.script, jobRunner)
}

object Job {

  object defaultRunner extends JobRunner {
    def run(job: Job) = ???
  }

}
abstract class Config[U] extends Tensor[String, U]

trait JobFamily[U] extends Tensor[Job, U] {

  def axes: U

  def configs: Config[U]

  def map(f: Job => Job): JobFamily[U]

  def foreach(f: Job => Unit) = ???

  def flatMap[V, W](f: Job => JobFamily[V])(implicit u: Union.Aux[U, V, W]): JobFamily[W]

}

object JobFamily {



}
