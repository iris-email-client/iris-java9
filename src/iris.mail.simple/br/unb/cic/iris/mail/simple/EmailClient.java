package br.unb.cic.iris.mail.simple;

import java.util.Date;
import java.util.List;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
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
		//added this because the message content was: com.sun.mail.imap.IMAPInputStream
		//http://stackoverflow.com/questions/10302564/can-not-cast-imapinputstream-to-multipart
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
		
	}

	@Override
	public void setProvider(EmailProvider provider) {
		setProvider(provider, CHARACTER_ENCODING);
	}

	@Override
	public void setProvider(EmailProvider provider, String encoding) {		
		sender = new EmailSender(provider, encoding);
		receiver = new EmailReceiver(provider, encoding);
	}

	@Override
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

	//TODO refactoring candidate. suggestion: throw EmailMessageValidationException whith messages instead of a list
	@Override
	public List<String> validateEmailMessage(EmailMessage message) {
		return EmailSender.validateEmailMessage(message);
	}

	@Override
	public List<EmailMessage> getMessagesAfterDate(String folder, Date lastMessageReceived) throws IrisException {
		SearchTerm searchTerm = null;
		if (lastMessageReceived != null) {
			searchTerm = new ReceivedDateTerm(ComparisonTerm.GT, lastMessageReceived);
		}
		return getMessages(folder, searchTerm);
	}
}