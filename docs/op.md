# Operators

In Nexus operators can be "grounded" or polymorphic.

### Basic elementary functions
| Op      | Type                    | Type requirement     | Function              |
|--------|--------------------------|----------------------|------------------|
|`Add`   |`(T[I], T[I]) => T[I]`    |                      |$z_i = x_i + y_i$ |
|`Neg`   |`T[I] => T[I]`            |                      |$y_i = -x_i$      |
|`Sub`   |`(T[I], T[I]) => T[I]`    |                      |$z_i = x_i - y_i$ |
|`Mul`   |`(T[I], T[I]) => T[I]`    |                      |$z_i = x_i \cdot y_i$ |
|`Inv`   |`T[I] => T[I]`            |                      |$y_i = \dfrac{1}{x_i}$      |
|`Div`   |`(T[I], T[I]) => T[I]`    |                      |$z_i = \dfrac{x_i}{y_i}$ |
|`Pow`   |                          |                      |                         |
|`Exp`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \exp(x_i)$      |
|`Expm1`  |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \exp(x_i) - 1$  |
|`Log`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \log(x_i)$      |
|`Log1p`  |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \log(1 + x_i)$  |
|`Lerp`   |`(T[I], T[I], R) => T[I]`| `IsRealTensor[T, R]` |$y_i = a_i + w(b_i - a_i)$ |

### Activation functions

| Op      | Type                    | Type requirement     | Function              |
|--------|--------------------------|----------------------|------------------|
|`Sigmoid`|`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \sigma(x_i) = \dfrac{1}{1 + e^{-x_i}}$ |
|`ReLU`   |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \max\{0, x_i\}$    |
|`SELU`   |                         |                      |    |
|`GELU`   |                         |                      |    |



### Trigonometric / Hyperbolic functions
| Op      | Type                    | Type requirement     | Function              |
|---------|-------------------------|----------------------|-----------------------|
|`Sin`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \sin(x)$      |
|`Cos`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \cos(x)$      |
|`Tan`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \tan(x)$      |
|`ArcSin` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \arcsin(x)$   |
|`ArcCos` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \arccos(x)$   |
|`ArcTan` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \arctan(x)$   |
|`ATan2`  |`(T[I], T[I]) => T[I]`   | `IsRealTensor[T, R]` |$z = \mathrm{atan2}(y, x)$   |
|`Sinh`   |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \sinh(x) = \dfrac{1}{2}(e^x - e^{-x})$     |
|`Cosh`   |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \cosh(x) = \dfrac{1}{2}(e^x + e^{-x})$      |
|`Tanh`   |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = \tanh(x) = \dfrac{e^x - e^{-x}}{e^x + e^{-x}}$      |
|`ArSinh` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = {\rm arsinh}(x) = \log(x + \sqrt{x^2 + 1})$   |
|`ArCosh` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = {\rm arcosh}(x) = \log(x + \sqrt{x^2 - 1})$   |
|`ArTanh` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y = {\rm artanh}(x) = \dfrac{1}{2}\log\left(\dfrac{1+x}{1-x}\right)$   |
   

### Miscellaneous functions
| Op      | Type                    | Requirement          | Function              |
|---------|-------------------------|----------------------|-----------------------|
|`Abs`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \|x_i\|$|
|`Ceil`   |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \lceil x_i \rceil$      |
|`Floor`  |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \lfloor x_i \rfloor$      |
|`Frac`   |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = x_i - \lfloor x_i \rfloor$|
|`Clamp`  |`(T[I], R, R) => T[I]`   | `IsRealTensor[T, R]` |   |

### Special functions
| Op      | Type                    | Requirement          | Function              |
|---------|-------------------------|----------------------|-----------------------|
|`Digamma`|`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \psi(x_i) = \dfrac{{\rm d} \log \Gamma (x_i)}{{\rm d}x_i}$ |
|`Gamma`  |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = \Gamma(x_i) = \displaystyle\int_0^{+\infty}{t^{x_i - 1}e^{-t}{\rm d}t}$      |
|`Erf`    |`T[I] => T[I]`           | `IsRealTensor[T, R]` |$y_i = {\rm erf}(x_i) = \dfrac{2}{\sqrt{\pi}}\displaystyle\int_0^{x_i}{e^{-t^2}{\rm d}t}$  |
|`ErfInv` |`T[I] => T[I]`           | `IsRealTensor[T, R]` |   |


### Tensor manipulation functions
| Op           | Type                    | Requirement             | Function              |
|--------------|-------------------------|-------------------------|-----------------------|
|`ConcatAlong` |`(Seq[T[U]], X) => T[I]` |                         |                       |
|`Chunk`       |                         |                         |                       |
|`Squeeze`     |`(T[U], I) => T[V]`      | $U \setminus \{I\} = V$ |                       |
|`Stack`       |                         |                         |                       |
|`SwapAxes`    |`(T[U], I, J) => T[V]`   | $$

