package br.unb.cic.iris.mail.secure.smime.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.asn1.smime.SMIMEEncryptionKeyPreferenceAttribute;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.RecipientInformationStore;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientId;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;
import org.bouncycastle.mail.smime.SMIMESigned;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.mail.smime.SMIMEUtil;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.Strings;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;



public class SmimeSecurityManager implements EmailSecurityManager {	
	private static final String BC = BouncyCastleProvider.PROVIDER_NAME;
	
	private static SmimeSecurityManager instance;
	
	private SmimeSecurityManager(){
		Security.addProvider(new BouncyCastleProvider());
		
		MailcapCommandMap mailcap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mailcap.addMailcap("application/pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_signature");
		mailcap.addMailcap("application/pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.pkcs7_mime");
		mailcap.addMailcap("application/x-pkcs7-signature;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_signature");
		mailcap.addMailcap("application/x-pkcs7-mime;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.x_pkcs7_mime");
		mailcap.addMailcap("multipart/signed;; x-java-content-handler=org.bouncycastle.mail.smime.handlers.multipart_signed");
		CommandMap.setDefaultCommandMap(mailcap);			
	}
	
	public static SmimeSecurityManager instance(){
		if (instance == null){
			instance = new SmimeSecurityManager();
		}
		return instance;
	}
	
	public Object decrypt(MimeMessage msg) throws Exception {
//		System.out.println("decrypt ....");
//		String pkcs12Keystore = "/home/pedro/desenvolvimento/workspace-java9/criptografia/CA/teste.pkcs12";
//		String keystorePassword = "123456";
//		String keyAlias = "iris";
//		//SMIMEToolkit toolkit = new SMIMEToolkit(new BcDigestCalculatorProvider());
//
//		//
//		// Open the key store
//		//
//		KeyStore ks = KeyStore.getInstance("PKCS12", BC);
//		System.out.println("Loading keystore: " + pkcs12Keystore);
//
//		ks.load(new FileInputStream(pkcs12Keystore), keystorePassword.toCharArray());
//
//		/*
//		 * Enumeration e = ks.aliases(); String keyAlias = null;
//		 * 
//		 * while (e.hasMoreElements()) { String alias = (String)
//		 * e.nextElement();
//		 * 
//		 * if (ks.isKeyEntry(alias)) { keyAlias = alias; } }
//		 * 
//		 * if (keyAlias == null) {
//		 * System.err.println("can't find a private key!"); System.exit(0); }
//		 */

		//
		// find the certificate for the private key and generate a
		// suitable recipient identifier.
		//
		//TODO eh TO
		String to = getEmail(msg.getRecipients(RecipientType.TO));
		System.out.println("DESTINATARIO: "+to);
		//X509Certificate certificate = SmimeKeyManager.instance().getCertificateByEmail(to);
		X509Certificate certificate = SmimeKeyManager.instance().getCertificate(to);
		System.out.println("CERTIFICATE: "+certificate.getSubjectDN().getName());
		PrivateKey privateKey = SmimeKeyManager.instance().getPrivateKeyByCertificate(certificate);
		System.out.println("PRIVATE_KEY: "+privateKey.getAlgorithm());
		
		//X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);
		RecipientId recId = new JceKeyTransRecipientId(certificate);

		SMIMEEnveloped m = new SMIMEEnveloped(msg);

		RecipientInformationStore recipients = m.getRecipientInfos();
		RecipientInformation recipient = recipients.get(recId);

		MimeBodyPart res = SMIMEUtil.toMimeBodyPart(recipient.getContent(new JceKeyTransEnvelopedRecipient(privateKey).setProvider(BC)));

//		System.out.println("Message Contents");
//		System.out.println("----------------");
//		System.out.println(res.getContent());
		
		return res.getContent();
	}

	public void verify(Message msg) throws Exception {
		//
		// make sure this was a multipart/signed message - there should be
		// two parts as we have one part for the content that was signed and
		// one part for the actual signature.
		//
		if (msg.isMimeType("multipart/signed")) {
			SMIMESigned s = new SMIMESigned((MimeMultipart) msg.getContent());

			//
			// extract the content
			//
			/*MimeBodyPart content = s.getContent();

			System.out.println("Content:");

			Object cont = content.getContent();

			if (cont instanceof String) {
				System.out.println((String) cont);
			} else if (cont instanceof Multipart) {
				Multipart mp = (Multipart) cont;
				int count = mp.getCount();
				for (int i = 0; i < count; i++) {
					BodyPart m = mp.getBodyPart(i);
					Object part = m.getContent();

					System.out.println("Part " + i);
					System.out.println("---------------------------");

					if (part instanceof String) {
						System.out.println((String) part);
					} else {
						System.out.println("can't print...");
					}
				}
			}

			System.out.println("Status:");*/

			verify(s);
		} else if (msg.isMimeType("application/pkcs7-mime") || msg.isMimeType("application/x-pkcs7-mime")) {
			//
			// in this case the content is wrapped in the signature block.
			//
			SMIMESigned s = new SMIMESigned(msg);

			//
			// extract the content
			//
			/*MimeBodyPart content = s.getContent();

			System.out.println("Content:");

			Object cont = content.getContent();

			if (cont instanceof String) {
				System.out.println((String) cont);
			}

			System.out.println("Status:");*/

			verify(s);
		} else {
			System.err.println("Not a signed message!");
		}
	}

	

	/**
	 * verify the signature (assuming the cert is contained in the message)
	 */
	public void verify(SMIMESigned s) throws Exception {
		//
		// extract the information to verify the signatures.
		//

		//
		// certificates and crls passed in the signature
		//
		Store certs = s.getCertificates();

		//
		// SignerInfo blocks which contain the signatures
		//
		SignerInformationStore signers = s.getSignerInfos();

		Collection c = signers.getSigners();
		Iterator it = c.iterator();

		//
		// check each signer
		//
		while (it.hasNext()) {
			SignerInformation signer = (SignerInformation) it.next();
			Collection certCollection = certs.getMatches(signer.getSID());

			Iterator certIt = certCollection.iterator();
			X509Certificate cert = new JcaX509CertificateConverter().setProvider(BC).getCertificate((X509CertificateHolder) certIt.next());

			//
			// verify that the sig is correct and that it was generated
			// when the certificate was current
			//
			if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider(BC).build(cert))) {
				System.out.println("signature verified");
			} else {
				System.out.println("signature failed!");
				throw new IrisException("Invalid signature");
			}
		}
	}

	@Override
	public void verifySignature(Part msg) throws Exception {
		if (msg.isMimeType("multipart/signed")) {			
			verifySignature((MimeMultipart) msg.getContent());
		} else if (msg.isMimeType("application/pkcs7-mime") || msg.isMimeType("application/x-pkcs7-mime")) {
			//
			// in this case the content is wrapped in the signature block.
			//
			SMIMESigned s = new SMIMESigned(msg);
			verify(s);
		} else {
			System.err.println("Not a signed message!");
		}
	}

	@Override
	public void verifySignature(MimeMultipart multi) throws Exception {
		verify(new SMIMESigned(multi));
	}

	@Override
	public MimeMessage encrypt(Session mailSession, MimeMessage message) throws Exception {
		System.out.println("Encrypting message ...");
		String to = getEmail(message.getRecipients(RecipientType.TO));
		System.out.println("TO: "+to);
		//X509Certificate certificate = SmimeKeyManager.instance().getCertificateByEmail(to);
		X509Certificate certificate = SmimeKeyManager.instance().getCertificate(to);
		System.out.println("CERTIFICATE: "+certificate.getSubjectDN().getName());
		
		/* Create the encrypter */
		SMIMEEnvelopedGenerator encrypter = new SMIMEEnvelopedGenerator();
		encrypter.addRecipientInfoGenerator(new JceKeyTransRecipientInfoGenerator(certificate).setProvider(BC));

		/* Encrypt the message */
		MimeBodyPart encryptedPart = encrypter.generate(message, new JceCMSContentEncryptorBuilder(CMSAlgorithm.RC2_CBC).setProvider(BC).build());

		/*
		 * Create a new MimeMessage that contains the encrypted and signed
		 * content
		 */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		encryptedPart.writeTo(out);

		MimeMessage encryptedMessage = new MimeMessage(mailSession, new ByteArrayInputStream(out.toByteArray()));

		/* Set all original MIME headers in the encrypted message */
		Enumeration headers = message.getAllHeaderLines();
		while (headers.hasMoreElements()) {
			String headerLine = (String) headers.nextElement();
			/*
			 * Make sure not to override any content-* headers from the original
			 * message
			 */
			if (!Strings.toLowerCase(headerLine).startsWith("content-")) {
				encryptedMessage.addHeaderLine(headerLine);
			}
		}

		return encryptedMessage;
	}

	@Override
	public MimeMessage sign(Session mailSession, MimeMessage message) throws Exception {
		System.out.println("Signing message ...");
		String from = getEmail(message.getFrom());
		System.out.println("FROM: "+from);
		//X509Certificate certificate = SmimeKeyManager.instance().getCertificateByEmail(from);
		X509Certificate certificate = SmimeKeyManager.instance().getCertificate(from);
		System.out.println("CERTIFICATE: "+certificate.getSubjectDN().getName());
		PrivateKey privateKey = SmimeKeyManager.instance().getPrivateKeyByCertificate(certificate);
		System.out.println("PRIVATE_KEY: "+privateKey.getAlgorithm());
		
		/* Create the SMIMESignedGenerator */
		SMIMECapabilityVector capabilities = new SMIMECapabilityVector();
		capabilities.addCapability(SMIMECapability.dES_EDE3_CBC);
		capabilities.addCapability(SMIMECapability.rC2_CBC, 128);
		capabilities.addCapability(SMIMECapability.dES_CBC);

		ASN1EncodableVector attributes = new ASN1EncodableVector();
		attributes.add(new SMIMEEncryptionKeyPreferenceAttribute(new IssuerAndSerialNumber(new X500Name(certificate.getIssuerDN().getName()), certificate.getSerialNumber())));
		attributes.add(new SMIMECapabilitiesAttribute(capabilities));

		SMIMESignedGenerator signer = new SMIMESignedGenerator();
		signer.addSignerInfoGenerator(new JcaSimpleSignerInfoGeneratorBuilder().setProvider(BC).setSignedAttributeGenerator(new AttributeTable(attributes)).build("DSA".equals(privateKey.getAlgorithm()) ? "SHA1withDSA" : "MD5withRSA", privateKey, certificate));

		/* Add the list of certs to the generator */
		List certList = new ArrayList();
		certList.add(certificate);
		Store certs = new JcaCertStore(certList);
		signer.addCertificates(certs);

		/* Sign the message */
		MimeMultipart mm = signer.generate(message);
		MimeMessage signedMessage = new MimeMessage(mailSession);

		/* Set all original MIME headers in the signed message */
		Enumeration headers = message.getAllHeaderLines();
		while (headers.hasMoreElements()) {
			signedMessage.addHeaderLine((String) headers.nextElement());
		}

		/* Set the content of the signed message */
		signedMessage.setContent(mm);
		signedMessage.saveChanges();

		return signedMessage;
	}
	
	
	private String getEmail(Address[] addresses){
		return addresses == null ? null : ((InternetAddress) addresses[0]).getAddress();
	}
}
