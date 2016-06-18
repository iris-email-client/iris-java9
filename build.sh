#!/bin/bash 

#
# Script used to compile, build jars and run
# For now: You need to select the modules 'manually'
# 

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN

# clean
echo Cleaning ...
rm -rf build
mkdir build
rm -rf mlib
mkdir mlib

# compile
echo Compiling ...
$JAVA_BIN/javac -mp libs -d build -modulesourcepath src $(find src -name "*.java")


# modules
echo Creating Modules ...

echo Creating Module: IrisModel
$JAVA_BIN/jar --create --file mlib/iris.model@1.0.jar --module-version 1.0 -C build/iris.model .

echo Creating Module: IrisBase
$JAVA_BIN/jar --create --file mlib/iris.base@1.0.jar --module-version 1.0 -C build/iris.base .

echo Creating Module: IrisCore
cp src/iris.core/*.properties build/iris.core
$JAVA_BIN/jar --create --file mlib/iris.core@1.0.jar --module-version 1.0 -C build/iris.core .

echo Creating Module: IrisMail
$JAVA_BIN/jar --create --file mlib/iris.mail@1.0.jar --module-version 1.0 -C build/iris.mail .

echo Creating Module: IrisMailProvider
$JAVA_BIN/jar --create --file mlib/iris.mail.provider@1.0.jar --module-version 1.0 -C build/iris.mail.provider .

echo Creating Module: IrisPersistence
$JAVA_BIN/jar --create --file mlib/iris.persistence@1.0.jar --module-version 1.0 -C build/iris.persistence .



echo Creating Module: IrisModelSimple
$JAVA_BIN/jar --create --file mlib/iris.model.simple@1.0.jar --module-version 1.0 -C build/iris.model.simple .


echo Creating Module: IrisMailSimple
$JAVA_BIN/jar --create --file mlib/iris.mail.simple@1.0.jar --module-version 1.0 -C build/iris.mail.simple .


echo Creating Module: IrisMailProviderGmail
$JAVA_BIN/jar --create --file mlib/iris.mail.provider.gmail@1.0.jar --module-version 1.0 -C build/iris.mail.provider.gmail .

echo Creating Module: IrisMailProviderOutlook
$JAVA_BIN/jar --create --file mlib/iris.mail.provider.outlook@1.0.jar --module-version 1.0 -C build/iris.mail.provider.outlook .

echo Creating Module: IrisMailProviderYahoo
$JAVA_BIN/jar --create --file mlib/iris.mail.provider.yahoo@1.0.jar --module-version 1.0 -C build/iris.mail.provider.yahoo .


#echo Creating Module: IrisPersistenceXml
#$JAVA_BIN/jar --create --file mlib/iris.persistence.xml@1.0.jar --module-version 1.0 -C build/iris.persistence.xml .

echo Creating Module: IrisPersistenceJDBC
$JAVA_BIN/jar --create --file mlib/iris.persistence.jdbc@1.0.jar --module-version 1.0 -C build/iris.persistence.jdbc .



#echo Creating Module: IrisConsole
#$JAVA_BIN/jar --create --file mlib/iris.ui.cli@1.0.jar --module-version 1.0 --main-class br.unb.cic.iris.cli.MainProgram -C build/iris.ui.cli .

echo Creating Module: IrisGUI
cp -Rf src/iris.ui.gui/images build/iris.ui.gui
$JAVA_BIN/jar --create --file mlib/iris.ui.gui@1.0.jar --module-version 1.0 --main-class br.unb.cic.iris.gui.MainProgram -C build/iris.ui.gui .


# link
echo Linking ...
rm -rf iris
$JAVA_BIN/jlink --modulepath $JAVA_HOME/jmods:mlib:libs --addmods iris.ui.gui --output iris

# run
echo Executing ...
$JAVA_BIN/java -mp mlib:libs -m iris.ui.gui

# run the run-time image created by jlink
#cd mailclient/bin 
#./MailClient



#$JAVA_BIN/java -cp libs/javax.mail-1.5.5.jar:libs/javax.mail-api-1.5.5.jar:libs/activation-1.1.1.jar -mp mlib -m iris.ui.cli

