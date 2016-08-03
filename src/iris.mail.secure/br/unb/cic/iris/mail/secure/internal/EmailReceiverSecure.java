package br.unb.cic.iris.mail.secure.internal;

import static br.unb.cic.iris.mail.MessageUtils.MIME_MULTIPART_ENCRYPTED;
import static br.unb.cic.iris.mail.MessageUtils.MIME_MULTIPART_SIGNED;
import static br.unb.cic.iris.mail.MessageUtils.MIME_SMIME;
import static br.unb.cic.iris.mail.MessageUtils.MIME_SMIME_2;

import java.io.InputStream;
import java.lang.reflect.Method;

import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.BASE64DecoderStream;

import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.MessageUtils;
import br.unb.cic.iris.mail.internal.AbstractEmailReceiver;
import br.unb.cic.iris.mail.secure.EmailSecurityManager;

//TODO rever exceptions + i18n
public abstract class EmailReceiverSecure extends AbstractEmailReceiver {	
	
	public EmailReceiverSecure(EmailProvider provider, String encoding) {
		super(provider, encoding);		
	}
	
	protected abstract EmailSecurityManager getEmailSecurityManager();
	
	private String from = "";
	
	protected String getText(Object content) throws Exception {
		try {
			Method method = getClass().getDeclaredMethod("getText", content.getClass());
			return method.invoke(this, content).toString();
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException("Can't read message type: " + content.getClass(), ex);
		}
	}
	
	protected String getText(Part part) throws Exception {
		System.out.println("Part: "+part.getContentType()+" ---- "+part.getContent().getClass());
		return getText(part.getContent());
	}
	
	
	protected String getText(InputStream msg) {
		System.out.println("InputStream ...");
		return MessageUtils.getStringFromInputStream(msg);
	}

	protected String getText(String str) {
		System.out.println("String: "+str);
		return str;
	}
	
	protected String getText(MimeMessage msg) throws Exception {
		System.out.println("EmailReceiverSmime_MimeMessage: "+msg.getContentType());
		from = MessageUtils.getFrom(msg);
		if(msg.isMimeType(MIME_MULTIPART_ENCRYPTED) 
				|| msg.isMimeType(MIME_SMIME) 
				|| msg.isMimeType(MIME_SMIME_2)){
			return getText(getEmailSecurityManager().decrypt(msg));
		}else if(msg.isMimeType(MIME_MULTIPART_SIGNED)){
			getEmailSecurityManager().verifySignature(msg);
		}

		return getText(msg.getContent());
	}
		
	protected String getText(MimeMultipart multi) throws Exception {
		System.out.println("EmailReceiverSmime_MimeMultipart: "+multi.getContentType());
		String contentType = MessageUtils.getMimeType(multi.getContentType());
		if (contentType.contains(MIME_MULTIPART_SIGNED)) {
			getEmailSecurityManager().verifySignature(multi, from);			
		}
		return getText((Multipart) multi);	
	}

	protected String getText(Multipart multi) throws Exception {
		System.out.println("Multipart: "+multi.getCount()+" -- "+multi.getContentType());
		int parts = multi.getCount();
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < parts; j++) {
			MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
			System.out.println("part="+part.getContentType());
			if(!(part.getContent() instanceof BASE64DecoderStream)){
				sb.append(getText(part.getContent())+"\n");
			}
		}
		return sb.toString();
	}

}
