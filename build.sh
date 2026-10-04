#!/bin/bash
# build.sh - compiles all Java files of the project into the "out" folder.
cd "$(dirname "$0")"

mkdir -p out
find src -name "*.java" > sources.txt

# -encoding UTF-8 makes sure the source files are read correctly on every system.
javac -encoding UTF-8 -d out @sources.txt
STATUS=$?
rm -f sources.txt

if [ $STATUS -eq 0 ]; then
    echo "Build successful. Compiled classes are in the 'out' folder."
else
    echo "Build failed. Please check the errors above."
    exit 1
fi
