package br.unb.cic.iris.mail.secure.pgp.internal;

import static br.unb.cic.iris.mail.MessageUtils.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchProviderException;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.mail.MessageUtils;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;
import net.suberic.crypto.EncryptionManager;
import net.suberic.crypto.EncryptionUtils;

public class PgpSecurityManager implements EmailSecurityManager {
	private static final PgpSecurityManager instance = new PgpSecurityManager();
	private PgpKeyManager keyManager;
	private EncryptionUtils cryptoUtils;

	public PgpSecurityManager() {
		try {
			keyManager = new PgpKeyManager();
			cryptoUtils = getEncryptionUtils();
		} catch (NoSuchProviderException e) {
			throw new IrisUncheckedException("No such provider: " + e.getMessage(), e);
		} catch (FileNotFoundException e) {
			throw new IrisUncheckedException("Config file not found: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new IrisUncheckedException("Error reading config file: " + e.getMessage(), e);
		}
	}

	public static PgpSecurityManager instance() {
		return instance;
	}

	private EncryptionUtils getEncryptionUtils() throws NoSuchProviderException {
		return EncryptionManager.getEncryptionUtils(EncryptionManager.PGP);
	}

	@Override
	public MimeMessage encrypt(Session mailSession, MimeMessage message) throws Exception {
		String to = MessageUtils.getTo(message);
		System.out.println("Encrypting message to: " + to);
		java.security.Key pgpPublicKey = keyManager.getPublicKey(to);
		System.out.println("pgpPublicKey: " + pgpPublicKey);
		return cryptoUtils.encryptMessage(mailSession, message, pgpPublicKey);
	}
	
	@Override
	public MimeMessage encrypt(MimeMessage message) throws Exception {		
		return encrypt(message.getSession(), message);
	}

	@Override
	public Object decrypt(MimeMessage message) throws Exception {
		return decrypt(message.getSession(), message);
	}
	
	@Override
	public Object decrypt(Session mailSession, MimeMessage message) throws Exception {
		String to = MessageUtils.getTo(message);
		System.out.println("Decrypting message: " + to);
		java.security.Key pgpPrivateKey = keyManager.getPrivateKey(to);
		return cryptoUtils.decryptMessage(mailSession, message, pgpPrivateKey);
	}

	@Override
	public MimeMessage sign(Session mailSession, MimeMessage message) throws Exception {
		String from = MessageUtils.getFrom(message);
		System.out.println("Signing message: " + from);
		Key privateKey = keyManager.getPrivateKey(from);
		System.out.println("privateKey=" + privateKey);
		return cryptoUtils.signMessage(mailSession, message, privateKey);
	}
	
	@Override
	public MimeMessage sign(MimeMessage message) throws Exception {		
		return sign(message.getSession(), message);
	}

	@Override
	public void verifySignature(MimePart msg, String from) throws Exception {
		System.out.println("Verify signature ...");
		System.out.println("FROM: " + from);
		Key publicKey = keyManager.getPublicKey(from);
		System.out.println("PUBLIC KEY: " + publicKey);		
		if(!cryptoUtils.checkSignature(msg, publicKey)){
			throw new IrisException("FALHA ASSINATURA .............");
		}
	}

	@Override
	public void verifySignature(MimeMultipart multi, String from) throws Exception {
		System.out.println("Verify signature ...");
		System.out.println("FROM: " + from);
		Key publicKey = keyManager.getPublicKey(from);
		System.out.println("PUBLIC KEY: " + publicKey);
		if(!cryptoUtils.checkSignature(multi, publicKey)){
			throw new IrisException("FALHA ASSINATURA .............");
		}
	}
	
	@Override
	public void verifySignature(MimeMessage msg) throws Exception {
		System.out.println("Verify signature ...");
		String from = getFrom(msg);
		System.out.println("FROM: " + from);
		Key publicKey = keyManager.getPublicKey(from);
		System.out.println("PUBLIC KEY: " + publicKey);		
		if(!cryptoUtils.checkSignature(msg, publicKey)){
			throw new IrisException("FALHA ASSINATURA .............");
		}
	}
	




	

	
	
	
	
	
	
	
	/////////////////////////////////

//	/*** added by dSecurityVerify
//	 */
//	public boolean verifySignature(Session mailSession, MimeMessage signedMsg)
//	throws Exception {
//		System.out.println("Verify signature ...");
//		String from = signedMsg.getFrom()[0].toString();
//		from = from.substring(from.indexOf('<') + 1);
//		from = from.substring(0, from.length() - 1);
//		System.out.println("FROM: " + from);
//		Key publicKey = keyManager.getPublicKey(from);
//		System.out.println("PUBLIC KEY: " + publicKey);
//		return cryptoUtils.checkSignature(signedMsg, publicKey);
//	}
//	/*** added by dSecurityVerify
//	 */
//	public boolean verifySignature(Session mailSession, MimePart part, String
//		from) throws Exception {
//		System.out.println("Verify signature ...");
//		System.out.println("FROM: " + from);
//		Key publicKey = keyManager.getPublicKey(from);
//		System.out.println("PUBLIC KEY: " + publicKey);
//		return cryptoUtils.checkSignature(part, publicKey);
//	}
//	/*** added by dSecurityVerify
//	 */
//	public boolean verifySignature(Session session, MimeMultipart multi, String
//		from) throws Exception {
//		System.out.println("Verify signature ...");
//		System.out.println("FROM: " + from);
//		Key publicKey = keyManager.getPublicKey(from);
//		System.out.println("PUBLIC KEY: " + publicKey);
//		return cryptoUtils.checkSignature(multi, publicKey);
//	}
//	/*** added by dSecurityEncrypt
//	 */
//	public MimeMessage encrypt(Session mailSession, MimeMessage msg, String
//		publicKeyAlias) throws Exception {
//		System.out.println("Encrypting message to: " + publicKeyAlias);
//		java.security.Key pgpPublicKey = keyManager.getPublicKey(publicKeyAlias);
//		System.out.println("pgpPublicKey: " + pgpPublicKey);
//		return cryptoUtils.encryptMessage(mailSession, msg, pgpPublicKey);
//	}
//	/*** added by dSecurityDecrypt
//	 */
//	public MimeMessage decrypt(MimeMessage msg, String privateKeyAlias, Session
//		mailSession) throws Exception {
//		System.out.println("Decrypting message: " + privateKeyAlias);
//		java.security.Key pgpPrivateKey = keyManager.getPrivateKey(privateKeyAlias);
//		return cryptoUtils.decryptMessage(mailSession, msg, pgpPrivateKey);
//	}
//	/*** added by dSecurityDecrypt
//	 */
//	public MimeMessage decrypt(Session mailSession, MimeMessage msg) throws Exception {
//		String to = new InternetAddress(msg.getRecipients(RecipientType.TO)[0].toString()).getAddress();
//		//to = to.substring(to.indexOf('<') + 1);
//		//to = to.substring(0, to.length() - 1);
//		//to = SystemFacade.instance().getProvider().getUsername();
//		System.out.println("TO: " + to);
//		return decrypt(msg, to, mailSession);
//	}



}
