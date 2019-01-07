#! /usr/bin/env python3

import sys

in_func = False
cuda_stream_found = False

for l in sys.stdin:
    if l.startswith('SWIGEXPORT'):
        in_func = True
        cuda_stream_found = False

    if in_func:
        if l == "  at::cuda::CUDAStream arg2 ;\n":
            cuda_stream_found = True
            print("Problematic definition found.", file=sys.stderr)
            continue
        if l == "  arg2 = *argp2; \n" and cuda_stream_found:
            print("  at::cuda::CUDAStream arg2 = *argp2;")
            print("Fixed.", file=sys.stderr)
            continue

    print(l, end="")

    if l.startswith("}"):
        in_func = False
