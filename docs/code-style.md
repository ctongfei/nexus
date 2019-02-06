### Naming of type variables

 - `E`: Any tensor element type: Could be either `R`/`Z`/`B`/`C` below
 - `R`: Any real type ($\mathbb{R}$), e.g. `Float`, `Double`, `Symbolic[Double]`
 - `Z`: Any integer type ($\mathbb{Z}$), e.g. `Int`, `Long`, `Symbolic[Int]`
 - `B`: Any Boolean type ($\mathbb{B}$), e.g. `Boolean`, `Symbolic[Boolean]`
 - `C`: Any complex type ($\mathbb{C}$)
 - `N`: A compile-time `Nat` for specified/inferred dimensions.
 - `T[_]`: Any tensor type. If the element type should be specified (e.g. $\mathbb{R}$), use `TR[_]`, `TZ[_]`, etc.
 - `I`, `J`, `K`: Single dimension labels (`I` could be used for tensor axes descriptors for elementwise operations)
 - `U`, `V`, `W`: Tensor axes descriptors (single or multiple dimension labels)
 - `X`, `Y`, `Z`: Arbitrary data type in functions (`X => Y` / `Y => Z`)
 - `F[X..., Y]`: Proof/predicate that an operator/module is applicable
 - `D[_]`: Abstracts over boxes that keeps track of the differentiable program (`Id`/`Symbolic`/`Traced`)
 
Examples:

 - Sigmoid activation: `T[I] => T[I]`
 - Addition: `(T[I], T[I]) => T[I]`
 - Matrix multiplication: `(T[(I, J)], T[(J, K)]) => T[(I, K)]`
 - Sum along axis: `implicit Remove.Aux[U, I, V] => (T[U], I) => T[V]` 
 - Natural tensor contraction: `implicit SymDiff.Aux[U, V, W] => (T[U], T[V]) => T[W]`