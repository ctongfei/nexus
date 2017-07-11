# nexus
A typeful & typesafe deep learning system that strives to be different

Design goals:

 - **Typeful**. Each axis of a tensor is statically typed using `HList`s. For example, an image is typed as `Tensor[Float, Width::Height::Channel::$]`. Free programmers from remembering what each axis stands for.
 - **Typesafe**.  Very strong static type checking to eliminate most bugs at compile time.
 - **[TODO] Automatic batching over sequences/trees**. Free programmers from the pain of manual batching.
 - **[TODO] GPU Acceleration**. Reuse `Torch` C++ core.
 
