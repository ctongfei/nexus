package nexus.layer.recurrent

import nexus._

/**
 * @author Tongfei Chen
 */
trait RecurrentUnit[T[_, _ <: $$], D, S, I] extends Module2[T[D, S::$], T[D, I::$], T[D, S::$]]
