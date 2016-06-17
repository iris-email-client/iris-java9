package br.unb.cic.iris.mail.simple;

import java.util.List;

import javax.mail.search.SearchTerm;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.IEmailClient;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

/***
 * added by dBaseMail
 */
public class EmailClient implements IEmailClient {
	public static final String CHARACTER_ENCODING = "UTF-8";
	private EmailSender sender;
	private EmailReceiver receiver;

	public EmailClient() {

	}

	// public EmailClient(EmailProvider provider) {
	// this(provider, CHARACTER_ENCODING);
	// }
	//
	// public EmailClient(EmailProvider provider, String encoding) {
	// sender = new EmailSender(provider, encoding);
	// receiver = new EmailReceiver(provider, encoding);
	// }

	// TODO connect
	public void setProvider(EmailProvider provider) {
		setProvider(provider, CHARACTER_ENCODING);
	}

	public void setProvider(EmailProvider provider, String encoding) {
		System.out.println("Using provider: " + provider.getName());
		sender = new EmailSender(provider, encoding);
		receiver = new EmailReceiver(provider, encoding);
	}

	public void send(EmailMessage email) throws EmailException {
		System.out.println("send message: " + email);
		sender.send(email);
	}

	@Override
	public List<IrisFolder> listFolders() throws EmailException {
		System.out.println("listing folders ...");
		return receiver.listFolders();
	}

	@Override
	public List<EmailMessage> getMessages(String folder) throws EmailException {
		return getMessages(folder, null);
	}

	// @Override
	public List<EmailMessage> getMessages(String folder, SearchTerm searchTerm) throws EmailException {
		return receiver.getMessages(folder, searchTerm);
	}

	@Override
	public List<EmailMessage> getMessages(String folder, int seqnum) throws EmailException {
		return receiver.getMessages(folder, seqnum);
	}

	@Override
	public List<EmailMessage> getMessages(String folder, int begin, int end) throws EmailException {
		return receiver.getMessages(folder, begin, end);
	}

	@Override
	public List<String> validateEmailMessage(EmailMessage message) {
		return EmailSender.validateEmailMessage(message);
	}
}