package br.unb.cic.iris.persistence;

import java.util.Date;
import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
/*** added by dBasePersistence
 */
public interface IEmailDAO {
	public void saveMessage(EmailMessage message) throws PersistenceException;
	public Date lastMessageReceived(String folderName) throws PersistenceException;
	public List<EmailMessage> listMessages(String idFolder) throws PersistenceException;
	public EmailMessage findById(String id) throws PersistenceException;
}