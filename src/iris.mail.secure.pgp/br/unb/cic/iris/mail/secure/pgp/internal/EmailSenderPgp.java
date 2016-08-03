package br.unb.cic.iris.mail.secure.pgp.internal;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;
import br.unb.cic.iris.mail.secure.internal.EmailSenderSecure;


public class EmailSenderPgp extends EmailSenderSecure {	

	public EmailSenderPgp(EmailProvider provider, String encoding) {
		super(provider, encoding);
	}

//	private MimeMessage sign(MimeMessage message) throws Exception {
//		return manager.sign(session.getSession(), message);
//	}
//	
//	private MimeMessage encrypt(MimeMessage message) throws Exception{
//		return manager.encrypt(session.getSession(), message);
//	}
//	
//	protected MimeMessage createMessage(EmailMessage email) throws MessagingException, UnsupportedEncodingException {
//		System.out.println("EmailSenderPgp: creating message ...");
//		MimeMessage message = super.createMessage(email);
//		try {
//			return encrypt(sign(message));
//		} catch (Exception e) {
//			throw new IrisUncheckedException("Error while creating message", e);
//		}
//	}

	@Override
	protected EmailSecurityManager getEmailSecurityManager() {		
		return PgpSecurityManager.instance();
	}

	
}
