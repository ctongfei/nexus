package nexus.layer

import nexus._

/**
 * @author Tongfei Chen
 */
package object recurrent {

  type RecurrentUnit[S, X] = Func2[S, X, S]

  type RecurrentUnitWithOutput[S, X, Y] = Func2[S, X, (S, Y)]

}
