package nexus.modules

import nexus._

/**
 * @author Tongfei Chen
 */
package object recurrent {

  type RecurrentUnit[S, X] = Func2[S, X, S]

}
