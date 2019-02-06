package nexus.workflow

import nexus._

/**
 * Hyperparameters dimension.
 * @author Tongfei Chen
 */
class HDim private(val pairs: Map[String, String]) extends Dim

object HDim {

  def apply(keys: String*) = new HDim(keys.map(s => (s, s)).toMap)

  def apply(keyValuePairs: (String, String)*) = new HDim(keyValuePairs.toMap)

}
