PYTORCH_PYTHON_PATH := $(shell python -c "import torch; print(torch.__file__)")
PYTORCH_PATH := $(shell dirname $(PYTORCH_PYTHON_PATH))
PYTORCH_VERSION := $(shell python -c "import torch; print(torch.__version__)")
PYTORCH_LIB_PATH := $(PYTORCH_PATH)/lib
PYTORCH_INCLUDE_PATH := $(PYTORCH_LIB_PATH)/include

GENERATED_SOURCE_PATH := jni/src/main/java/nexus/torch/jni
GENERATED_JAVA_PACKAGE := nexus.torch.jni
RESOURCES_PATH := jni/src/main/resources

OS := $(shell uname)

JNI_LIBNAME := jnitorch
JNI_FILENAME_Linux := lib$(JNI_LIBNAME).so
JNI_FILENAME_Darwin := lib$(JNI_LIBNAME).dylib
JNI_FILENAME := $(JNI_FILENAME_$(OS))

JAVA_OS_DEPENDENT_INCLUDE_Linux := linux
JAVA_OS_DEPENDENT_INCLUDE_Darwin := darwin
JAVA_OS_DEPENDENT_INCLUDE := $(JAVA_OS_DEPENDENT_INCLUDE_$(OS))

CXXFLAGS_Linux := -std=c++11 -fPIC -static
CXXFLAGS_Darwin :=
CXXFLAGS := $(CXXFLAGS_$(OS))

LDFLAGS_Linux := -shared -fPIC torch_wrap_fixed.o -o $(RESOURCES_PATH)/$(JNI_FILENAME) -L $(PYTORCH_LIB_PATH) -l caffe2 -l caffe2_gpu
LDFLAGS_Darwin := -dynamiclib -undefined suppress -flat_namespace torch_wrap_fixed.o -o $RESOURCES_PATH/$JNI_FILENAME
LDFLAGS := $(LDFLAGS_$(OS))

.PHONY: all clean

$(info PyTorch $(PYTORCH_VERSION) found at $(PYTORCH_PATH))
$(info OS is $(OS))
$(info JAVA_HOME is $(JAVA_HOME))
$(info CUDA_HOME is $(CUDA_HOME))

all: $(RESOURCES_PATH)/$(JNI_FILENAME)

include-original:
	mkdir -p ./include-original;
	for m in TH THC ATen; do \
		cp -R $(PYTORCH_INCLUDE_PATH)/$$m ./include-original/$$m; \
	done;
	@echo $(PYTORCH_LIB_PATH);
	cp $(PYTORCH_LIB_PATH)/THNN.h ./include-original;
	cp $(PYTORCH_LIB_PATH)/THCUNN.h ./include-original;

include-swig: include-original
	mkdir -p ./include-swig; \
	cp -R ./include-original/* ./include-swig; \
	for f in $(shell find ./include-original -name \*.h); do \
		x=$${f#./include-original/}; \
        cat $$f \
        | sed -E "s|<TH(.*)>|\"TH\1\"|g" \
        | grep -v "#include <.*>" \
        | grep -v "#include \"cu.*\.h\"" \
        | grep -v "TH_NO_RETURN" \
        | grep -v "__signed" \
        | grep -v "Reduction.h" \
        | sed -e "s|__thalign__([0-9])||g" \
        > ./include-swig/$$x; \
    done

torch-preprocessed.h : include-swig torch.h
	g++ -P -E \
	  -I include-swig \
	  -I include-swig/TH \
	  -I include-swig/THNN \
	  -I include-swig/THC \
	  -I include-swig/THCUNN \
	  torch.h \
	  | sed -e 's/__attribute__((__visibility__("default")))//g' \
	  | sed -e 's/CAFFE2_API//g' \
	  | grep -v "AT_CUDA_API" \
	  | grep -v "^at::DataPtr" \
	  | python remove_classes.py \
	  > torch-preprocessed.h

torch_wrap.cxx : torch-preprocessed.h torch.i
	mkdir -p $(GENERATED_SOURCE_PATH);
	swig -c++ -v -DSWIGWORDSIZE64 -java \
	  -package $(GENERATED_JAVA_PACKAGE) \
	  -outdir $(GENERATED_SOURCE_PATH) torch.i

torch_wrap_fixed.cxx : torch_wrap.cxx
	cat torch_wrap.cxx \
      | python fix_cuda_stream_dereferencing.py \
      > torch_wrap_fixed.cxx

torch_wrap_fixed.o : torch_wrap_fixed.cxx
	g++ $(CXXFLAGS) -c torch_wrap_fixed.cxx \
	-I $(JAVA_HOME)/include \
	-I $(JAVA_HOME)/include/$(JAVA_OS_DEPENDENT_INCLUDE) \
	-I $(CUDA_HOME)/include \
	-I $(PYTORCH_LIB_PATH) \
    -I $(PYTORCH_INCLUDE_PATH) \
    -I $(PYTORCH_INCLUDE_PATH)/TH \
    -I $(PYTORCH_INCLUDE_PATH)/THC

$(RESOURCES_PATH)/$(JNI_FILENAME): torch_wrap_fixed.o
	mkdir -p $(RESOURCES_PATH);
	g++ $(LDFLAGS)

clean:
	-rm -r include-original
	-rm -r include-swig
	-rm torch-preprocessed.h
	-rm torch_wrap.cxx
	-rm torch_wrap_fixed.cxx
	-rm torch_wrap_fixed.o
