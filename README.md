### nexus
A prototype of a typeful & typesafe deep learning system that strives to be different

A simple neural network for learning the XOR function can be found [**here**](https://github.com/ctongfei/nexus/blob/master/core/src/test/scala/nexus/XorTest.scala).

Building a typesafe XOR network:
```scala
  class In;     val In = new In          
  class Hidden; val Hidden = new Hidden
  class Out;    val Out = new Out      // tensor axis labels declared as types and singletons

  val x = Input[FloatTensor[In::$]]()  // input vectors
  val y = Input[FloatTensor[Out::$]]() // gold labels

  val ŷ = x                       |>   // type: Expr[FloatTensor[In::$]]
    Affine(In -> 2, Hidden -> 2)  |>   // type: Expr[FloatTensor[Hidden::$]]
    Sigmoid                       |>   // type: Expr[FloatTensor[Hidden::$]]
    Affine(Hidden -> 2, Out -> 2) |>   // type: Expr[FloatTensor[Out::$]]
    Softmax                            // type: Expr[FloatTensor[Out::$]]
  val loss = CrossEntropy(y, ŷ)        // type: Expr[Float]
``` 

Design goals:

 - **Typeful**. Each axis of a tensor is statically typed using `HList`s. For example, an image is typed as `FloatTensor[Width::Height::Channel::$]`, whereas an embedded sentence is typed as `FloatTensor[Word::Embedding::$]`. This frees programmers from remembering what each axis stands for.
 - **Typesafe**.  Very strong static type checking to eliminate most bugs at compile time.
 - **Never, ever specify axis index again**. For things like `reduce_sum(x, axis=1)`, write `x |> SumAlong(AxisName)`.
 - **Mixing differentiable code with non-differentiable code**.
 - **Automatic typeclass derivation**: Differentiation through any case class (product type).
 - **[TODO] Automatic batching over sequences/trees** (Neubig, Goldberg, Dyer, NIPS 2017). Free programmers from the pain of manual batching.
 - **[TODO] GPU Acceleration**. Reuse `Torch` C++ core through Swig [(bindings)](https://github.com/ctongfei/torch-swig-java).
 - **[TODO] Multiple backends**. Torch / MXNet / ?
 - **[TODO] Automatic operator fusion for optimization.**
 - **[TODO] Typesafe higher-order gradients**.
 
### Reference
Please cite this in academic work as

Tongfei Chen (2017): [Typesafe Abstractions for Tensor Operations](https://arxiv.org/abs/1710.06892). In _Proceedings of the 8th ACM SIGPLAN International Symposium on Scala_, pp. 45-50.

```TeX
@inproceedings{chen2017typesafe,
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
