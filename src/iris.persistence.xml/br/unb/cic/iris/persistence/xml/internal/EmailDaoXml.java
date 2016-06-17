package br.unb.cic.iris.persistence.xml.internal;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.xml.internal.EmailMessageXml;
import br.unb.cic.iris.model.xml.internal.IrisFolderXml;
import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.PersistenceException;

public class EmailDaoXml extends AbstractDaoXml implements IEmailDAO {

	@Override
	public void saveMessage(EmailMessage message) throws PersistenceException {
		IrisMessageStoreXml store = getIrisXmlStore();
		IrisFolderXml folder = store.findFolderByPredicate(f -> f.getId().equals(message.getFolder().getId()));
		message.setId(UUID.randomUUID().toString());
		folder.addMessage((EmailMessageXml)message);
		persistIrisStore(store);
	}

	@Override
	public Date lastMessageReceived() throws PersistenceException {
		IrisFolder<EmailMessageXml> folder = getFolderDAO().findByName(IrisFolder.INBOX); 
		return folder.getMessages().stream()
				.map(EmailMessageXml::getDate) //m -> m.getDate()
				.max(Date::compareTo)
				.get();
	}

	@Override
	public List<EmailMessage> listMessages(String idFolder) throws PersistenceException {
		IrisFolder folder = getFolderDAO().findById(idFolder);
		if(folder == null){
			throw new PersistenceException("There exists no folder with the specified ID: "+idFolder);
		}		
		return folder.getMessages();
	}

}
