package br.unb.cic.iris.mail.simple;

import static br.unb.cic.iris.mail.i18n.MessageBundle.message;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.internal.AbstractEmailReceiver;

public class EmailReceiverSimple extends AbstractEmailReceiver {

	public EmailReceiverSimple(EmailProvider provider, String encoding) {
		super(provider, encoding);
	}

	protected String getText(Object obj) throws Exception {
		if (obj instanceof Message) {
			return getText((Message) obj);
		}
		// TODO i18n
		throw new IrisException("Unhandled type: " + obj.getClass());

	}

	protected String getText(Message message) throws IOException, MessagingException {
		String result = message.getContent().toString();
		if (message instanceof MimeMessage) {
			MimeMessage m = (MimeMessage) message;
			Object contentObject = m.getContent();
			if (contentObject instanceof Multipart) {
				BodyPart clearTextPart = null;
				BodyPart htmlTextPart = null;
				Multipart content = (Multipart) contentObject;
				int count = content.getCount();
				for (int i = 0; i < count; i++) {
					BodyPart part = content.getBodyPart(i);
					if (part.isMimeType("text/plain")) {
						clearTextPart = part;
						break;
					} else if (part.isMimeType("text/html")) {
						htmlTextPart = part;
					}
				}
				if (clearTextPart != null) {
					result = (String) clearTextPart.getContent();
				} else if (htmlTextPart != null) {
					String html = (String) htmlTextPart.getContent();
					result = html;
				}
			} else if (contentObject instanceof String) {
				result = (String) contentObject;
			} else {
				System.err.println(message("email.receiver.invalid.body", message.toString()));
				result = null;
			}
		}
		return result;
	}
}