#!/bin/bash 

#Script used to run jdeps and generate image and .dot files representing the module hierarchy

#sudo apt-get install graphviz

echo "Changing directory: "
cd iris.produtos/target
pwd

echo Cleaning DOT files ...
rm -Rf dot
mkdir dot

echo Generating DOT files ...
jdeps -dotoutput dot --module-path libs libs/*.jar

echo Converting summary to image ...
dot dot/summary.dot -Tpng -o dot/summary.png
