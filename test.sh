#!/bin/sh
# Shell script to test PLXC
# executes "java $1 test.$3 | ctd" and compares the output with the output of "plxc test.$3 | ctd" for each test case in the folder specified by $2
# $1: name of the PLXC java class
# $2: path to the test cases
# $3: extension of the test cases

# check if the number of arguments is correct
if [ $# -ne 3 ]; then
    echo "Usage: $0 <PLXC java class> <path to test cases> <extension of test cases>"
    exit 1
fi

# check if the PLXC java class exists
if [ ! -f $1.class ]; then
    echo "Error: $1.class does not exist"
    exit 1
fi

# check if the test cases folder exists
if [ ! -d $2 ]; then
    echo "Error: $2 does not exist"
    exit 1
fi

# check if the test cases folder is empty
if [ ! "$(ls -A $2)" ]; then
    echo "Error: $2 is empty"
    exit 1
fi

# check if the test cases folder contains any .plx files
if [ ! "$(ls $2/*.$3)" ]; then
    echo "Error: $2 does not contain any .$3 files"
    exit 1
fi

# check if the extension of the test cases is .pl or .plx
if [ $3 != "pl" ] && [ $3 != "plx" ]; then
    echo "Error: $3 is not a valid extension"
    exit 1
fi

# execute "java $1 test.plx | ctd" and compare the output with the output of "plxc test.plx | ctd" for each test case in the folder specified by $2
for file in $2/*.$3; do
    echo "Testing $file"
    java $1 $file | ctd > $file.java.out
    plxc $file | ctd > $file.plxc.out
    diff $file.java.out $file.plxc.out
    if [ $? -eq 0 ]; then
        echo "✅ $file"
    else
        echo "❌ $file"
    fi
done