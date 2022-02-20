#!/bin/bash
# Copyright 2020 Darius Neatu (neatudarius@gmail.com)

sudo apt-get update

sudo -H apt-get install                        \
    gcc-8                                   \
    python3 python3-pip                     \
    perl                                    \
    make gdb valgrind

# all py packages json os pathlib recordclass subprocess sys
sudo -H pip3 install                           \
    argparse                                \
    pathlib                                 \
    recordclass
