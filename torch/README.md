`nexus-torch` is a tensor computation backend for Nexus based on [PyTorch](https://github.com/pytorch/pytorch). 
It uses SWIG to generate the JNI bindings directly from the Torch C/C++ source without the Python part of PyTorch.

### Tested environments

 - Linux + CUDA 9.1 / 10.0 + PyTorch 1.0.0 / 1.0.1


### Building the binding manually

Prerequisites:

 - PyTorch 1.0.0+
 - SWIG 3.0+

Steps:
  
  - **Patch SWIG**. 
   Use the [`swig-patch/fix-long.patch`](https://github.com/ctongfei/nexus/blob/master/torch/swig-patch/fix-long.patch) 
   file to patch SWIG: This resolves SWIG issue [#646](https://github.com/swig/swig/issues/646), which incorrectly maps `ptrdiff_t` to `Int` instead of the correct `Long` under 64-bit machines.
    ```sh
     sudo patch /usr/share/swig3.0/java/java.swg < ./swig-patch/fix-long.patch
     # The actual path to `java.swg` may vary depending on your system.
     # The path shown here is a typical Linux installation of SWIG 
    ```
  - **Generate the JNI shared library**:
    ```sh
     make all
    ```
    The shared library (`libjnitorch.so` / `libjnitorch.dylib`) will be built in `jni/src/main/resources` so that it'll be loaded when the backend starts in JVM.
  - **Remove intermediate files**:
    ```sh
      make clean
    ```
    This removes all intermediate files. If the build is not successful or if you want to remove the generated Java source or the shared library, use
    ```sh
      make cleanall
    ```
  - `sbt` should be ready to build the `nexus-torch` module now.
 
### Troubleshooting

  - **Q:** "Intel MKL FATAL ERROR: Cannot load `libmkl_avx2.so` or `libmkl_def.so`":
    
    **A:**  This is a linking error from MKL. 
    Solution: put 
    ```sh
    export LD_PRELOAD="libmkl_rt.so"
    ``` 
    in your `~/.profile` (or whatever shell startup script you have). Credit to https://stackoverflow.com/a/13966719/2990673.
