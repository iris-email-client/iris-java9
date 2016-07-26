#!/bin/bash 

#
# Script used to compile, $BUILD_DIR jars and run
# For now: You need to put all dependencies(jars) in 'libs' dir
# 
# USAGE:
#  * define basic config and run:
# ./$BUILD_DIR.sh
#

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN

mvn -f pom-test.xml package

SRC_DIR=src
BUILD_DIR=build
MODS_GENERATED_DIR=modules/generated
MODS_AUTOMATIC_DIR=modules/automatic
MODS_CLASSPATH_DIR=modules/libs
MODS_TEST_GENERATED_DIR=modules/test
MODS_TEST_AUTOMATIC_DIR=modules/test-automatic
MODS_TEST_CLASSPATH_DIR=modules/test-libs
MODS_TEST_TMP_DIR=modules/test-tmp

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
	copy_mail_libs
	BASE_MODULES=(iris.model iris.base iris.mail iris.mail.provider iris.persistence iris.core)
	create_modules "${BASE_MODULES[@]}"
}

create_email_module () {
	# iris.mail.simple or iris.mail.secure (not yet implemented)
	create_module "iris.mail.simple"
}

create_email_providers () {
	PROVIDERS=(iris.mail.provider.gmail iris.mail.provider.outlook iris.mail.provider.yahoo)
	create_modules "${PROVIDERS[@]}"
}

create_main_modules () {
	create_basic_modules
	create_email_module
	create_email_providers
}

create_persistence_modules () {
	PERSISTENCE=$1
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

create_test_modules () {
	
}









clean_all
create_main_modules


#apenas para testar
#PERSISTENCE_MODES=(xml relational lucene)
PERSISTENCE_MODES=(xml)
create_persistence_modules "${PERSISTENCE_MODES[@]}"
