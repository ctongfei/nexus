package nexus.diff

import nexus.diff.io.Serializer

/**
 * Base trait for anything that can be serialized (as a map of keys to parameters).
 * These include modules and optimizers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait HasParameters {

  /** The set of parameters in this module. */
  def parameterMap: Map[String, Param[_]]

  def loadFromParameterMap(m: Map[String, Param[_]]): Unit

  def save(filename: String, format: Serializer): Unit =
    format.save(parameterMap, filename)

  def load(filename: String, format: Serializer): Unit =
    this.loadFromParameterMap(format.load(filename))

}
