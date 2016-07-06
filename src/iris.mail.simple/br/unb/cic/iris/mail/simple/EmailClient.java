package br.unb.cic.iris.mail.simple;

import java.util.List;

import javax.mail.search.SearchTerm;

import br.unb.cic.iris.exception.IrisException;
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

	public void setProvider(EmailProvider provider) {
		setProvider(provider, CHARACTER_ENCODING);
	}

	public void setProvider(EmailProvider provider, String encoding) {		
		sender = new EmailSender(provider, encoding);
		receiver = new EmailReceiver(provider, encoding);
	}

	public void send(EmailMessage email) throws IrisException {		
		sender.send(email);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<IrisFolder> listFolders() throws IrisException {		
		return receiver.listFolders();
	}

	@Override
	public List<EmailMessage> getMessages(String folder) throws IrisException {
		return getMessages(folder, null);
	}

	@Override
	public List<EmailMessage> getMessages(String folder, SearchTerm searchTerm) throws IrisException {
		return receiver.getMessages(folder, searchTerm);
	}

	@Override
	public List<EmailMessage> getMessages(String folder, int seqnum) throws IrisException {
		return receiver.getMessages(folder, seqnum);
	}

	@Override
	public List<EmailMessage> getMessages(String folder, int begin, int end) throws IrisException {
		return receiver.getMessages(folder, begin, end);
	}

	//TODO refactoring candidate. suggestion: throw EmailMessageValidationException whith messages instead
	@Override
	public List<String> validateEmailMessage(EmailMessage message) {
		return EmailSender.validateEmailMessage(message);
	}
}