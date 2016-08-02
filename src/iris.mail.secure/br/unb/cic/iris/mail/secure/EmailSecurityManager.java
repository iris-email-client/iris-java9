package br.unb.cic.iris.mail.secure;

import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public interface EmailSecurityManager {
	public MimeMessage encrypt(Session mailSession, MimeMessage message) throws Exception;
	public Object decrypt(MimeMessage msg) throws Exception;
	
	public MimeMessage sign(Session mailSession, MimeMessage message) throws Exception;
	public void verifySignature(Part msg) throws Exception;
	public void verifySignature(MimeMultipart multi) throws Exception;
}
