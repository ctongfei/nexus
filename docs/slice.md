# Slices

Nexus supports tensor slices like NumPy. 

In NumPy, the syntax of a slice object is `left:right:step`. This actually encapsulates the following forms:


| Nexus      | NumPy    | Nexus type           | Description                             |
|------------|----------|----------------------|-----------------------------------------|
|`?`         |`:`       | `Slice.Unbounded`    |An unbounded slice with step size 1      |
|`? by s`    |`::s`     | `Slice.Unbounded`    |An unbounded slice with step size `s`    |
|`l ~ ?`     |`l:`      | `Slice.LeftBounded`  |A left-bounded slice with step size 1    |
|`l ~ ? by s`|`l::s`    | `Slice.LeftBounded`  |A left-bounded slice with step size `s`  |
|`? ~ r`     |`:r`      | `Slice.RightBounded` |A right-bounded slice with step size 1   |
|`? ~ r by s`|`:r:s`    | `Slice.RightBounded` |A right-bounded slice with step size `s` |
|`l ~ r`     |`l:r`     | `Slice.Bounded`      |A bounded slice with step size 1         |
|`l ~ r by s`|`l:r:s`   | `Slice.Bounded`      |A bounded slice with step size `s`       |

Reversed slices (with `step < 0`) is not yet supported.
