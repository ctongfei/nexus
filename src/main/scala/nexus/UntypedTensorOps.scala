package nexus

/**
 * @author Tongfei Chen
 */
class UntypedTensorOps[UT[_], D](val a: UT[D])(env: Env[UT, D]) {

  def +(b: UT[D]): UT[D] = env.add(a, b)
  def -(b: UT[D]): UT[D] = env.sub(a, b)
  def |*|(b: UT[D]): UT[D] = env.mul(a, b)
  def |/|(b: UT[D]): UT[D] = env.div(a, b)

  def sigmoid: UT[D] = env.sigmoid(a)


}

trait UntypedTensorOpsMixin {

  private[nexus] implicit class untypedTensorOps[UT[_], D](val a: UT[D])(implicit env: Env[UT, D])
    extends UntypedTensorOps[UT, D](a)(env)

}