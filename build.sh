#!/bin/bash 

#
# Script used to compile, build jars and run
# For now: You need to select the modules 'manually'
# 
# USAGE:
#  *** to launch IrisGUI:
# ./build.sh gui
#
#  *** to launch IrisConsole
# ./build.sh cli
#    or just
# ./build.sh
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


# Persistence
echo Creating Module: IrisPersistenceJDBC
$JAVA_BIN/jar --create --file mlib/iris.persistence.jdbc@1.0.jar --module-version 1.0 -C build/iris.persistence.jdbc .

echo Creating Module: IrisPersistenceLucene
$JAVA_BIN/jar --create --file mlib/iris.persistence.lucene@1.0.jar --module-version 1.0 -C build/iris.persistence.lucene .

echo Creating Module: IrisPersistenceXml
$JAVA_BIN/jar --create --file mlib/iris.persistence.xml@1.0.jar --module-version 1.0 -C build/iris.persistence.xml .


echo Creating Module: IrisAddressBookAPI
$JAVA_BIN/jar --create --file mlib/iris.addressbook.api@1.0.jar --module-version 1.0 -C build/iris.addressbook.api .

echo Creating Module: IrisAddressBookModelSimple
$JAVA_BIN/jar --create --file mlib/iris.addressbook.model.simple@1.0.jar --module-version 1.0 -C build/iris.addressbook.model.simple .

#echo Creating Module: IrisAddressBookPersistenceJDBC
#$JAVA_BIN/jar --create --file mlib/iris.addressbook.persistence.jdbc@1.0.jar --module-version 1.0 -C build/iris.addressbook.persistence.jdbc .

echo Creating Module: IrisAddressBookPersistenceXml
$JAVA_BIN/jar --create --file mlib/iris.addressbook.persistence.xml@1.0.jar --module-version 1.0 -C build/iris.addressbook.persistence.xml .

echo Creating Module: IrisAddressBookCLI
$JAVA_BIN/jar --create --file mlib/iris.addressbook.ui.cli@1.0.jar --module-version 1.0 -C build/iris.addressbook.ui.cli .



# default module is CLI (command line interface)
MODULE_NAME="IrisConsole"
MODULE_REAL_NAME="iris.ui.cli"
MAIN_CLASS="br.unb.cic.iris.cli.MainProgram"

if [ "$#" -ne  "0" ] 
then
     if [ "$1" = "gui" ] 
     then     	
     	cp -Rf src/iris.ui.gui/images build/iris.ui.gui
     	MODULE_NAME="IrisGUI"
		MODULE_REAL_NAME="iris.ui.gui"
		MAIN_CLASS="br.unb.cic.iris.gui.MainProgram"
     fi
fi

echo Creating Module: $MODULE_NAME
$JAVA_BIN/jar --create --file mlib/$MODULE_REAL_NAME@1.0.jar --module-version 1.0 --main-class $MAIN_CLASS -C build/$MODULE_REAL_NAME .




# link
echo Linking ...
rm -rf iris
$JAVA_BIN/jlink --modulepath $JAVA_HOME/jmods:mlib:libs --addmods $MODULE_REAL_NAME --output iris


# run
echo Executing ...
$JAVA_BIN/java -mp mlib:libs -m $MODULE_REAL_NAME
# TODO understand why we need to put jars on cp
#$JAVA_BIN/java -mp mlib:libs -cp libs/lucene-core-4.10.2.jar:libs/lucene-codecs-4.10.2.jar -m $MODULE_REAL_NAME



# run the run-time image created by jlink
#cd mailclient/bin 
#./MailClient


#$JAVA_BIN/java -mp mlib:lib -cp lib/lucene-core-4.10.2.jar:lib/lucene-codecs-4.10.2.jar -m TesteLucene
#$JAVA_BIN/java -cp libs/javax.mail-1.5.5.jar:libs/javax.mail-api-1.5.5.jar:libs/activation-1.1.1.jar -mp mlib -m iris.ui.cli

