# Scalar Functions

Nexus supports the following basic math operations on scalar values by importing 
```scala
import nexus.math._
```

Beside having more functions than the standard package `scala.math`, Functions in `nexus.math` are **type-polymorphic**: 
It applies to all scalar types (`Float` / `Double` / `Int`) when applicable 
(i.e., the “type requirement” is satisfied for each function below).

### Basic constants
| Name   | Name/description                    |  Scala type              | Type requirement             | Value   |
|--------|-------------------------------------|--------------------------|------------------------------|---------|
|`zero`  | Zero (additive identity)            |`R`                       | `AdditiveMonoid[R]`          |$0$     |
|`one`   | One (multiplicative identity)       |`R`                       | `MultiplicativeMonoid[R]`    |$1$     |

### Basic arithmetic

| Op     | Name/description                    |  Scala type              | Type requirement             | Function   |
|--------|-------------------------------------|--------------------------|------------------------------|------------|
|`add`   | Addition                            |`(R, R) => R`             | `AdditiveSemigroup[R]`       |$z = x + y$ |
|`sub`   | Subtraction                         |`(R, R) => R`             | `AdditiveGroup[R]`           |$z = x - y$ |
|`neg`   | Negation (additive inverse)         |`R => R`                  | `AdditiveGroup[R]`           |$y = -x$    |
|`mul`   | Multiplication                      |`(R, R) => R`             | `MultiplicativeSemigroup[R]` |$z = x \cdot y$ |
|`div`   | Division                            |`(R, R) => R`             | `MultiplicativeGroup[R]`     |$z = \dfrac{x}{y}$ |
|`recip` | Reciprocal (multiplicative inverse) |`R => R`                  | `MultiplicativeGroup[R]`     |$y = \dfrac{1}{x}$ |
|`mod`   | Modulo                              |`(Z, Z) => Z`             | `IsInt[Z]`                   |$z = x\operatorname{mod} y$ |

### Power and root
| Op        | Name/description        |  Scala type              | Type requirement             | Function   |
|-----------|-------------------------|--------------------------|------------------------------|------------|
|`sqr`      | Square                  |`R => R`                  | `MultiplicativeSemigroup[R]` |$y = x^2$   |
|`cube`     | Cube                    |`R => R`                  | `MultiplicativeSemigroup[R]` |$y = x^3$   |
|`sqrt`     | Square root             |`R => R`                  | `IsReal[R]`                  |$y = \sqrt{x}$ |
|`cbrt`     | Cube root               |`R => R`                  | `IsReal[R]`                  |$y = \sqrt[3]{x}$ |
|`pow`      | Power                   |`(R, R) => R`             | `IsReal[R]`                  |$z = x^y$ |


### Absolute value and sign
| Op        | Name/description     |  Scala type              | Type requirement     | Function   |
|-----------|----------------------|--------------------------|----------------------|------------|
|`abs`      | Absolute value       |`R => R`                  | `IsReal[R]`          |$y = \|x\|$   |
|`sgn`      | Signum               |`R => R`                  | `IsReal[R]`          |$y = \operatorname{sgn} x$ |

### Exponentiation, logarithm, and related functions
| Op        | Name/description                     |  Scala type              | Type requirement     | Function   |
|-----------|--------------------------------------|--------------------------|----------------------|------------|
|`exp`      | Exponentiation                       |`R => R`                  |                      |$y = e^x$   |
|`exp2`     | Exponentiation with base 2           |`R => R`                  |                      |$y = 2^x$   |
|`expm1`    | Exponentiation minus 1 (stability)   |`R => R`                  |                      |$y = e^x - 1$ |
|`log`      | Natural logarithm                    |`R => R`                  |                      |$y = \log x$ |
|`log2`     | Logarithm with base 2                |`R => R`                  |                      |$y = \log_2 x$ |
|`log1p`    | Natural logarithm plus 1 (stability) |`R => R`                  |                      |$y = \log(1 + x)$ |
|`logistic` | Standard logistic (a.k.a. sigmoid)   |`R => R`                  |                      |$y = \sigma(x) = \dfrac{1}{1+e^{-x}}$ |
|`logLogistic`| Log of logistic (stability)        |`R => R`                  |                      |$y = \log\sigma(x)$ | 
|`logit`    | Standard logit (inverse of logistic) |`R => R`                  |                      |$y = \log\dfrac{x}{1-x}$ |
|`logAddExp`| Log of sum of exp (stability)        |`(R, R) => R`             |                      |$z = \log(e^x + e^y)$ |

### Trigonometric and hyperbolic functions

| Op        |  Scala type              | Type requirement     | Function   |
|-----------|--------------------------|----------------------|------------|
|`sin`      |`R => R`                  |                      |$y = \sin x$   |
|`cos`      |`R => R`                  |                      |$y = \cos x$   |
|`tan`      |`R => R`                  |                      |$y = \tan x$ |
|`arcsin`   |`R => R`                  |                      |$y = \arcsin x$ |
|`arccos`   |`R => R`                  |                      |$y = \arccos x$ |
|`arctan`   |`R => R`                  |                      |$y = \arctan x$ |
|`arctan2`  |`(R, R) => R`             |                      | |
|`hypot`    |`(R, R) => R`             |                      |$z = \sqrt{x^2 + y^2}$ |
|`sinh`      |`R => R`                 |                      |$y = \sinh x = \dfrac{e^x - e^{-x}}{2}$   |
|`cosh`      |`R => R`                 |                      |$y = \cosh x = \dfrac{e^x + e^{-x}}{2}$   |
|`tanh`      |`R => R`                 |                      |$y = \tanh x = \dfrac{e^x - e^{-x}}{e^x + e^{-x}}$ |
|`arsinh`   |`R => R`                  |                      |$y = \operatorname{arsinh} x = \log \left( x + \sqrt{x^2 + 1} \right)$ |
|`arcosh`   |`R => R`                  |                      |$y = \operatorname{arcosh} x = \log \left( x + \sqrt{x^2 - 1} \right)$ |
|`artanh`   |`R => R`                  |                      |$y = \operatorname{artanh} x = \dfrac{1}{2} \log \dfrac{1+x}{1-x}$ |

### Minimum and maximum
| Op        |  Scala type              | Type requirement     | Function   |
|-----------|--------------------------|----------------------|------------|
|`min`      |`(R, R) => R`             |                      |$z = \min\{x, y\}$   |
|`max`      |`(R, R) => R`             |                      |$z = \max\{x, y\}$   |

### Rounding and clipping functions
| Op        |  Scala type              | Type requirement     | Function   |
|-----------|--------------------------|----------------------|------------|
|`round`    |`R => R`                  |                      |$y = \lfloor x \rceil$   |
|`ceil`     |`R => R`                  |                      |$y = \lceil x \rceil$   |
|`floor`    |`R => R`                  |                      |$y = \lfloor x \rfloor $ |
|`clip`     |`(R, R, R) => R`          |                      | |

### Normal distribution, error, and related functions
| Op        |  Scala type              | Type requirement     | Function   |
|-----------|--------------------------|----------------------|------------|
|`erf`      |`R => R`                  |                      |$y = \operatorname{erf} x = \displaystyle\frac{1}{\sqrt{\pi}} \int_{-x}^{x} e^{-t^2}dt$   |
|`probit`   |`R => R`                  |                      |$y = \sqrt{2} \operatorname{erf}^{-1}(2x - 1)$   |

### Factorial, Gamma and related functions
| Op        |  Scala type              | Type requirement     | Function   |
|-----------|--------------------------|----------------------|------------|
|`gamma`    |`R => R`                  |                      |$y = \Gamma(x) = \displaystyle \int_{0}^{+\infty} t^{x-1} e^{-t}dt$   |
|`logGamma` |
|`digamma`  |
|`beta`     |
