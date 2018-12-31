#! /bin/bash

set -e  # terminates this on any error below


PYTORCH_PATH=$(dirname $(python -c "import torch; print(torch.__file__)"))
PYTORCH_VERSION=$(python -c "import torch; print(torch.__version__)")
echo "PyTorch $PYTORCH_VERSION at $PYTORCH_PATH detected"

PYTORCH_INCLUDE_PATH=$PYTORCH_PATH/lib/include
PYTORCH_LIB_PATH=$PYTORCH_PATH/lib


# Determining operating system
if [[ $OSTYPE == "linux-gnu" ]]; then
    echo "Operating system is $OSTYPE"
    JAVA_OS_DEPENDENT_INCLUDE=linux
    JNI_COMPILATION_FLAGS="-std=c++11 -fPIC -static"
    CC_LIB_ARGS="-shared torch_wrap.o -o jni/src/main/resources/libjnitorch.so -Wl,-rpath,$PYTORCH_LIB_PATH -L $PYTORCH_LIB_PATH -lcaffe2 -lcaffe2_gpu"

elif [[ $OSTYPE == "darwin"* ]]; then
    echo "Operating system is $OSTYPE"
    JAVA_OS_DEPENDENT_INCLUDE=darwin
    JNI_COMPILATION_FLAGS=""
    CC_LIB_ARGS="-dynamiclib -undefined suppress -flat_namespace torch_wrap.o -o jni/src/main/resources/libjnitorch.dylib"

fi


TORCH_MODULES=(TH THC ATen)

mkdir -p include
mkdir -p include-swig

echo "Copying include files..."
for m in ${TORCH_MODULES[@]}; do
    cp -R $PYTORCH_INCLUDE_PATH/$m ./include/$m;
done
cp $PYTORCH_LIB_PATH/THNN.h ./include
cp $PYTORCH_LIB_PATH/THCUNN.h ./include

cp -R include/* include-swig

echo "Preprocessing all header files for SWIG to parse..."
#   change '#include <THxxx.h>' to '#include "THxxx.h"'
#   remove system headers
#   remove CUDA headers
#   remove TH_NO_RETURN
#   remove 'Reduction.h'
#   remove '__thalign__([0-9])'
cd include
for f in $(find . -name \*.h); do
    cat $f \
    | sed -E "s|<TH(.*)>|\"TH\1\"|g" \
    | grep -v "#include <.*>" \
    | grep -v "#include \"cu.*\.h\"" \
    | grep -v "TH_NO_RETURN" \
    | grep -v "__signed" \
    | grep -v "Reduction.h" \
    | sed -e "s|__thalign__([0-9])||g" \
    > ../include-swig/$f
done
cd ..

rm -r include

echo "Preprocessing C headers..."
cp torch.h include-swig
cd include-swig
g++ -P -E \
  -I TH -I THNN -I THC -I THCUNN torch.h \
  | sed -e 's/__attribute__((__visibility__("default")))//g' \
  | sed -e 's/CAFFE2_API//g' \
  | grep -v "AT_CUDA_API" \
  | grep -v "^at::DataPtr" \
  | python ../remove_classes.py \
  > torch-preprocessed.h
cd ..


echo "Generating SWIG bindings..."
mkdir -p jni/src/main/java/nexus/torch/jni
swig -c++ -v -DSWIGWORDSIZE64 -java -package nexus.torch.jni -outdir jni/src/main/java/nexus/torch/jni torch.i

cat torch_wrap.cxx \
  | python fix_cuda_stream_dereferencing.py \
  > torch_wrap_fixed.cxx


echo "Compiling SWIG generated JNI wrapper code..."
echo "Compiling using Java: $JAVA_HOME"

g++ $JNI_COMPILATION_FLAGS -c torch_wrap_fixed.cxx \
    -I $JAVA_HOME/include \
    -I $JAVA_HOME/include/$JAVA_OS_DEPENDENT_INCLUDE \
    -I $CUDA_ROOT/include \
    -I $PYTORCH_LIB_PATH \
    -I $PYTORCH_INCLUDE_PATH \
    -I $PYTORCH_INCLUDE_PATH/TH \
    -I $PYTORCH_INCLUDE_PATH/THC

echo "Building dynamic linking library..."

mkdir -p jni/src/main/resources
cc $CC_LIB_ARGS

echo "Cleaning up..."
#rm -r include-swig
#
#rm torch_wrap.cxx
#rm torch_wrap_fixed.cxx


echo "Done. SWIG JNI bridge built."
