package nexus.diff.modules

import nexus.diff._

/**
 * @author Tongfei Chen
 */
package object recurrent {

  type RecurrentUnit[S, X] = Func2[S, X, S]

}
