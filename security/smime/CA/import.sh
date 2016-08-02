#!/bin/bash 
#Common commands: 
#https://shib.kuleuven.be/docs/ssl_commands.shtml


CA_DIR=ca
CA_KEY_FILE=$CA_DIR/rootCA.key
CA_CERT_FILE=$CA_DIR/rootCA.pem

KEYSTORE_TMP=keystore_tmp.pkcs12
KEYSTORE=keystore.pkcs12
KEYSTORE_PASSWORD=123456
KEYSTORE_TYPE=PKCS12

############## basic changes #############
CLIENT_ALIAS=br.unb.cic.iris@gmail.com
CLIENT_PASSWORD=123456
SUBJECT_DN="/C=BR/ST=DistritoFederal/L=Brasilia/O=UNB/OU=CIC/CN=iris/emailAddress=br.unb.cic.iris@gmail.com"
##########################################

CLIENT_DIR=client
CLIENT_KEY=$CLIENT_DIR/$CLIENT_ALIAS.key
CLIENT_CSR=$CLIENT_DIR/$CLIENT_ALIAS.csr
CLIENT_CERT=$CLIENT_DIR/$CLIENT_ALIAS.crt
CLIENT_CERT_PKCS12=$CLIENT_DIR/$CLIENT_ALIAS.p12

##### IMPORT USING key, crt FILES
#openssl pkcs12 -export -in $CLIENT_CERT -inkey $CLIENT_KEY -out $KEYSTORE_TMP -name $CLIENT_ALIAS -noiter -nomaciter -passout pass:$KEYSTORE_PASSWORD
#keytool -v -importkeystore -srckeystore $KEYSTORE_TMP -srcalias $CLIENT_ALIAS -srcstoretype $KEYSTORE_TYPE -srcstorepass $CLIENT_PASSWORD -destkeystore $KEYSTORE -destalias $CLIENT_ALIAS -deststorepass $KEYSTORE_PASSWORD -destkeypass $CLIENT_PASSWORD -deststoretype PKCS12 -noprompt
#rm $KEYSTORE_TMP

##### IMPORT USING p12 FILE
#keytool -list -v -storetype $KEYSTORE_TYPE -storepass $CLIENT_PASSWORD -keystore $CLIENT_CERT_PKCS12
keytool -changealias -storetype $KEYSTORE_TYPE -keystore $CLIENT_CERT_PKCS12 -storepass $CLIENT_PASSWORD -alias 1 -destalias $CLIENT_ALIAS
keytool -v -importkeystore -srckeystore $CLIENT_CERT_PKCS12 -srcalias $CLIENT_ALIAS -srcstoretype $KEYSTORE_TYPE -srcstorepass $CLIENT_PASSWORD -destkeystore $KEYSTORE -destalias $CLIENT_ALIAS -deststorepass $KEYSTORE_PASSWORD -destkeypass $CLIENT_PASSWORD -deststoretype PKCS12 -noprompt


#keytool -list -v -storetype $KEYSTORE_TYPE -storepass $KEYSTORE_PASSWORD -keystore $KEYSTORE -alias $CLIENT_ALIAS

echo 'COMMAND TO LIST KEYSTORE ENTRIES: keytool -list -v -storetype '$KEYSTORE_TYPE' -storepass '$KEYSTORE_PASSWORD' -keystore '$KEYSTORE
