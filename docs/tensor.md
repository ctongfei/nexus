# Typesafe Tensors

The distinctive feature of Nexus is **typesafe** tensors.


In Nexus, a tensor is typed using a tuple of axis labels.

``` scala
val image: FloatTensor[(Width, Height, Channel)]
```


This is in contrast with common deep learning libraries, where multidimensional arrays (tensors) all belong to one type (given the element type). For example, a tensor with float is typed as `numpy.ndarray` in NumPy and `torch.FloatTensor` in PyTorch, no matter how many dimensions are there (rank) in the tensor, or what each axis means in the tensor.
