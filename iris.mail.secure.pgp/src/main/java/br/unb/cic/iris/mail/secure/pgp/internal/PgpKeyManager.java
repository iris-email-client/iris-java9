package br.unb.cic.iris.mail.secure.pgp.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Iterator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.unb.cic.iris.base.Configuration;
import br.unb.cic.iris.exception.IrisException;
import net.suberic.crypto.EncryptionKeyManager;
import net.suberic.crypto.EncryptionManager;
import net.suberic.crypto.EncryptionUtils;

/*import
irisdeltaj.simplerelationalsecuritycomplete.br.unb.cic.iris.core.Configuration;
import
irisdeltaj.simplerelationalsecuritycomplete.br.unb.cic.iris.core.exception.EmailException;*/
/***
 * added by dSecurityPgp
 */
@SuppressWarnings("rawtypes")
public class PgpKeyManager {
	public static final String CONFIG_FILE_PRIVATE = "gpg.file.private";
	public static final String CONFIG_FILE_PUBLIC = "gpg.file.public";
	public static final String CONFIG_FILE_SECRET = "gpg.file.secret";
	static final String PROVIDER = "BC";
	static final int KEY_SIZE = 2048;
	private String PRIVATE_FILE;
	private String PUBLIC_FILE;
	private char[] FILE_SECRET;
	private EncryptionUtils pgpUtils;
	
	static {
		Security.insertProviderAt(new BouncyCastleProvider(), 0);
		System.out.println("Adding Bouncy Castle Provider ...");
	}

	public PgpKeyManager() throws FileNotFoundException, IOException, NoSuchProviderException {
		PRIVATE_FILE = Configuration.getProperty(CONFIG_FILE_PRIVATE);
		PUBLIC_FILE = Configuration.getProperty(CONFIG_FILE_PUBLIC);
		System.out.println("Public keystore: " + PUBLIC_FILE);
		System.out.println("Private keystore: " + PRIVATE_FILE);
		FILE_SECRET = Configuration.getProperty(CONFIG_FILE_SECRET).toCharArray();
		pgpUtils = getEncryptionUtils();
	}

	public Key getPrivateKey(String id) throws Exception {
		EncryptionKeyManager pgpKeyMgr = pgpUtils.createKeyManager();
		pgpKeyMgr.loadPrivateKeystore(new FileInputStream(new File(PRIVATE_FILE)), FILE_SECRET);
		Iterator it = pgpKeyMgr.privateKeyAliases().iterator();
		while (it.hasNext()) {
			String alias = it.next().toString();
			if (alias.contains(id)) {
				return pgpKeyMgr.getPrivateKey(alias, FILE_SECRET);
			}
		}
		throw new IrisException("Couldn't find private key for: " + id + ". Please, generate the key pair");
	}

	public Key getPublicKey(String email) throws Exception {
		String publicKeyAlias = getLocalAlias(email);
		System.out.println("publicKeyAlias: " + publicKeyAlias);
		EncryptionKeyManager pgpKeyMgr = pgpUtils.createKeyManager();
		pgpKeyMgr.loadPublicKeystore(new FileInputStream(new File(PUBLIC_FILE)), FILE_SECRET);
		return pgpKeyMgr.getPublicKey(publicKeyAlias);
	}

	public String getLocalAlias(String email) throws Exception {
		EncryptionKeyManager pgpKeyMgr = pgpUtils.createKeyManager();
		pgpKeyMgr.loadPublicKeystore(new FileInputStream(new File(PUBLIC_FILE)), FILE_SECRET);
		Iterator it = pgpKeyMgr.publicKeyAliases().iterator();
		while (it.hasNext()) {
			String alias = it.next().toString();
			if (alias.contains(email)) {
				return alias;
			}
		}
		throw new IrisException("Couldn't find public key for: " + email + ". Please, install the public key.");
	}

	private EncryptionUtils getEncryptionUtils() throws NoSuchProviderException {
		return EncryptionManager.getEncryptionUtils(EncryptionManager.PGP);
	}

	public boolean existKeys() {
		return new File(PRIVATE_FILE).exists() && new File(PUBLIC_FILE).exists();
	}
}