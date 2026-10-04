#!/bin/bash
# run.sh - starts the Retail Sales Analysis System (build first using ./build.sh).
cd "$(dirname "$0")"

if [ ! -d out ]; then
    echo "The 'out' folder was not found. Building the project first..."
    ./build.sh || exit 1
fi

java -Dfile.encoding=UTF-8 -cp out com.retail.sales.Main
