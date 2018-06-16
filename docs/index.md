# Nexus: Typesafe deep learning in Scala

Nexus is a prototypical typesafe deep learning system in Scala.

Nexus is a departure from common deep learning libraries such as [TensorFlow](tensorflow.org), [PyTorch](pytorch.org), [Theano](http://www.deeplearning.net/software/theano/), [MXNet](mxnet.io), etc. 

 - Ever been baffled by the axes of tensors? Which axis should I max out? 
 - Ever got `TypeError`s in Python?
 - Ever spending hours or days getting the tensors' axes and dimensions right?

Nexus' answer to these problems is **static types**. By specifying tensor axes' semantics in  types exploiting Scala's expressive types, compilers can validate the program **at compile time**, freeing developers' burden of remembering axes by heart, and eliminating nearly all errors above before even running.
 
Nexus embraces **declarative** and **functional** programming: Neural networks are built using composable components, making code very easy to follow, understand and maintain.

### Citation

Please cite this project in academic work as follows.

Tongfei Chen (2017): [Typesafe Abstractions for Tensor Operations](https://arxiv.org/abs/1710.06892). In _Proceedings of the 8th ACM SIGPLAN International Symposium on Scala_, pp. 45-50.

``` bib
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
