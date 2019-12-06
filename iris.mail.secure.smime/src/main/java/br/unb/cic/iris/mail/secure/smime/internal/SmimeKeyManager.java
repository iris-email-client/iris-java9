package br.unb.cic.iris.mail.secure.smime.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.exception.IrisException;

public class SmimeKeyManager {
	private static final String BC = BouncyCastleProvider.PROVIDER_NAME;

	private static SmimeKeyManager instance;

	//TODO usando a mesma senha sempre, inclusive para as chaves privadas ... o usuario deve informar a senha da chave privada dele
	private String pkcs12Keystore = Configuration.IRIS_HOME+Configuration.FILE_SEPARATOR+"keystore.pkcs12";
	private String keystorePassword = "123456";

	private KeyStore keystore;

	private SmimeKeyManager() throws Exception {
		// Security.addProvider(new BouncyCastleProvider());

		loadKeystore();
	}

	public static SmimeKeyManager instance() throws Exception {
		if (instance == null) {
			instance = new SmimeKeyManager();
		}
		return instance;
	}

	private void loadKeystore() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, NoSuchProviderException {
		/* Open the keystore */
		System.out.println("Loading keystore: " + pkcs12Keystore);
		keystore = KeyStore.getInstance("PKCS12", BC);
		keystore.load(new FileInputStream(pkcs12Keystore), keystorePassword.toCharArray());
	}

	public X509Certificate getCertificate(String keyAlias) throws KeyStoreException {
		return (X509Certificate) keystore.getCertificate(keyAlias);
		//return (X509Certificate) keystore.getCertificateChain(keyAlias)[0];
	}

	public X509Certificate getCertificateByEmail(String email) throws KeyStoreException, IrisException {
		Enumeration<String> aliases = keystore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			System.out.println("    - alias=" + alias);
			X509Certificate certificate = getCertificate(alias);
			if (certificate != null) {
				System.out.println("    - cert=" + certificate.getSubjectDN());
				String name = certificate.getSubjectDN().getName();
				String certEmail = name.substring(name.lastIndexOf("E=") + 2);
				if (email.equalsIgnoreCase(certEmail)) {
					return certificate;
				}
			}
		}
		throw new IrisException("No certificate for email: " + email);
	}

	private PrivateKey getPrivateKey(String keyAlias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IrisException {
		/* Get the private key to sign the message with */
		System.out.println("Loading private key for: " + keyAlias);
		System.out.println("isKeyEntry: "+keystore.isKeyEntry(keyAlias));
		// TODO eh a chave do alias ... nao do keystore ... o usuario deve informar a senha da chave privada dele
		PrivateKey privateKey = (PrivateKey) keystore.getKey(keyAlias, keystorePassword.toCharArray());
		if (privateKey == null) {
			throw new IrisException("Cannot find private key for alias: " + keyAlias);
		}
		return privateKey;
	}

	private PrivateKey getPrivateKeyByEmail(String email) throws KeyStoreException, IrisException, UnrecoverableKeyException, NoSuchAlgorithmException {
		X509Certificate certificate = getCertificateByEmail(email);
		return getPrivateKeyByCertificate(certificate);
	}

	public PrivateKey getPrivateKeyByCertificate(Certificate certificate) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IrisException {
		return getPrivateKey(keystore.getCertificateAlias(certificate));
	}

}
