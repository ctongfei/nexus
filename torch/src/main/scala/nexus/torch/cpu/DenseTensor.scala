package nexus.torch.cpu

import jtorch._
import nexus._

/**
 * @author Tongfei Chen
 */
abstract class DenseTensor[D, A <: $$] extends TensorLike[D, A, DenseTensor[D, A]]
