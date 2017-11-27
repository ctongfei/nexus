### nexus
A prototype of a typeful & typesafe deep learning system that strives to be different

A simple neural network for learning the XOR function can be found [**here**](https://github.com/ctongfei/nexus/blob/master/core/src/test/scala/nexus/XorTest.scala).

Building a typesafe XOR network:
```scala
  val x = Input[DenseTensor[Float, In::$]]()  // input vectors
  val y = Input[DenseTensor[Float, Out::$]]() // gold labels

  val ŷ = x                       |>  // type: Expr[Tensor[Float, In::$]]
    Affine(In -> 2, Hidden -> 2)  |>  // type: Expr[Tensor[Float, Hidden::$]]
    Sigmoid                       |>  // type: Expr[Tensor[Float, Hidden::$]]
    Affine(Hidden -> 2, Out -> 2) |>  // type: Expr[Tensor[Float, Out::$]]
    Softmax                           // type: Expr[Tensor[Float, Out::$]]
  val loss = CrossEntropy(y, ŷ)       // type: Expr[Float]
```

Design goals:

 - **Typeful**. Each axis of a tensor is statically typed using `HList`s. For example, an image is typed as `Tensor[Float, Width::Height::Channel::$]`, whereas a sentence in which each word is mapped to an embedding is typed as `Tensor[Float, Word::Embedding::$]`. Free programmers from remembering what each axis stands for.
 - **Typesafe**.  Very strong static type checking to eliminate most bugs at compile time.
 - **Never, ever specify axis index again**. For things like `reduce_sum(x, axis=1)`, write `x |> SumAlong(AxisName)`.
 - **Mixing differentiable code with non-differentiable code**.
 - **Automatic typeclass derivation**: Differentiation through any case class (product type).
 - **[TODO] Automatic batching over sequences/trees**. Free programmers from the pain of manual batching.
 - **[TODO] GPU Acceleration**. Reuse `Torch` C++ core through Swig [(bindings)](https://github.com/ctongfei/torch-swig-java).
 - **[TODO] Multiple backends**. Torch / DyNet.
 - **[TODO] Distributed training**.
 - **[TODO] Typesafe higher-order gradients**.
 
### Reference
Please cite this in academic work as
```TeX
@inproceedings{Chen:2017:TAT:3136000.3136001,
 author = {Chen, Tongfei},
 title = {Typesafe Abstractions for Tensor Operations (Short Paper)},
 booktitle = {Proceedings of the 8th ACM SIGPLAN International Symposium on Scala},
 series = {SCALA 2017},
 year = {2017},
 pages = {45--50},
 url = {http://doi.acm.org/10.1145/3136000.3136001},
 doi = {10.1145/3136000.3136001}
}
```
