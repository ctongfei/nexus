#! /bin/bash
PYTORCH_INCLUDE_PATH=/home/tongfei/proj/pytorch1/aten/src
PYTORCH_LIB_PATH=/usr/local/lib/python3.6/dist-packages/torch/lib

TORCH_MODULES=(TH THNN THC THCUNN ATen)

# Determining operating system
if [[ $OSTYPE == "linux-gnu" ]]; then
    echo "Operating system is $OSTYPE"
    JAVA_OS_DEPENDENT_INCLUDE=linux
    JNI_COMPILATION_FLAGS="-std=c99 -fPIC -static"
    CC_LIB_ARGS="-shared torch_wrap.o -o jni/src/main/resources/libjnitorch.so -Wl,-rpath,$PYTORCH_LIB_PATH -L $PYTORCH_LIB_PATH -lcaffe2 -lcaffe2_gpu"

elif [[ $OSTYPE == "darwin"* ]]; then
    echo "Operating system is $OSTYPE"
    JAVA_OS_DEPENDENT_INCLUDE=darwin
    JNI_COMPILATION_FLAGS=""
    CC_LIB_ARGS="-dynamiclib -undefined suppress -flat_namespace torch_wrap.o -o jni/src/main/resources/libjnitorch.dylib"

fi

mkdir -p include
mkdir -p include-swig

echo "Copying include files..."
for m in ${TORCH_MODULES[@]}; do 
    cp -RT $PYTORCH_INCLUDE_PATH/$m ./include/$m;
    cp -RT $PYTORCH_INCLUDE_PATH/$m ./include-swig/$m;
done


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

echo "Preprocessing C headers..."
cp torch.h include-swig
cd include-swig
cc -P -E \
  -I TH -I THNN -I THC -I THCUNN torch.h \
  | grep -v "__signed" \
  | grep -v "struct THCState;" \
  | grep -v "typedef struct THCState THCState;" \
  > torch-preprocessed.h
cd ..

echo "Generating SWIG bindings..."
mkdir -p jni/src/main/java/nexus/torch/jni
swig -DSWIGWORDSIZE64 -java -package nexus.torch.jni -outdir jni/src/main/java/nexus/torch/jni torch.i

echo "Compiling SWIG generated JNI wrapper code..."

cc $JNI_COMPILATION_FLAGS -c torch_wrap.c \
    -I $JAVA_HOME/include \
    -I $JAVA_HOME/include/$JAVA_OS_DEPENDENT_INCLUDE \
    -I $CUDA_ROOT/include \
    -I $PYTORCH_INCLUDE_PATH \
    -I $PYTORCH_INCLUDE_PATH/ATen \
    -I $PYTORCH_INCLUDE_PATH/TH \
    -I $PYTORCH_INCLUDE_PATH/THNN \
    -I $PYTORCH_INCLUDE_PATH/THC \
    -I $PYTORCH_INCLUDE_PATH/THCUNN
# TODO: remove Reduction.h 

echo "Building dynamic linking library..."
mkdir -p jni/src/main/resources
cc $CC_LIB_ARGS
