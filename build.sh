#!/bin/bash 

#
# Script used to compile, build jars and run
# For now: You need to put all dependencies(jars) in 'libs' dir
# 
# USAGE:
#  * define basic config and run:
# ./build.sh
#

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN

############### BASIC CONFIG ###################
#interface: gui/cli
INTERFACE=cli

#persistence: xml/lucene/relational
PERSISTENCE=relational

#adb: true/false
ADDRESS_BOOK=true

#tags: true/false
TAGS=true
################################################


# clean
echo Cleaning ...
rm -rf build
mkdir build
rm -rf mlib
mkdir mlib

# compile
echo Compiling ...
$JAVA_BIN/javac -mp libs -d build -modulesourcepath src $(find src -name "*.java")
#$JAVA_BIN/javac -mp libs -cp lib/javax.mail-1.5.5.jar -d build -modulesourcepath src $(find src -name "*.java")


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


if [ $PERSISTENCE = "relational" ] || [ $PERSISTENCE = "lucene" ]; then
	echo Creating Module: IrisModelSimple
	$JAVA_BIN/jar --create --file mlib/iris.model.simple@1.0.jar --module-version 1.0 -C build/iris.model.simple .
fi

echo Creating Module: IrisMailSimple
$JAVA_BIN/jar --create --file mlib/iris.mail.simple@1.0.jar --module-version 1.0 -C build/iris.mail.simple .


echo Creating Module: IrisMailProviderGmail
$JAVA_BIN/jar --create --file mlib/iris.mail.provider.gmail@1.0.jar --module-version 1.0 -C build/iris.mail.provider.gmail .

echo Creating Module: IrisMailProviderOutlook
$JAVA_BIN/jar --create --file mlib/iris.mail.provider.outlook@1.0.jar --module-version 1.0 -C build/iris.mail.provider.outlook .

echo Creating Module: IrisMailProviderYahoo
$JAVA_BIN/jar --create --file mlib/iris.mail.provider.yahoo@1.0.jar --module-version 1.0 -C build/iris.mail.provider.yahoo .


# Persistence
if [ $PERSISTENCE = "relational" ]; then
	echo Creating Module: IrisPersistenceJDBC
	$JAVA_BIN/jar --create --file mlib/iris.persistence.jdbc@1.0.jar --module-version 1.0 -C build/iris.persistence.jdbc .
elif [ $PERSISTENCE = "lucene" ]; then
	echo Creating Module: IrisPersistenceLucene
	$JAVA_BIN/jar --create --file mlib/iris.persistence.lucene@1.0.jar --module-version 1.0 -C build/iris.persistence.lucene .
else
	echo Creating Module: IrisPersistenceXml
	$JAVA_BIN/jar --create --file mlib/iris.persistence.xml@1.0.jar --module-version 1.0 -C build/iris.persistence.xml .
fi

# Address Book Feature
if [ $ADDRESS_BOOK = true ]; then
	echo Creating Module: IrisAddressBookAPI
	$JAVA_BIN/jar --create --file mlib/iris.addressbook.api@1.0.jar --module-version 1.0 -C build/iris.addressbook.api .

	if [ $PERSISTENCE = "relational" ] || [ $PERSISTENCE = "lucene" ]; then
		echo Creating Module: IrisAddressBookModelSimple
		$JAVA_BIN/jar --create --file mlib/iris.addressbook.model.simple@1.0.jar --module-version 1.0 -C build/iris.addressbook.model.simple .
	fi
	
	if [ $PERSISTENCE = "relational" ]; then
		echo Creating Module: IrisAddressBookPersistenceJDBC
		$JAVA_BIN/jar --create --file mlib/iris.addressbook.persistence.jdbc@1.0.jar --module-version 1.0 -C build/iris.addressbook.persistence.jdbc .
	elif [ $PERSISTENCE = "lucene" ]; then
		echo Creating Module: IrisAddressBookPersistenceLucene NOT YET IMPLEMENTED!!!!!!!!!!!		
	else
		echo Creating Module: IrisAddressBookPersistenceXml
		$JAVA_BIN/jar --create --file mlib/iris.addressbook.persistence.xml@1.0.jar --module-version 1.0 -C build/iris.addressbook.persistence.xml .
	fi
		
	if [ $INTERFACE = "gui" ]; then
		echo Creating Module: IrisAddressBookGUI
		cp -Rf src/iris.addressbook.ui.gui/images build/iris.addressbook.ui.gui
		$JAVA_BIN/jar --create --file mlib/iris.addressbook.ui.gui@1.0.jar --module-version 1.0 -C build/iris.addressbook.ui.gui .
	else
		echo Creating Module: IrisAddressBookCLI
		$JAVA_BIN/jar --create --file mlib/iris.addressbook.ui.cli@1.0.jar --module-version 1.0 -C build/iris.addressbook.ui.cli .
	fi
fi #end_of if [ $ADDRESS_BOOK = true ]


# Tag Feature
if [ $TAGS = true ]; then
	echo Creating Module: IrisTagAPI
	$JAVA_BIN/jar --create --file mlib/iris.tag.api@1.0.jar --module-version 1.0 -C build/iris.tag.api .
	
	if [ $PERSISTENCE = "relational" ] || [ $PERSISTENCE = "lucene" ]; then
		echo Creating Module: IrisTagModelSimple
		$JAVA_BIN/jar --create --file mlib/iris.tag.model.simple@1.0.jar --module-version 1.0 -C build/iris.tag.model.simple .
	fi
	
	if [ $PERSISTENCE = "relational" ]; then
		echo Creating Module: IrisTagPersistenceJDBC
		$JAVA_BIN/jar --create --file mlib/iris.tag.persistence.jdbc@1.0.jar --module-version 1.0 -C build/iris.tag.persistence.jdbc .
	elif [ $PERSISTENCE = "lucene" ]; then
		echo Creating Module: IrisTagPersistenceLucene NOT YET IMPLEMENTED!!!!!!!!!!!		
	else
		echo Creating Module: IrisTagPersistenceXml
		$JAVA_BIN/jar --create --file mlib/iris.tag.persistence.xml@1.0.jar --module-version 1.0 -C build/iris.tag.persistence.xml .
	fi
	
	if [ $INTERFACE = "gui" ]; then
		echo Creating Module: IrisTagGUI
		cp -Rf src/iris.tag.ui.gui/images build/iris.tag.ui.gui
		$JAVA_BIN/jar --create --file mlib/iris.tag.ui.gui@1.0.jar --module-version 1.0 -C build/iris.tag.ui.gui .
	else
		echo Creating Module: IrisTagCLI
		$JAVA_BIN/jar --create --file mlib/iris.tag.ui.cli@1.0.jar --module-version 1.0 -C build/iris.tag.ui.cli .
	fi
fi #end_of if [ $TAGS = true ]


if [ $INTERFACE = "gui" ]; then
 	cp -Rf src/iris.ui.gui/images build/iris.ui.gui
 	cp src/iris.ui.gui/*.properties build/iris.ui.gui
 	MODULE_NAME="IrisGUI"
	MODULE_REAL_NAME="iris.ui.gui"
	MAIN_CLASS="br.unb.cic.iris.gui.MainProgram"
else
	cp src/iris.ui.cli/*.properties build/iris.ui.cli    
	MODULE_NAME="IrisConsole"
	MODULE_REAL_NAME="iris.ui.cli"
	MAIN_CLASS="br.unb.cic.iris.cli.MainProgram" 
fi

echo Creating Module: $MODULE_NAME
$JAVA_BIN/jar --create --file mlib/$MODULE_REAL_NAME@1.0.jar --module-version 1.0 --main-class $MAIN_CLASS -C build/$MODULE_REAL_NAME .


#javadoc
#echo Creating javadocs ...
#rm -Rf javadoc
#mkdir javadoc
#$JAVA_BIN/javadoc -d javadoc -modulepath $JAVA_HOME/jmods:mlib:libs -modulesourcepath src $(find src -name "*.java")


# link
#echo Linking ...
#rm -rf iris
#$JAVA_BIN/jlink --modulepath $JAVA_HOME/jmods:mlib:libs --addmods $MODULE_REAL_NAME --output iris



# run
echo Executing ...
if [ $PERSISTENCE = "lucene" ]; then
# TODO understand why we need to put jars in cp
$JAVA_BIN/java -mp mlib:libs -cp libs/lucene-core-4.10.2.jar:libs/lucene-codecs-4.10.2.jar -m $MODULE_REAL_NAME
else
$JAVA_BIN/java -mp mlib:libs -m $MODULE_REAL_NAME
fi



# run the run-time image created by jlink
#cd mailclient/bin 
#./MailClient


#$JAVA_BIN/java -mp mlib:lib -cp lib/lucene-core-4.10.2.jar:lib/lucene-codecs-4.10.2.jar -m TesteLucene
#$JAVA_BIN/java -cp libs/javax.mail-1.5.5.jar:libs/javax.mail-api-1.5.5.jar:libs/activation-1.1.1.jar -mp mlib -m iris.ui.cli

