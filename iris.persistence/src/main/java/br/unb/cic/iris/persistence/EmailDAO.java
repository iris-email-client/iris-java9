package br.unb.cic.iris.persistence;

import java.util.Date;
import java.util.List;

import br.unb.cic.iris.model.EmailMessage;
/*** added by dBasePersistence
 */
public interface EmailDAO {
	public void saveMessage(EmailMessage message) throws IrisPersistenceException;
	public Date lastMessageReceived(String folderName) throws IrisPersistenceException;
	public List<EmailMessage> listMessages(String idFolder) throws IrisPersistenceException;
	public EmailMessage findById(String id) throws IrisPersistenceException;
}