#!/bin/bash
# Usage: ./cecr.sh ex1.c test

set -e

sourcename=$1
binname="${sourcename%.*}"
testprefix=$2

echo "Compiling source file $sourcename to $binname"

gcc -Wall $sourcename

i=0
while
	((i++))
	testinfile="$testprefix$i.in"
	[ -f $testinfile ]
do
	echo "Running test $testinfile"
	outfile="david$i.out"
	./a.out < $testinfile > $outfile
	diff $outfile "$testprefix$i.out"
done

echo "Test $testprefix$i.in not found"