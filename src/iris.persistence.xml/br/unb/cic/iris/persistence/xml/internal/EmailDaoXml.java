package br.unb.cic.iris.persistence.xml.internal;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.model.xml.internal.EmailMessageXml;
import br.unb.cic.iris.model.xml.internal.IrisFolderXml;
import br.unb.cic.iris.model.xml.internal.IrisMessageStoreXml;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class EmailDaoXml extends AbstractDaoXml implements EmailDAO {

	@Override
	public void saveMessage(EmailMessage message) throws IrisPersistenceException {
		IrisMessageStoreXml store = getIrisXmlStore();
		IrisFolderXml folder = store.findFolderByPredicate(f -> f.getId().equals(message.getFolder().getId()));
		message.setId(UUID.randomUUID().toString());
		folder.addMessage((EmailMessageXml) message);
		persistIrisStore(store);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Date lastMessageReceived(String folderName) throws IrisPersistenceException {
		IrisFolder<EmailMessageXml> folder = getFolderDAO().findByName(folderName);
		return folder.getMessages().stream()
				.map(EmailMessageXml::getDate) // m -> m.getDate()
				.max(Date::compareTo).orElse(null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EmailMessage> listMessages(String idFolder) throws IrisPersistenceException {
		IrisFolder folder = getFolderDAO().findById(idFolder);
		if (folder == null) {
			throw new IrisPersistenceException("There exists no folder with the specified ID: " + idFolder);
		}
		return folder.getMessages();
	}

	/*
	 * flatMap(Function<? super T,? extends Stream<? extends R>> mapper) Returns
	 * a stream consisting of the results of replacing each element of this
	 * stream with the contents of a mapped stream produced by applying the
	 * provided mapping function to each element.
	 */
	@Override
	public EmailMessage findById(String id) throws IrisPersistenceException {
		Optional<EmailMessageXml> message = getIrisXmlStore().getFolders()
			.stream()
			.flatMap(f -> f.getMessages().stream())
			.filter(m -> m.getId().equals(id))
			.findFirst();		
		return message.get();
	}

}
