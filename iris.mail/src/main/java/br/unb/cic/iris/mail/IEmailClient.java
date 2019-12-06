package br.unb.cic.iris.mail;

import java.util.Date;
import java.util.List;

import javax.mail.search.SearchTerm;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;

public interface IEmailClient {
	public static final String CHARACTER_ENCODING = "UTF-8";
	
	public void setProvider(EmailProvider provider);

	public void setProvider(EmailProvider provider, String encoding);

	public void send(EmailMessage message) throws IrisException;

	@SuppressWarnings("rawtypes")
	public List<IrisFolder> listFolders() throws IrisException;

	public List<EmailMessage> getMessages(String folder, SearchTerm searchTerm) throws IrisException;
	
	public List<EmailMessage> getMessagesAfterDate(String folder, Date lastMessageReceived) throws IrisException;

	public List<EmailMessage> getMessages(String folder) throws IrisException;

	public List<String> validateEmailMessage(EmailMessage message);

	public List<EmailMessage> getMessages(String folder, int seqnum) throws IrisException;

	public List<EmailMessage> getMessages(String folder, int begin, int end) throws IrisException;

}
