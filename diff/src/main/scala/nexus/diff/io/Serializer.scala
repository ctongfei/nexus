package nexus.diff.io

import nexus.diff._

/**
 * @author Tongfei Chen
 */
trait Serializer {

  def save(map: Map[String, Param[_]], f: String): Unit
  def load(f: String): Map[String, Param[_]]

}
