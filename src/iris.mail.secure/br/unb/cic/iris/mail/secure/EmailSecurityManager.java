package br.unb.cic.iris.mail.secure;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

public interface EmailSecurityManager {
	public MimeMessage encrypt(Session mailSession, MimeMessage message) throws Exception;
	public MimeMessage encrypt(MimeMessage message) throws Exception;
	public Object decrypt(Session mailSession, MimeMessage msg) throws Exception;
	public Object decrypt(MimeMessage msg) throws Exception;
	
	public MimeMessage sign(Session mailSession, MimeMessage message) throws Exception;
	public MimeMessage sign(MimeMessage message) throws Exception;
	public void verifySignature(MimeMessage msg) throws Exception;
	public void verifySignature(MimePart msg, String from) throws Exception;	
	public void verifySignature(MimeMultipart multi, String from) throws Exception;
}
