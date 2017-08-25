### nexus
A prototype of a typeful & typesafe deep learning system that strives to be different

A simple neural network for learning the XOR function can be found [**here**](https://github.com/ctongfei/nexus/blob/master/core/src/test/scala/nexus/XorTest.scala).

Building a typesafe XOR network:
```scala
  val x = Input[DenseTensor[Float, In::$]]()  // input vectors
  val y = Input[DenseTensor[Float, Out::$]]() // gold labels

  val yʹ = x                      |>  // type: Expr[Tensor[Float, In::$]]
    Affine(In -> 2, Hidden -> 2)  |>  // type: Expr[Tensor[Float, Hidden::$]]
    Sigmoid                       |>  // type: Expr[Tensor[Float, Hidden::$]]
    Affine(Hidden -> 2, Out -> 2) |>  // type: Expr[Tensor[Float, Out::$]]
    Softmax                           // type: Expr[Tensor[Float, Out::$]]
  val loss = CrossEntropy(y, yʹ)      // type: Expr[Float]
```

Design goals:

 - **Typeful**. Each axis of a tensor is statically typed using `HList`s. For example, an image is typed as `Tensor[Float, Width::Height::Channel::$]`, whereas a sentence in which each word is mapped to an embedding is typed as `Tensor[Float, Word::Embedding::$]`. Free programmers from remembering what each axis stands for.
 - **Typesafe**.  Very strong static type checking to eliminate most bugs at compile time.
 - **Never, ever specific axis index again**. For things like `reduce_sum(x, axis=1)`, write `x |> SumOut(AxisName)`.
 - **[TODO] Automatic batching over sequences/trees**. Free programmers from the pain of manual batching.
 - **[TODO] GPU Acceleration**. Reuse `Torch` C++ core through Swig [(bindings)](https://github.com/ctongfei/torch-swig-java).
 - **[TODO] Multiple backends**. Torch / DyNet.
 - **[TODO] Differentiation through any `HList`/`Coproduct`**: Or any case class (product type) / sealed traits (sum type).
 - **[TODO] Distributed training**.
 - **[TODO] Typesafe higher-order gradients**.
 
