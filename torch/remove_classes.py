#! /usr/bin/env python3

import sys

in_class = False

for l in sys.stdin:
    if l.startswith("class"):
        in_class = True

    if in_class:
        if l.startswith("};"):
            in_class = False
        continue
    else:
        print(l, end='')
