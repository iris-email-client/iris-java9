#!/bin/bash 

#Script used to run jdeps and generate image and .dot files representing the module hierarchy

#sudo apt-get install xdot
#xdot stuff.dot

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN

echo Cleaning DOT files ...
rm -Rf dot
mkdir dot

echo Generating DOT files ...
$JAVA_BIN/jdeps -dotoutput dot mlib/*.jar

echo Converting summary to image ...
dot dot/summary.dot -Tpng -o dot/summary.png
