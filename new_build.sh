#!/bin/bash

############### BASIC CONFIG ###################
# test: true/false
TEST=true

#interface: gui/cli
INTERFACE=gui

#email_type: simple/smime/pgp
EMAIL_TYPE=simple

#persistence: xml/lucene/relational
PERSISTENCE=xml

#adb: true/false
ADDRESS_BOOK=false

#tags: true/false
TAGS=false

#search: true/false
SEARCH=false
################################################

export JAVA_HOME=/usr/lib/jvm/java-9-oracle

JAVA_BIN=$JAVA_HOME/bin
echo Java: $JAVA_BIN


SRC_DIR=src
TEST_DIR=test
BUILD_DIR=build
MODS_TMP_LIBS_DIR=modules/tmp
MODS_GENERATED_DIR=modules/generated
MODS_AUTOMATIC_DIR=modules/automatic
MODS_CLASSPATH_DIR=modules/libs
MODS_TEST_GENERATED_DIR=modules/test
MODS_TEST_AUTOMATIC_DIR=modules/test-automatic
MODS_TEST_CLASSPATH_DIR=modules/test-libs
#MODS_TEST_TMP_DIR=modules/test-tmp

# copy maven dependencies
mvn -f pom.xml -Dlibs.dest.dir=$MODS_TMP_LIBS_DIR package


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
	cp $MODS_TMP_LIBS_DIR/javax.mail-1.5.5.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/javax.mail-api-1.5.5.jar $MODS_CLASSPATH_DIR
	cp $MODS_TMP_LIBS_DIR/activation-1.1.jar $MODS_CLASSPATH_DIR
} 

copy_persistence_relational_libs () {
	cp $MODS_TMP_LIBS_DIR/sqlite-jdbc-3.8.11.2.jar $MODS_AUTOMATIC_DIR	
}

copy_persistence_lucene_libs () {
	cp $MODS_TMP_LIBS_DIR/lucene-core-4.10.2.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/lucene-analyzers-common-4.10.2.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/lucene-queryparser-4.10.2.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/lucene-queries-4.10.2.jar $MODS_CLASSPATH_DIR
	cp $MODS_TMP_LIBS_DIR/lucene-sandbox-4.10.2.jar $MODS_CLASSPATH_DIR	
	cp $MODS_TMP_LIBS_DIR/lucene-codecs-4.10.2.jar $MODS_CLASSPATH_DIR	
}

copy_secure_smime_libs () {
	cp $MODS_TMP_LIBS_DIR/bcmail-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/bcpkix-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/bcprov-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
}

copy_secure_pgp_libs () {
	cp $MODS_TMP_LIBS_DIR/bcprov-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/bcpg-jdk15on-1.54.jar $MODS_AUTOMATIC_DIR
	cp $MODS_TMP_LIBS_DIR/javamail-crypto-1.0.jar $MODS_AUTOMATIC_DIR	
	cp $MODS_TMP_LIBS_DIR/javamail-crypto-cryptix-openpgp-1.0.jar $MODS_CLASSPATH_DIR
}
#      bcprov-ext-jdk15on-1.54.jar   cryptix-openpgp-provider-1.0.jar    
#bcmail-jdk15on-1.54.jar    cryptix-pki-api-1.0.jar                        
#   cryptix-jce-provider-1.0.jar  hamcrest-core-1.3.jar                   
#bcpkix-jdk15on-1.54.jar  cryptix-message-api-1.0.jar              junit-4.12.jar                         

copy_test_libs () {
	cp $MODS_TMP_LIBS_DIR/junit-4.12.jar $MODS_TEST_AUTOMATIC_DIR	
	cp $MODS_TMP_LIBS_DIR/hamcrest-core-1.3.jar $MODS_TEST_CLASSPATH_DIR	
}




compile_module () {	
	MODULE_NAME=$1
	#echo " - Compiling module: $MODULE_NAME"	
	$JAVA_BIN/javac -mp $MODS_GENERATED_DIR:$MODS_AUTOMATIC_DIR:$MODS_TEST_GENERATED_DIR:$MODS_TEST_AUTOMATIC_DIR -cp $MODS_CLASSPATH_DIR -d $BUILD_DIR/$MODULE_NAME -sourcepath $SRC_DIR/$MODULE_NAME $(find $SRC_DIR/$MODULE_NAME -name "*.java")
}
 
_create_jar_module () {	
	DEST_DIR=$1
	MODULE_NAME=$2
	#echo " - Creating jar module: $MODULE_NAME"	
	$JAVA_BIN/jar --create --file $DEST_DIR/$MODULE_NAME@1.0.jar --module-version 1.0  -C $BUILD_DIR/$MODULE_NAME .	
}

create_jar_module () {	
	_create_jar_module $MODS_GENERATED_DIR $1
}

_create_main_jar_module () {
	DEST_DIR=$1
	MODULE_NAME=$2
	MAIN_CLASS=$3
	$JAVA_BIN/jar --create --file $DEST_DIR/$MODULE_NAME@1.0.jar --module-version 1.0 --main-class $MAIN_CLASS -C $BUILD_DIR/$MODULE_NAME .
}

create_main_jar_module () {
	_create_main_jar_module $MODS_GENERATED_DIR $1 $2	
}

create_module () {
	MODULE=$1
	echo Creating module: $MODULE
    compile_module $MODULE
    create_jar_module $MODULE
}

create_module_main () {
	MODULE=$1
	MAIN_CLASS=$2
	echo Creating main module: $MODULE
    compile_module $MODULE
    create_main_jar_module $MODULE $MAIN_CLASS
}

create_module_test () {
	MODULE=$1	
	echo Creating test module: $MODULE
    compile_module $MODULE
    _create_jar_module $MODS_TEST_GENERATED_DIR $MODULE
}

create_module_test_main () {
	MODULE=$1
	MAIN_CLASS=$2
	echo Creating test module: $MODULE
    compile_module $MODULE
    _create_main_jar_module $MODS_TEST_GENERATED_DIR $MODULE $MAIN_CLASS
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
	BASE_MODULES=(iris.model iris.base iris.mail iris.mail.provider iris.persistence)
	create_modules "${BASE_MODULES[@]}"
	
	MODULE="iris.core"
	mkdir $BUILD_DIR/$MODULE
	cp $SRC_DIR/$MODULE/*.properties $BUILD_DIR/$MODULE
	create_module $MODULE
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
		copy_secure_pgp_libs
		create_module "iris.mail.secure.pgp"
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
	
	
	#pequeno teste antes de uma versao malehor para tests
	if [ $TEST = "true" ]; then
		copy_test_libs
		create_module_test "iris.persistence.test"	
		
		TEST_MODULE="iris.persistence.xml.test"
		MAIN_CLASS="br.unb.cic.iris.persistence.xml.test.TestRunner"
		if [ $PERSISTENCE = "relational" ]; then			
			TEST_MODULE="iris.persistence.jdbc.test"
			MAIN_CLASS="br.unb.cic.iris.persistence.jdbc.test.TestRunner"
		elif [ $PERSISTENCE = "lucene" ]; then					
			TEST_MODULE="iris.persistence.lucene.test"	
			MAIN_CLASS="br.unb.cic.iris.persistence.lucene.test.TestRunner"
		fi						
		create_module_test_main $TEST_MODULE $MAIN_CLASS
		run_test $TEST_MODULE
	fi
	
}

copy_images () {
	MODULE=$1
	SRC=$2
	DEST=$3
	mkdir -p $DEST/$MODULE/images
	cp $SRC/$MODULE/images/* $DEST/$MODULE/images	 
}

create_ui_modules () {
	MODULE_NAME="iris.ui.cli"
	MAIN_CLASS="br.unb.cic.iris.cli.MainProgram"
	if [ $INTERFACE = "gui" ]; then
		MODULE_NAME="iris.ui.gui"
		MAIN_CLASS="br.unb.cic.iris.gui.MainProgram"
		copy_images $MODULE_NAME $SRC_DIR $BUILD_DIR	 	   		
	fi
	cp $SRC_DIR/$MODULE_NAME/*.properties $BUILD_DIR/$MODULE_NAME		
	create_module_main $MODULE_NAME $MAIN_CLASS
}

create_addressbook_modules () {
	if [ $ADDRESS_BOOK = true ]; then
		create_module "iris.addressbook.api"

		if [ $PERSISTENCE = "relational" ] || [ $PERSISTENCE = "lucene" ]; then			
			create_module "iris.addressbook.model.simple"
		fi
		
		MODULE="iris.addressbook.persistence.xml"
		if [ $PERSISTENCE = "relational" ]; then
			MODULE="iris.addressbook.persistence.jdbc"
		elif [ $PERSISTENCE = "lucene" ]; then
			MODULE="iris.addressbook.persistence.lucene"		
		fi
		create_module $MODULE
			
		MODULE="iris.addressbook.ui.cli"
		if [ $INTERFACE = "gui" ]; then			
			MODULE="iris.addressbook.ui.gui"
			copy_images $MODULE $SRC_DIR $BUILD_DIR			
		fi
		create_module $MODULE
	fi
}

create_tagging_modules () {
	if [ $TAGS = true ]; then		
		create_module "iris.tag.api"
		
		if [ $PERSISTENCE = "relational" ] || [ $PERSISTENCE = "lucene" ]; then			
			create_module "iris.tag.model.simple"
		fi
		
		if [ $PERSISTENCE = "relational" ]; then
			create_module "iris.tag.persistence.jdbc"
		elif [ $PERSISTENCE = "lucene" ]; then
			create_module "iris.tag.persistence.lucene"
		else
			create_module "iris.tag.persistence.xml"
		fi
		
		MODULE="iris.tag.ui.cli"
		if [ $INTERFACE = "gui" ]; then
			MODULE="iris.tag.ui.gui"
			copy_images $MODULE $SRC_DIR $BUILD_DIR			
		fi
		create_module $MODULE
	fi
}

create_search_modules () {
	if [ $SEARCH = true ]; then
		create_module "iris.search.api"
		
		if [ $PERSISTENCE = "relational" ]; then
			create_module "iris.search.persistence.jdbc"
		elif [ $PERSISTENCE = "lucene" ]; then
			create_module "iris.search.persistence.lucene"
		else
			create_module "iris.search.persistence.xml"
		fi
	
		MODULE="iris.search.ui.cli"
		if [ $INTERFACE = "gui" ]; then
			MODULE="iris.search.ui.gui"			
			copy_images $MODULE $SRC_DIR $BUILD_DIR						
		fi
		cp $SRC_DIR/$MODULE/*.properties $BUILD_DIR/$MODULE
		create_module $MODULE
	fi
}


create_iris_modules () {
	start=`date +%s`
	echo Creating modules ...
	create_basic_modules
	create_email_module
	create_email_providers	
	create_persistence_modules
	create_ui_modules
	create_addressbook_modules
	create_tagging_modules
	create_search_modules	
	end=`date +%s`
	runtime=$((end-start))
	echo Modules created in $runtime seconds.
}


run () {
	echo Executing ...
	MODULE_NAME="iris.ui.cli"
	if [ $INTERFACE = "gui" ]; then
	 	MODULE_NAME="iris.ui.gui"
	fi

	$JAVA_BIN/java -mp $MODS_GENERATED_DIR:$MODS_AUTOMATIC_DIR -cp $MODS_CLASSPATH_DIR -m $MODULE_NAME
}

run_test () {
	MODULE=$1
	echo Testing: $MODULE
	CLASSPATH="$MODS_TEST_CLASSPATH_DIR/hamcrest-core-1.3.jar" 	
	if [ $PERSISTENCE = "lucene" ]; then
		CLASSPATH=$CLASSPATH:$MODS_AUTOMATIC_DIR/lucene-core-4.10.2.jar:$MODS_CLASSPATH_DIR/lucene-codecs-4.10.2.jar
		#CLASSPATH=$CLASSPATH:$MODS_CLASSPATH_DIR/lucene-codecs-4.10.2.jar
	fi
	
	echo $JAVA_BIN/java -mp $MODS_GENERATED_DIR:$MODS_AUTOMATIC_DIR:$MODS_TEST_GENERATED_DIR:$MODS_TEST_AUTOMATIC_DIR -cp $MODS_TEST_CLASSPATH_DIR/hamcrest-core-1.3.jar -m $MODULE
	$JAVA_BIN/java -mp $MODS_GENERATED_DIR:$MODS_AUTOMATIC_DIR:$MODS_TEST_GENERATED_DIR:$MODS_TEST_AUTOMATIC_DIR -cp $CLASSPATH -m $MODULE
}


################### EXECUTE ###################
clean_all
create_iris_modules
#run