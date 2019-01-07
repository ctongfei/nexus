`nexus-torch` is a tensor computation backend for Nexus based on [PyTorch](https://github.com/pytorch/pytorch). 
It uses SWIG to generate the JNI bindings directly from the Torch C/C++ source without the Python part of PyTorch.

### Tested environments

 - Linux + CUDA 9.1 / 10.0 + PyTorch 1.0.0


### Building the binding manually

Prerequisites:

 - PyTorch 1.0.0
 ```
 pip install torch==1.0.0
 ```
 - SWIG 3.0+

Steps:
  
  - **Patch SWIG**. 
   Use the [`swig-patch/fix-long.patch`](https://github.com/ctongfei/nexus/blob/master/torch/swig-patch/fix-long.patch) 
   file to patch SWIG: This resolves SWIG issue [#646](https://github.com/swig/swig/issues/646), which incorrectly maps `ptrdiff_t` to `Int` instead of the correct `Long` under 64-bit machines.
  - **Generate JNI shared library**: Run `build.sh` to build the shared library (`libjnitorch.so` / `libjnitorch.dylib`).
 
### Internals

  - 