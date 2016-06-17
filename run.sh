#!/bin/bash 

#Script used to just run the application/product(SPL) compiled before.
#Use this script if you don't want to recompile. 
#EXAMPLE: run after remove a module from mlib (app modules dir) 

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN

# run
echo Executing ...
$JAVA_BIN/java -mp mlib:libs -m iris.ui.cli
