%module torch

%{
    #include <math.h>
    #include <stdbool.h>
    #include <stddef.h>

    #include "include/TH/TH.h"
    #include "include/THC/THC.h"
    #include "include/ATen/ATen.h"

    using namespace at;
    using namespace c10::detail;
%}

%include "stdint.i"
%include "carrays.i"

%define ptrdiff_t
intptr_t
%enddef

%array_class(uint8_t, CUInt8Array);
%array_class(int8_t, CInt8Array);
%array_class(int16_t, CInt16Array);
%array_class(int32_t, CInt32Array);
%array_class(int64_t, CInt64Array);
%array_class(float, CFloatArray);
%array_class(double, CDoubleArray);

%array_functions(THTensor *, TensorArray);

SWIG_JAVABODY_PROXY(public, public, SWIGTYPE)
SWIG_JAVABODY_TYPEWRAPPER(public, public, public, SWIGTYPE)


%include "include-swig/torch-preprocessed.h"

%pragma(java) jniclasscode=%{

    static {
        try {
            System.loadLibrary("caffe2");
        }
        catch (Exception e) {
            System.err.println("caffe2 native library not available.");
        }

        try {
            System.loadLibrary("caffe2_gpu");
        }
        catch (Exception e) {
            System.err.println("caffe2_gpu native library unavailable.");
        }

        try {
            nexus.jniloader.JNILoader.loadLibraryFromJar("jnitorch");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

%}
