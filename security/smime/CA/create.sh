#!/bin/bash 

IRIS_HOME=$HOME/.iris
IRIS_CA_DIR=$IRIS_HOME/CA

CA_KEY_FILE=$IRIS_CA_DIR/rootCA.key
CA_CERT_FILE=$IRIS_CA_DIR/rootCA.pem
CA_CERT_FILE_PKCS12=$IRIS_CA_DIR/rootCA.p12
CA_SUBJECT_DN="/C=BR/ST=DistritoFederal/L=Brasilia/O=UNB/OU=CIC/CN=IrisCertificationAuthority"
CA_ALIAS=iris_ca

CLIENT_DIR=$IRIS_HOME/client

KEYSTORE=$IRIS_HOME/keystore.pkcs12
KEYSTORE_TMP=$IRIS_HOME/keystore_tmp.pkcs12
KEYSTORE_PASSWORD=123456
KEYSTORE_TYPE=PKCS12

KEY_SIZE=2048
VALIDITY=3650


########## FUNCTIONS ########## 
clean () {
	rm -Rf $IRIS_CA_DIR
	mkdir -p $IRIS_CA_DIR
	rm -Rf $CLIENT_DIR
	mkdir -p $CLIENT_DIR
	rm -Rf $KEYSTORE
}

create_CA () {
	echo Creating CA ...
	
	clean
	
	openssl genrsa -out $CA_KEY_FILE $KEY_SIZE
	openssl req -x509 -new -nodes -key $CA_KEY_FILE -sha256 -days $VALIDITY -subj $CA_SUBJECT_DN -out $CA_CERT_FILE
	openssl pkcs12 -export -out $CA_CERT_FILE_PKCS12 -inkey $CA_KEY_FILE -in $CA_CERT_FILE -certfile $CA_CERT_FILE -passout pass:$KEYSTORE_PASSWORD
	keytool -import -trustcacerts -alias $CA_ALIAS -file $CA_CERT_FILE -storetype $KEYSTORE_TYPE -storepass $KEYSTORE_PASSWORD -keystore $KEYSTORE -noprompt
		
	echo CA created
}

create_client_cert () {
	CLIENT_ALIAS=$1
	CLIENT_SUBJECT_DN=$2
	#password: same of keystore for now
	CLIENT_PASSWORD=$KEYSTORE_PASSWORD		
		
	CLIENT_KEY=$CLIENT_DIR/$CLIENT_ALIAS.key
	CLIENT_CSR=$CLIENT_DIR/$CLIENT_ALIAS.csr
	CLIENT_CERT=$CLIENT_DIR/$CLIENT_ALIAS.crt
	CLIENT_CERT_PKCS12=$CLIENT_DIR/$CLIENT_ALIAS.p12
	
	openssl genrsa -out $CLIENT_KEY $KEY_SIZE
	openssl req -new -key $CLIENT_KEY -out $CLIENT_CSR -subj $CLIENT_SUBJECT_DN
	openssl x509 -req -in $CLIENT_CSR -CA $CA_CERT_FILE -CAkey $CA_KEY_FILE -CAcreateserial -out $CLIENT_CERT -days $VALIDITY -sha256
	openssl pkcs12 -export -out $CLIENT_CERT_PKCS12 -inkey $CA_KEY_FILE -in $CA_CERT_FILE -certfile $CLIENT_CERT -passout pass:$CLIENT_PASSWORD
	rm $CLIENT_CSR
	
	openssl pkcs12 -export -in $CLIENT_CERT -inkey $CLIENT_KEY -out $KEYSTORE_TMP -name $CLIENT_ALIAS -noiter -nomaciter -passout pass:$KEYSTORE_PASSWORD
	keytool -v -importkeystore -srckeystore $KEYSTORE_TMP -srcalias $CLIENT_ALIAS -srcstoretype $KEYSTORE_TYPE -srcstorepass $CLIENT_PASSWORD -destkeystore $KEYSTORE -destalias $CLIENT_ALIAS -deststorepass $KEYSTORE_PASSWORD -destkeypass $CLIENT_PASSWORD -deststoretype $KEYSTORE_TYPE -noprompt
	rm $KEYSTORE_TMP	
	
	#echo 'COMMAND TO LIST KEYSTORE ENTRIES: keytool -list -v -storetype '$KEYSTORE_TYPE' -storepass '$KEYSTORE_PASSWORD' -keystore '$KEYSTORE
	echo Certificate created for: $CLIENT_ALIAS
}


########### EXECUTE ############
create_CA
create_client_cert "br.unb.cic.iris@gmail.com" "/C=BR/ST=DistritoFederal/L=Brasilia/O=UNB/OU=CIC/CN=iris/emailAddress=br.unb.cic.iris@gmail.com"
keytool -list -storetype $KEYSTORE_TYPE -storepass $KEYSTORE_PASSWORD -keystore $KEYSTORE
