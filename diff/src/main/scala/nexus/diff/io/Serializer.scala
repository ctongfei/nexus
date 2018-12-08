package nexus.diff.io

import nexus.diff._

/**
 * @author Tongfei Chen
 */
trait Serializer {

  def save(module: HasParameters, f: String): Unit
  def load(module: HasParameters, f: String): Unit

}
