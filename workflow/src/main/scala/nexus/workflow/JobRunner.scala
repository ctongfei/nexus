package nexus.workflow

/**
 * @author Tongfei Chen
 */
trait JobRunner {
  def run(job: Job): Boolean
}
