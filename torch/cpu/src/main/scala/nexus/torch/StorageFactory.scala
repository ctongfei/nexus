package nexus.torch

/**
 * @author Tongfei Chen
 */
abstract class StorageFactory[@specialized(Boolean, Byte, Short, Int, Long, Float, Double) E, S] {

  def fromArray(a: Array[E]): S

}
