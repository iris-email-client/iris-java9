package br.unb.cic.iris.mail.secure.internal;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.internal.AbstractEmailSender;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;
import br.unb.cic.iris.model.EmailMessage;

public abstract class EmailSenderSecure extends AbstractEmailSender {

	public EmailSenderSecure(EmailProvider provider, String encoding) {
		super(provider, encoding);
	}
	
	protected abstract EmailSecurityManager getEmailSecurityManager();

	
	private MimeMessage sign(MimeMessage message) throws Exception {
		return getEmailSecurityManager().sign(session.getSession(), message);
	}
	
	private MimeMessage encrypt(MimeMessage message) throws Exception{
		return getEmailSecurityManager().encrypt(session.getSession(), message);
	}
	
	protected MimeMessage createMessage(EmailMessage email) throws MessagingException, UnsupportedEncodingException {
		System.out.println("EmailSenderSecure: creating message ...");
		MimeMessage message = super.createMessage(email);
		try {
			return encrypt(sign(message));
		} catch (Exception e) {
			throw new IrisUncheckedException("Error while creating message", e);
		}
	}
}
