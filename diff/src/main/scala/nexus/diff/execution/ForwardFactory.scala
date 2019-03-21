package nexus.diff.execution

import nexus.diff._


trait ForwardFactory[D[_], F <: Forward[D]] {

  implicit def factory: ForwardFactory[D, F] = this

  /**
   * Creates a forward interpreter given the list of assignments.
   * @param inputs List of assignments
   * @return A new forward interpreter instance
   */
  def given(inputs: Assignment[D]*): F

}
