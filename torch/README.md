## nexus-torch
`nexus-torch` is a Torch (PyTorch C/C++ core) backend for Nexus. It uses SWIG to generate the JNI bindings.

### Tested environments

 - Linux + CUDA 9.1 / 10.0 + PyTorch 1.0.0


### Building the binding manually

Prerequisites:
 - PyTorch 1.0.0
 ```
 pip install torch=1.0.0
 ```
 - SWIG 3.0+

Steps:
 - **Patch SWIG**. Use the `swig-patch.diff` file to patch SWIG: This resolves SWIG issue [#646](https://github.com/swig/swig/issues/646), which incorrectly maps `ptrdiff_t` to `Int` instead of the correct `Long` under 64-bit machines.
 - **Generate JNI shared library**: Run `build.sh` to build the shared library (`libjnitorch.so` / `libjnitorch.dylib`).
 
