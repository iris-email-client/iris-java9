package br.unb.cic.iris.mail;

import java.util.List;

import br.unb.cic.iris.exception.EmailException;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;


public interface IEmailClient {
	public void setProvider(EmailProvider provider);
	public void setProvider(EmailProvider provider, String encoding);
	
	
	public void send(EmailMessage message) throws EmailException;

	public List<IrisFolder> listFolders() throws EmailException;

	//public List<EmailMessage> getMessages(String folder, SearchTerm searchTerm) throws EmailException;

	public List<EmailMessage> getMessages(String folder) throws EmailException;

	public List<String> validateEmailMessage(EmailMessage message);

	public List<EmailMessage> getMessages(String folder, int seqnum) throws EmailException;

	public List<EmailMessage> getMessages(String folder, int begin, int end) throws EmailException;

	// public void send(EmailMessage message) throws EmailException;
	//
	// public List<IrisFolder> listFolders() throws EmailException;
	//
	// public List<EmailMessage> getMessages(String folder) throws
	// EmailException;
	//
	// public List<EmailMessage> getMessages(String folder, int seqnum) throws
	// EmailException;
	//
	// public List<EmailMessage> getMessages(String folder, int begin, int end)
	// throws EmailException;
	//
	// public List<String> validateEmailMessage(EmailMessage message);

}
