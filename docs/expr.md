# Symbolic expressions

The core data type in Nexus is `Symbolic[A]`: symbolic expressions. A `Symbolic[A]` is a symbolic expression that conceptually holds data of type `A`.

???+ "Analogous types in other libraries"
    `Expr` is similar to

      - `tf.Tensor`: 
      - `torch.autograd.Variable` (before PyTorch 0.4.0): 

An expression `Symbolic[A]` in Nexus can be one of the following:

  - `Input[A]`: Symbolic input of type `A`.
  - `Const[A]`: Constant.
  - `Param[A]`: A parameter that will be updated while training.
  - `AppN[A]` (`N` = {1, 2, 3}): Result of applying a $N$-ary function.
  