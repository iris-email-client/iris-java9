package br.unb.cic.iris.mail.internal;

import static br.unb.cic.iris.mail.i18n.MessageBundle.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.FolderEvent;
import javax.mail.event.StoreEvent;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;

import br.unb.cic.iris.exception.IrisException;
import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.mail.EmailProvider;
import br.unb.cic.iris.mail.EmailReceiver;
import br.unb.cic.iris.mail.EmailStatusManager;
import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.model.IrisFolder;

public abstract class AbstractEmailReceiver implements EmailReceiver {
	private Store store;
	private EmailSession session;
	private EmailProvider provider;
	private EntityFactory entityFactory;

	public AbstractEmailReceiver(EmailProvider provider, String encoding) {
		this.provider = provider;
		session = new EmailSession(provider, encoding);
		initEntityFactory();
	}

	protected abstract String getText(Object obj) throws Exception;
	
	public List<IrisFolder> listFolders() throws IrisException {
		List<IrisFolder> folders = new ArrayList<>();
		try {
			Folder defaultFolder = getStore().getDefaultFolder();
			Folder[] externalFolders = defaultFolder.list();
			for (Folder f : externalFolders) {
				folders.add(getEntityFactory().createIrisFolder(f.getName()));
			}
		} catch (MessagingException e) {
			throw new IrisException(message("error.list.folder"), e);
		}
		return folders;
	}

	public List<EmailMessage> getMessages(String folderName, SearchTerm searchTerm) throws IrisException {
		List<EmailMessage> messages = new ArrayList<>();
		Folder folder = openFolder(folderName);
		try {
			Message messagesRetrieved[] = null;
			if (searchTerm == null) {
				messagesRetrieved = folder.getMessages();
			} else {
				messagesRetrieved = folder.search(searchTerm);
			}
			messages = convertToIrisMessage(messagesRetrieved);
		} catch (MessagingException e) {
			throw new IrisException(e.getMessage(), e);
		}
		return messages;
	}

	public List<EmailMessage> getMessages(String folderName, int begin, int end) throws IrisException {
		List<EmailMessage> messages = new ArrayList<>();
		Folder folder = openFolder(folderName);
		try {
			Message messagesRetrieved[] = folder.getMessages(begin, end);
			messages = convertToIrisMessage(messagesRetrieved);
		} catch (MessagingException e) {
			throw new IrisException(e.getMessage(), e);
		}
		return messages;
	}

	public List<EmailMessage> getMessages(String folderName, int seqnum) throws IrisException {
		List<EmailMessage> messages = new ArrayList<>();
		Folder folder = openFolder(folderName);
		try {
			List<Message> messagesList = new ArrayList<>();
			int messageCount = folder.getMessageCount();
			for (int i = seqnum; i <= messageCount; i++) {
				Message message = folder.getMessage(i);
				messagesList.add(message);
			}
			Message[] messagesRetrieved = toArray(messagesList);
			messages = convertToIrisMessage(messagesRetrieved);
		} catch (MessagingException e) {
			throw new IrisException(e.getMessage(), e);
		}
		return messages;
	}

	private Message[] toArray(List<Message> messagesList) {
		return messagesList.toArray(new Message[messagesList.size()]);
	}

	private List<EmailMessage> convertToIrisMessage(Message[] messagesRetrieved) throws IrisException {
		List<EmailMessage> messages = new ArrayList<>();
		int cont = 0;
		int total = messagesRetrieved.length;
		for (Message m : messagesRetrieved) {
			// TODO
			messages.add(convertToIrisMessage(m));
			if (total != 0) {
				for (int i = 0; i < 15; i++) {
					System.out.print('\b');
				}
				cont++;
				int tmp = 100 * cont;
				System.out.print(message("email.receiver.download.progress", (tmp / total)));

				EmailStatusManager.instance().notifyMessageDownloadProgress((tmp / total));
			}
		}
		System.out.println();
		return messages;
	}

	private Folder openFolder(String folderName) throws IrisException {
		return openFolder(folderName, Folder.READ_ONLY);
	}

	private Folder openFolder(String folderName, int openType) throws IrisException {
		try {
			Folder folder = getStore().getFolder(folderName);
			folder.open(openType);
			return folder;
		} catch (MessagingException | IrisException e) {
			throw new IrisException(e.getMessage(), e);
		}
	}

	private EmailMessage convertToIrisMessage(Message message) throws IrisException {
		MimeMessage m = (MimeMessage) message;
		EmailMessage msg = getEntityFactory().createEmailMessage();

		try {
			msg.setBcc(convertAddressToString(m.getRecipients(RecipientType.BCC)));
			msg.setCc(convertAddressToString(m.getRecipients(RecipientType.CC)));
			msg.setTo(convertAddressToString(m.getRecipients(RecipientType.TO)));
			msg.setFrom(convertAddressToString(m.getFrom()));
			msg.setMessage(getText(m));
			msg.setSubject(m.getSubject());
			msg.setDate(m.getReceivedDate());
		} catch (Exception e) {
			// TODO i18n
			throw new IrisException("error ....", e);
		}

		return msg;
	}

	private String convertAddressToString(Address[] recipients) {
		StringBuilder sb = new StringBuilder("");
		if (recipients != null) {
			for (Address a : recipients) {
				sb.append(a.toString() + ", ");
			}
		}
		return sb.toString();
	}

	private Store createStoreAndConnect() throws MessagingException {
		EmailStatusManager.instance().notifyListener(message("email.status.receiver.create.store"));
		Store storeTmp = session.getSession().getStore(provider.getStoreProtocol());
		storeTmp.addStoreListener(this);
		storeTmp.addConnectionListener(session);
		session.connect(storeTmp, provider.getStoreHost(), provider.getStorePort());
		return storeTmp;
	}

	public Store getStore() throws IrisException {
		if (store == null) {
			try {
				store = createStoreAndConnect();
			} catch (MessagingException e) {
				throw new IrisException(e.getMessage(), e);
			}
		}
		return store;
	}

	public Store renew() throws IrisException {
		if (store != null) {
			try {
				store.close();
			} catch (MessagingException e) {
				throw new IrisException(e.getMessage(), e);
			}
			store = null;
		}
		return getStore();
	}

	@Override
	public void notification(StoreEvent e) {
		EmailStatusManager.instance().notifyListener(message("email.status.receiver.notification", e.getMessage()));
	}

	@Override
	public void folderCreated(FolderEvent e) {
	}

	@Override
	public void folderDeleted(FolderEvent e) {
	}

	@Override
	public void folderRenamed(FolderEvent e) {
	}

	private EntityFactory getEntityFactory() {
		return entityFactory;
	}

	private void initEntityFactory() {
		ServiceLoader<EntityFactory> sl = ServiceLoader.load(EntityFactory.class);
		Iterator<EntityFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No Entity Factory found!");

		entityFactory = it.next();
		System.out.println("Entity Factory: " + entityFactory.getClass().getCanonicalName());
	}

}
