package nexus.torch

import nexus._

/**
 * @author Tongfei Chen
 */
object NativeMemory extends Device {

  type Obj = NativeObject

  def registerNativeArray[E](array: NativeArray[E]) = {}

  def registerStorage[E](storage: Storage[E]) = {}

  def registerTensor[E](tensor: Tensor[E, _]) = {}

  def allocatedObjects = ???

  def totalMemoryUsage = ???

  def gc(): Unit = ???

}
