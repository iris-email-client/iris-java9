package br.unb.cic.iris.mail.internal;

import static br.unb.cic.iris.mail.i18n.MessageBundle.message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisValidationException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.EmailSender;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.util.StringUtil;

public abstract class AbstractEmailSender implements EmailSender {
	protected EmailSession session;
	private EmailProvider provider;

	public AbstractEmailSender(EmailProvider provider, String encoding) {
		this.provider = provider;
		session = new EmailSession(this.provider, encoding);
	}

	public void send(EmailMessage email) throws IrisException {
		List<String> errorMessages = validateEmailMessage(email);
		if (errorMessages.isEmpty()) {
			try {
				final Message message = createMessage(email);
				message.saveChanges();
				Transport transport = createTransport();
				session.connect(transport, provider.getTransportHost(), provider.getTransportPort());
				notifyListeners(message("email.status.sender.sending"));				
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (final UnsupportedEncodingException e) {				
				throw new IrisException(message("error.invalid.encoding", e.getMessage()), e);
			} catch (final MessagingException e) {				
				throw new IrisException(message("error.send.email", e.getMessage()), e);
			}
		} else {
			throw new IrisValidationException(errorMessages);
		}
	}

	public List<String> validateEmailMessage(EmailMessage message) {
		List<String> errorMessages = new ArrayList<>();
		if (message == null) {
			errorMessages.add(message("error.null.message"));
		} else if (StringUtil.isEmpty(message.getFrom())) {
			errorMessages.add(message("error.required.field", message("command.send.label.from")));
		}
		return errorMessages;
	}

	private Transport createTransport() throws MessagingException {
		notifyListeners(message("email.status.sender.create.transport", provider.getTransportProtocol()));		
		Transport transport = session.getSession().getTransport(provider.getTransportProtocol());
		transport.addTransportListener(this);
		transport.addConnectionListener(session);
		return transport;
	}

	protected MimeMessage createMessage(final EmailMessage email) throws MessagingException, UnsupportedEncodingException {
		final MimeMessage message = new MimeMessage(session.getSession());
		message.setSubject(email.getSubject(), session.getEncoding());
		message.setFrom(new InternetAddress(email.getFrom(), session.getEncoding()));
		message.setRecipient(RecipientType.TO, new InternetAddress(email.getTo()));
		message.setText(email.getMessage(), session.getEncoding());
		if (StringUtil.notEmpty(email.getCc())) {
			message.setRecipient(RecipientType.CC, new InternetAddress(email.getCc()));
		}
		if (StringUtil.notEmpty(email.getBcc())) {
			message.setRecipient(RecipientType.BCC, new InternetAddress(email.getBcc()));
		}
		message.setSentDate(new Date());
		return message;
	}

	@Override
	public void messageDelivered(TransportEvent e) {
		notifyListeners(message("email.status.sender.message.delivered"));
	}

	@Override
	public void messageNotDelivered(TransportEvent e) {
		notifyListeners(message("email.status.sender.message.not.delivered"));
	}

	@Override
	public void messagePartiallyDelivered(TransportEvent e) {
		notifyListeners(message("email.status.sender.message.partially.delivered"));	
	}
}
