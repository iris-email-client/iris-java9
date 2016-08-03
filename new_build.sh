#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN

############### BASIC CONFIG ###################
#interface: gui/cli
INTERFACE=cli

#email_type: simple/smime/pgp
EMAIL_TYPE=smime

#persistence: xml/lucene/relational
PERSISTENCE=xml

#adb: true/false
ADDRESS_BOOK=false

#tags: true/false
TAGS=true

#search: true/false
SEARCH=true
################################################

mvn -f pom.xml package

SRC_DIR=src
BUILD_DIR=build
MODS_TMP_LIBS_DIR=modules/tmp
MODS_GENERATED_DIR=modules/generated
MODS_AUTOMATIC_DIR=modules/automatic
MODS_CLASSPATH_DIR=modules/libs
MODS_TEST_GENERATED_DIR=modules/test
MODS_TEST_AUTOMATIC_DIR=modules/test-automatic
MODS_TEST_CLASSPATH_DIR=modules/test-libs
MODS_TEST_TMP_DIR=modules/test-tmp


#echo Compiling ...
#$JAVA_BIN/javac -Xlint:unchecked -mp $MODS_GENERATED_DIR:$MODS_TMP_LIBS_DIR -cp $MODS_TMP_LIBS_DIR -d build -modulesourcepath src $(find src -name "*.java")


### TODO rever os clean
clean () {
	echo Cleaning ...
	rm -rf $BUILD_DIR
	mkdir -p $BUILD_DIR
	rm -rf $MODS_GENERATED_DIR
	mkdir -p $MODS_GENERATED_DIR	
	rm -rf $MODS_TEST_GENERATED_DIR
	mkdir -p $MODS_TEST_GENERATED_DIR
}

clean_all () {
	echo Cleaning ...
	rm -rf $BUILD_DIR
	mkdir -p $BUILD_DIR
	rm -rf $MODS_AUTOMATIC_DIR
	mkdir -p $MODS_AUTOMATIC_DIR	
	
	rm -rf $MODS_GENERATED_DIR
	mkdir -p $MODS_GENERATED_DIR	
	rm -rf $MODS_CLASSPATH_DIR
	mkdir -p $MODS_CLASSPATH_DIR	
	rm -rf $MODS_TEST_GENERATED_DIR
	mkdir -p $MODS_TEST_GENERATED_DIR
	rm -rf $MODS_TEST_AUTOMATIC_DIR
	mkdir -p $MODS_TEST_AUTOMATIC_DIR
	rm -rf $MODS_TEST_CLASSPATH_DIR
	mkdir -p $MODS_TEST_CLASSPATH_DIR
}

copy_mail_libs () {
	cp $MODS_TEST_TMP_DIR/javax.mail-1.5.5.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TEST_TMP_DIR/javax.mail-api-1.5.5.jar $MODS_CLASSPATH_DIR
	cp $MODS_TEST_TMP_DIR/activation-1.1.jar $MODS_CLASSPATH_DIR
} 

copy_persistence_relational_libs () {
	cp $MODS_TEST_TMP_DIR/sqlite-jdbc-3.8.11.2.jar $MODS_AUTOMATIC_DIR	
}

copy_persistence_lucene_libs () {
	cp $MODS_TEST_TMP_DIR/lucene-core-4.10.2.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TEST_TMP_DIR/lucene-analyzers-common-4.10.2.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TEST_TMP_DIR/lucene-queryparser-4.10.2.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TEST_TMP_DIR/lucene-queries-4.10.2.jar $MODS_CLASSPATH_DIR
	cp $MODS_TEST_TMP_DIR/lucene-sandbox-4.10.2.jar $MODS_CLASSPATH_DIR	
	cp $MODS_TEST_TMP_DIR/lucene-codecs-4.10.2.jar $MODS_CLASSPATH_DIR	
}

copy_secure_smime_libs () {
	cp $MODS_TEST_TMP_DIR/bcmail-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TEST_TMP_DIR/bcpkix-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TEST_TMP_DIR/bcprov-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
}

compile_module () {	
	MODULE_NAME=$1
	#echo " - Compiling module: $MODULE_NAME"
	#$JAVA_BIN/javac -mp $MODS_GENERATED_DIR:$MODS_AUTOMATIC_DIR:$MODS_TEST_AUTOMATIC_DIR -cp $MODS_CLASSPATH_DIR -d $BUILD_DIR/$MODULE_NAME -sourcepath $SRC_DIR/$MODULE_NAME $(find $SRC_DIR/$MODULE_NAME -name "*.java")	
	$JAVA_BIN/javac -Xlint:unchecked -mp $MODS_GENERATED_DIR:$MODS_AUTOMATIC_DIR:$MODS_TEST_AUTOMATIC_DIR -cp $MODS_CLASSPATH_DIR -d $BUILD_DIR/$MODULE_NAME -sourcepath $SRC_DIR/$MODULE_NAME $(find $SRC_DIR/$MODULE_NAME -name "*.java")	
}

create_jar_module () {	
	MODULE_NAME=$1
	#echo " - Creating jar module: $MODULE_NAME"	
	$JAVA_BIN/jar --create --file $MODS_GENERATED_DIR/$MODULE_NAME@1.0.jar --module-version 1.0  -C $BUILD_DIR/$MODULE_NAME .	
}

create_module () {
	MODULE=$1
	echo Creating module: $MODULE
    compile_module $MODULE
    create_jar_module $MODULE
}

create_modules () {	
	MODULES=("${@}")
	for module in ${MODULES[*]};
	do
	    create_module $module
	done
}

create_basic_modules () {
	echo Creating basic modules ...
	copy_mail_libs
	BASE_MODULES=(iris.model iris.base iris.mail iris.mail.provider iris.persistence iris.core)
	create_modules "${BASE_MODULES[@]}"
}

create_email_providers () {
	PROVIDERS=(iris.mail.provider.gmail iris.mail.provider.outlook iris.mail.provider.yahoo)
	create_modules "${PROVIDERS[@]}"
}

create_email_module () {	
	if [ $EMAIL_TYPE = "smime" ] || [ $EMAIL_TYPE = "pgp" ]; then
		create_module "iris.mail.secure"
	fi
	
	if [ $EMAIL_TYPE = "smime" ]; then	
		copy_secure_smime_libs	
		create_module "iris.mail.secure.smime"
	elif [ $EMAIL_TYPE = "pgp" ]; then
		echo iris.mail.secure.pgp PGP NOT YET IMPLEMENTED!!!!!!!	
	else		
		create_module "iris.mail.simple"
	fi
}

create_persistence_modules () {	
	if [ $PERSISTENCE = "relational" ] || [ $PERSISTENCE = "lucene" ]; then
		create_module "iris.model.simple"
	fi
	MODULE="iris.persistence.xml"
	if [ $PERSISTENCE = "relational" ]; then
		copy_persistence_relational_libs
		MODULE="iris.persistence.jdbc"
	elif [ $PERSISTENCE = "lucene" ]; then
		copy_persistence_lucene_libs
		MODULE="iris.persistence.lucene"	
	fi
	create_module $MODULE
}

create_iris_modules () {
	echo Creating modules ...
	create_basic_modules
	create_email_module
	create_email_providers	
	create_persistence_modules
}




################### EXECUTE ###################
clean_all
create_iris_modules
