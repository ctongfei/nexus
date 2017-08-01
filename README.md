### nexus
A prototype of a typeful & typesafe deep learning system that strives to be different

A simple neural network for learning the XOR function can be found [**here**](https://github.com/ctongfei/nexus/blob/master/src/test/scala/nexus/XorTest.scala).

Building a typesafe XOR network:
```scala
  val x = Input[DenseTensor[Float, In::$]]()  // input vectors
  val y = Input[DenseTensor[Float, Out::$]]() // gold labels

  val Layer1 = Affine(In -> 2, Hidden -> 2)   // type: Module[Tensor[Float, In::$], Tensor[Float, Hidden::$]]
  val Layer2 = Affine(Hidden -> 2, Out -> 2)  // type: Module[Tensor[Float, Hidden::$], Tensor[Float, Out::$]]
  val hidden = x |> Layer1 |> Sigmoid         // type: Expr[Tensor[Float, Hidden::$]]
  val output = hidden |> Layer2 |> Softmax    // type: Expr[Tensor[Float, Out::$]]
  val loss   = LogLoss(output, y)             // type: Expr[Tensor[Float, $]]
```

Design goals:

 - **Typeful**. Each axis of a tensor is statically typed using `HList`s. For example, an image is typed as `Tensor[Float, Width::Height::Channel::$]`, whereas a sentence in which each word is mapped to an embedding is typed as `Tensor[Float, Word::Embedding::$]`. Free programmers from remembering what each axis stands for.
 - **Typesafe**.  Very strong static type checking to eliminate most bugs at compile time.
 - **[TODO] Automatic batching over sequences/trees**. Free programmers from the pain of manual batching.
 - **[TODO] GPU Acceleration**. Reuse `Torch` C++ core.
 - **[TODO] Differentiation through any `HList`/`Coproduct`**.
 