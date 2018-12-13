#! /usr/bin/env python

import sys

in_func = False
cuda_stream_found = False

for l in sys.stdin:
    if l.startswith('SWIGEXPORT'):
        in_func = True
        cuda_stream_found = False

    if in_func:
        if l == "  at::cuda::CUDAStream arg2 ;":
            cuda_stream_found = True
            continue
        if l == "  arg2 = *argp2; " and cuda_stream_found:
            print("  at::cuda::CUDAStream arg2 = *argp2;")

    else:
        print(l, end="")

    if l.startswith("}"):
        in_func = False
